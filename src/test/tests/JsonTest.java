package tests;

import model.Song;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkSong(String songName, String songArtist, String songGenre, int songLength, Song song) {
        assertEquals(songName, song.getSongName());
        assertEquals(songArtist, song.getArtistName());
        assertEquals(songGenre, song.getGenreType());
        assertEquals(songLength, song.getLengthOfSong());
    }
}
