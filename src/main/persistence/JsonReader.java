package persistence;

import model.Event;
import model.EventLog;
import model.Song;
import model.Playlist;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Playlist read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Data loaded to file"));
        return parsePlaylist(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses playlist from JSON object and returns it
    private Playlist parsePlaylist(JSONObject jsonObject) {
        String name = jsonObject.getString("Playlist");
        Playlist pl = new Playlist(name);
        addSongs(pl, jsonObject);
        return pl;
    }

    // MODIFIES: pl
    // EFFECTS: parses songs from JSON object and adds them to workroom
    private void addSongs(Playlist pl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Songs");
        for (Object json : jsonArray) {
            JSONObject nextSong = (JSONObject) json;
            addSong(pl, nextSong);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses song from JSON object and adds it to workroom
    private void addSong(Playlist pl, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String artist = jsonObject.getString("artist");
        String genre = jsonObject.getString("genre");
        int length = jsonObject.getInt("length");
        Song song = new Song(name, artist, genre, length);
        pl.addSong(song);
    }
}
