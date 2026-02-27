package ui;

import model.Event;
import model.EventLog;
import model.exception.LogException;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.Playlist;
import model.Song;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

// Represents a PlaylistApp
public class PlaylistApp {
    public static final String JSON_STORE = "./data/Playlist.json";
    private Scanner input;
    private Playlist playlist;
    private Scanner deleteInput;
    private Scanner songInput;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private FileWriter fw;

    public PlaylistApp() throws LogException {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runPlaylist();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runPlaylist() throws LogException {
        boolean playlistOpen = true;
        String command;

        init();

        while (playlistOpen) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                playlistOpen = false;
            } else {
                processCommand(command);
            }
        }
        printLog(EventLog.getInstance());
        System.out.println("Goodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        input = new Scanner(System.in);
        playlist = new Playlist("Top Songs");
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAdd();
        } else if (command.equals("d")) {
            doDelete();
        } else if (command.equals("p")) {
            doPlaySong();
        } else if (command.equals("v")) {
            doView();
        } else if (command.equals("m")) {
            showMostStreamedSong();
        } else if (command.equals("s")) {
            savePlaylist();
        } else if (command.equals("l")) {
            loadPlaylist();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: will track that the given song names song has been played
    private void doPlaySong() {
        Scanner seeSongName = new Scanner(System.in);
        System.out.println("Song Name:");
        String songName = seeSongName.nextLine();

        if (playlist.findSong(songName) == null) {
            System.out.println("Sorry, cannot find the song you're looking for.");
        } else {
            playlist.findSong(songName).playSong();
            System.out.println("Your song has been played.");
        }
    }

    // EFFECTS: displays menu of options
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add song");
        System.out.println("\td -> delete song");
        System.out.println("\tp -> track played song");
        System.out.println("\tv -> view songs");
        System.out.println("\tm -> show most streamed song");
        System.out.println("\ts -> save work room to file");
        System.out.println("\tl -> load work room from file");
        System.out.println("\tq -> close application");
    }

    // EFFECTS: this
    // EFFECTS: creates and adds a song to playlist from the users input
    private void doAdd() {
        songInput = new Scanner(System.in);
        System.out.println("\nSong name:");
        String songName = songInput.nextLine();

        System.out.println("Song artist:");
        String songArtist = songInput.nextLine();

        System.out.println("Song genre:");
        String songGenre = songInput.nextLine();

        System.out.println("Song length in seconds:");
        int songLength = songInput.nextInt();

        Song song = new Song(songName, songArtist, songGenre, songLength);
        playlist.addSong(song);
    }

    // EFFECTS: Displays the most streamed song in the playlist
    private void showMostStreamedSong() {
        Song topSong = playlist.getTopSong();
        int amountListened = topSong.getStreams() * topSong.getLengthOfSong();
        if (playlist.getSongs().isEmpty()) {
            System.out.println("You have no played songs!");
        } else {
            System.out.println("Your most streamed song is: " + topSong.getSongName() + "\n"
                    + topSong.viewSong(topSong));
            System.out.println("\nYou have listened to this song " + topSong.getStreams() + " times");
            System.out.println("\nYou have listened to this song for " + amountListened + " seconds");
        }
    }

    // EFFECTS: Display the songs in your playlist
    private void doView() {
        if (playlist.getSongs().isEmpty()) {
            System.out.println("You have no songs in your playlist!");
        } else {
            for (Song song : playlist.getSongs()) {
                System.out.println("\n\tSong Name: " + song.getSongName() + "\n\tSong Artist: "
                        + song.getArtistName() + "\n\tSong Genre: " + song.getGenreType()
                        + "\n\tSong Length (in seconds): " + song.getLengthOfSong());
            }
            System.out.println("\nYou have " + playlist.getNumSongs() + " songs in your playlist.");
        }
    }

    //MODIFIES: this
    // EFFECTS: Deletes the song in the playlist that corresponds with the users in put of song name
    private void doDelete() {
        deleteInput = new Scanner(System.in);
        System.out.println("What is the name of the song you want to delete?");
        String input = deleteInput.nextLine();
        List<Song> temp = new ArrayList<>();

        if (playlist.getSongs().isEmpty()) {
            System.out.println("You have no songs in your playlist!");
        } else {
            for (Song song : playlist.getSongs()) {
                if (!Objects.equals(song.getSongName(), input)) {
                    temp.add(song);
                }
            }
            System.out.println("Your song has been deleted from the playlist.");
        }
        playlist.setSongs(temp);
        playlist.setNumSongs(temp.size());
    }

    // EFFECTS: saves the workroom to file
    private void savePlaylist() {
        try {
            jsonWriter.open();
            jsonWriter.write(playlist);
            jsonWriter.close();
            System.out.println("Saved " + playlist.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    public void loadPlaylist() {
        try {
            playlist = jsonReader.read();
            System.out.println("Loaded " + playlist.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public void printLog(EventLog el) throws LogException {
        try {
            for (Event next : el) {
                fw.write(next.toString());
                fw.write("\n\n");
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new LogException("Cannot write to file");
        }
    }
}


