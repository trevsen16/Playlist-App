package tests;

import model.Playlist;
import model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PlaylistTest {
    private Playlist playlist;
    private Song hotelCali;
    private Song backInBlack;

    @BeforeEach
    void runBefore() {
        playlist = new Playlist("Top Songs");
        hotelCali = new Song("Hotel California", "Eagles", "Rock", 390);
        backInBlack = new Song("Back In Black", "AC/DC", "Rock", 255);
    }

    @Test
    void testConstructor() {
        assertEquals("Top Songs", playlist.getName());
        assertTrue(playlist.getSongs().isEmpty());
    }

    @Test
    void testAddSongs() {
        playlist.addSong(hotelCali);

        assertEquals(hotelCali, playlist.getSongs().get(0));
        assertEquals(1, playlist.getNumSongs());
    }

    @Test
    void testAddMultipleSongs() {
        playlist.addSong(hotelCali);
        playlist.addSong(backInBlack);

        assertEquals(hotelCali, playlist.getSongs().get(0));
        assertEquals(backInBlack, playlist.getSongs().get(1));
        assertEquals(2, playlist.getNumSongs());
    }

    @Test
    void testRemoveSongs() {
        playlist.addSong(hotelCali);
        playlist.addSong(backInBlack);
        playlist.removeSong(hotelCali);

        assertEquals(backInBlack, playlist.getSongs().get(0));
        assertEquals(1, playlist.getNumSongs());
    }

    @Test
    void testRemoveMultipleSongs() {
        playlist.addSong(hotelCali);
        playlist.addSong(backInBlack);
        playlist.removeSong(hotelCali);
        playlist.removeSong(backInBlack);

        assertTrue(playlist.getSongs().isEmpty());
        assertEquals(0, playlist.getNumSongs());
    }

    @Test
    void testGetTopSong() {
        playlist.addSong(hotelCali);
        playlist.addSong(backInBlack);
        hotelCali.playSong();
        hotelCali.playSong();
        backInBlack.playSong();

        assertEquals(hotelCali, playlist.getTopSong());
    }

    @Test
    void testGetTopSongLast() {
        playlist.addSong(hotelCali);
        playlist.addSong(backInBlack);
        hotelCali.playSong();
        backInBlack.playSong();
        backInBlack.playSong();

        assertEquals(backInBlack, playlist.getTopSong());
    }

    @Test
    void testFindSong() {
        playlist.addSong(hotelCali);
        assertEquals(hotelCali, playlist.findSong("Hotel California"));
    }

    @Test
    void testFindSongWithMultiple() {
        playlist.addSong(hotelCali);
        playlist.addSong(backInBlack);
        assertEquals(hotelCali, playlist.findSong("Hotel California"));
    }

    @Test
    void testSetSongs() {
        List<Song> songs = new ArrayList<>();
        songs.add(hotelCali);
        songs.add(backInBlack);
        playlist.setSongs(songs);
        assertEquals(songs, playlist.getSongs());
    }

    @Test
    void testSetNumSongs() {
        playlist.setNumSongs(2);
        assertEquals(2, playlist.getNumSongs());
    }
}
