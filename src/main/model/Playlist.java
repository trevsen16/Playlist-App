package model;

import persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// represents a playlist with a list of songs in it
public class Playlist implements Writable {
    private int numSongs;   //number of songs in the playlist
    private final String name;    //name of the playlist
    private List<Song> songs;

    public Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    // MODIFIES: This
    // EFFECTS: adds a song to the playlist
    public void addSong(Song song) {
        songs.add(song);
        numSongs++;
        EventLog.getInstance().logEvent(new Event("Added " + song.getSongName() + " to the playlist"));
    }

    // MODIFIES: This
    // EFFECTS: removes a song from the playlist
    public void removeSong(Song song) {
        numSongs--;
        songs.remove(song);
        EventLog.getInstance().logEvent(new Event("Removed " + song.getSongName() + " from the playlist"));
    }

    // EFFECTS: gets the song that you have played the most amount of times.
    //          if it is equal to another, the first song is produced.
    public Song getTopSong() {
        Song topSong = songs.get(0);

        for (Song song : songs) {
            if (topSong.getStreams() < song.getStreams()) {
                topSong = song;
            }
        }
        return topSong;
    }

    // EFFECTS: Will find and return a song given the song name
    public Song findSong(String songName) {
        Song findSong = null;
        for (Song song : songs) {
            if (Objects.equals(song.getSongName(), songName)) {
                findSong = song;
            }
        }
        return findSong;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Playlist", name);
        json.put("Songs", songsToJson());
        return json;
    }

    // EFFECTS: returns songs in this workroom as a JSON array
    private JSONArray songsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Song s : songs) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: sets the given songs to be the playlists songs
    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public String getName() {
        return name;
    }

    public int getNumSongs() {
        return numSongs;
    }

    public void setNumSongs(int songs) {
        numSongs = songs;
    }
}
