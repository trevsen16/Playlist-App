package model;

import persistence.Writable;
import org.json.JSONObject;

// Represents a Song with a name, artist, genre and length of the song
public class Song implements Writable {
    private final String name;
    private final String artist;
    private final String genre;
    private final int length;   // length of song in seconds
    private int streams;


    // REQUIRES: lengthOfSong > 0
    // EFFECTS: Constructs a song with a song name, artist, genre and the length of the song
    public Song(String songName, String artist, String genreType, int lengthOfSong) {
        this.name = songName;
        this.artist = artist;
        this.genre = genreType;
        this.length = lengthOfSong;
    }

    // MODIFIES: this
    // EFFECTS: adds 1 to the count of streams of the song
    public void playSong() {
        streams++;
    }

    // EFFECTS: displays song and its variables in a string
    public String viewSong(Song song) {
        return ("\tSong Name: " + song.getSongName() + "\n\tSong Artist: " + song.getArtistName() + "\n\tSong Genre: "
                + song.getGenreType() + "\n\tSong Length (in seconds): " + song.getLengthOfSong());
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("artist", artist);
        json.put("genre", genre);
        json.put("length", length);
        return json;
    }

    public String printSong() {
        return "\n\tSong Name: " + getSongName() + " \n\tSong Artist: "
                    + getArtistName() + " \n\tSong Genre: " + getGenreType()
                    + " \n\tSong Length (in seconds): " + getLengthOfSong();
    }


    public String getGenreType() {
        return genre;
    }

    public int getLengthOfSong() {
        return length;
    }

    public int getStreams() {
        return streams;
    }

    public String getSongName() {
        return name;
    }

    public String getArtistName() {
        return artist;
    }
}
