package ui;

import model.Event;
import model.EventLog;
import model.Playlist;
import model.Song;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import static ui.PlaylistApp.JSON_STORE;

// Graphical User Interface for program
public class GUI extends JFrame {
    private JPanel panelDelete;
    private JPanel panelAdd;
    private JPanel panelAll;
    private JPanel panelSongName;
    private JPanel panelArtistName;
    private JPanel panelGenre;
    private JPanel panelSongLength;
    private JPanel panelLoadAndSave;
    private Playlist playlist;
    private JTextField songNameTextField;
    private JTextField artistNameTextField;
    private JTextField genreTextField;
    private JTextField songLengthTextField;
    private JFrame frame;
    private JTextField deleteTextField;
    private JPanel songDisplay;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public GUI() {
        initialize();
    }

    // MODIFIES: this
    // EFFECTS: initializes all fields, photo, panels, and frame
    public void initialize() {
        instantiateFields();
        makeDeletePanel();
        makeAddPanel();
        makeSaveAndLoadPanel();
        combinePanels();
        songDisplay();

        frame = new JFrame();
        frame.setSize(500, 400);
        frame.add(panelAll, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Playlist App");
        frame.pack();
        frame.setVisible(true);
        addFrameWindowListener();

        initializePhoto();
    }

    // MODIFIES: this
    // EFFECTS: adds a window listener to frame and prints out EventLog when application closes
    private void addFrameWindowListener() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.toString());
                    System.out.println("\n");
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initialize all fields
    public void instantiateFields() {
        panelDelete = new JPanel();
        panelAdd = new JPanel();
        panelAll = new JPanel();
        panelSongName = new JPanel();
        panelArtistName = new JPanel();
        panelGenre = new JPanel();
        panelSongLength = new JPanel();
        panelLoadAndSave = new JPanel();
        playlist = new Playlist("Top Songs");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: initializes photo onto the frame
    public void initializePhoto() {
        JLabel initPic;
        ImageIcon img = new ImageIcon("data/210pic.png");
        initPic = new JLabel();
        initPic.setIcon(img);
        frame.add(initPic, BorderLayout.EAST);
        frame.pack();
    }

    // MODIFIES: this
    // EFFECTS: creates panel for songs to be displayed onto the frame
    public void songDisplay() {
        songDisplay = new JPanel();
        songDisplay.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        songDisplay.setLayout(new BoxLayout(songDisplay, BoxLayout.Y_AXIS));
    }

    // MODIFIES: this
    //EFFECTS: displays an updated version of the song panel
    public void displayUpdate() {
        JLabel songLabel;
        for (Song song : playlist.getSongs()) {
            songLabel = new JLabel(song.printSong());
            songDisplay.add(songLabel);
        }

        frame.add(songDisplay, BorderLayout.NORTH);
        frame.pack();
    }

    // MODIFIES: this
    // EFFECTS: creates a delete panel with textField to be put onto the frame
    public void makeDeletePanel() {
        JButton deleteButton = new JButton("Delete a song");
        deleteTextField = new JTextField(15);

        deleteButton.addActionListener((e) -> {
            deleteSongInDisplay();
            playlist.removeSong(playlist.findSong(deleteTextField.getText()));
        });

        panelDelete.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panelDelete.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelDelete.add(deleteTextField);
        panelDelete.add(deleteButton);
    }

    // MODIFIES: this
    // EFFECTS: deletes song from playlist and removes it from the display panel
    public void deleteSongInDisplay() {
        for (Song song : playlist.getSongs()) {
            if (Objects.equals(song.getSongName(), deleteTextField.getText())) {
                playlist.removeSong(song);
                break;
            }
        }
        frame.remove(songDisplay);
        songDisplay();
        displayUpdate();
    }

    // REQUIRES: song name, artist and genre inputs to be of type String
    //           song length to be of type int
    // MODIFIES: this
    //EFFECTS: creates an add panel to be used and displayed on the frame
    public void makeAddPanel() {
        makeSongNamePanel();
        makeArtistNamePanel();
        makeGenrePanel();
        makeSongLengthPanel();

        JButton addButton = new JButton("Add a song");
        addButton.addActionListener((e) -> {
            playlist.addSong(new Song(songNameTextField.getText(), artistNameTextField.getText(),
                    genreTextField.getText(), Integer.parseInt(songLengthTextField.getText())));
            frame.remove(songDisplay);
            songDisplay();
            displayUpdate();
        });

        panelAdd.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panelAdd.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelAdd.add(panelSongName);
        panelAdd.add(panelArtistName);
        panelAdd.add(panelGenre);
        panelAdd.add(panelSongLength);
        panelAdd.add(addButton);
    }

    // MODIFIES: this
    // EFFECTS: creates song name panel to be placed into add panel
    public void makeSongNamePanel() {
        songNameTextField = new JTextField(15);
        panelSongName.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panelSongName.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelSongName.add(new JLabel("Song Name"));
        panelSongName.add(songNameTextField);
    }

    // MODIFIES: this
    // EFFECTS: creates artist name panel to be placed into add panel
    public void makeArtistNamePanel() {
        artistNameTextField = new JTextField(15);
        panelArtistName.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panelArtistName.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelArtistName.add(new JLabel("Artist Name"));
        panelArtistName.add(artistNameTextField);
    }

    // MODIFIES: this
    // EFFECTS: creates genre panel to be placed into add panel
    public void makeGenrePanel() {
        genreTextField = new JTextField(15);
        panelGenre.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panelGenre.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelGenre.add(new JLabel("Genre"));
        panelGenre.add(genreTextField);
    }

    // MODIFIES: this
    // EFFECTS: creates song length panel to be placed into add panel
    public void makeSongLengthPanel() {
        songLengthTextField = new JTextField(15);
        panelSongLength.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panelSongLength.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelSongLength.add(new JLabel("Song Length (in seconds)"));
        panelSongLength.add(songLengthTextField);
    }

    // MODIFIES: this
    // EFFECTS: creates a save and load button on JFrame to give option to save and load data from program
    public void makeSaveAndLoadPanel() {
        JButton saveButton = new JButton("Save playlist");
        JButton loadButton = new JButton("Load playlist");

        saveButton.addActionListener((e) -> savePlaylist());

        loadButton.addActionListener((e) -> loadPlaylist());

        panelLoadAndSave.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panelLoadAndSave.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelLoadAndSave.add(saveButton);
        panelLoadAndSave.add(loadButton);
    }

    // MODIFIES: this
    // EFFECTS: saves the workroom to file
    public void savePlaylist() {
        try {
            jsonWriter.open();
            jsonWriter.write(playlist);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: ");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    public void loadPlaylist() {
        try {
            playlist = jsonReader.read();
            songDisplay();
            displayUpdate();
            frame.pack();
        } catch (IOException e) {
            System.out.println("Unable to read from file: ");
        }
    }

    // MODIFIES: this
    // EFFECTS: combines all the panels to be placed into one area on the JFrame
    public void combinePanels() {
        panelAll.setLayout(new BoxLayout(panelAll, BoxLayout.Y_AXIS));
        panelAll.setSize(new Dimension(0, 0));
        add(panelAll, BorderLayout.SOUTH);
        panelAll.add(panelAdd);
        panelAll.add(panelDelete);
        panelAll.add(panelLoadAndSave);
    }


    public static void main(String[] args) {
        new GUI();
    }

}
