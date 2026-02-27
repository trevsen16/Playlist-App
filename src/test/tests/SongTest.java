package tests;

import model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SongTest {
    private Song song;
    private Song backInBlack;

    @BeforeEach
    void runBefore() {
        song = new Song("Hotel California", "Eagles", "Rock", 390);
        backInBlack = new Song("Back In Black", "AC/DC", "Rock", 255);
    }

    @Test
    void testConstructor() {
        assertEquals("Hotel California", song.getSongName());
        assertEquals("Eagles", song.getArtistName());
        assertEquals("Rock", song.getGenreType());
        assertEquals(390, song.getLengthOfSong());
    }

    @Test
    void testPlaySong() {
        song.playSong();
        assertEquals(1, song.getStreams());
    }

    @Test
    void testPlaySongMultiple() {
        song.playSong();
        song.playSong();
        song.playSong();
        song.playSong();
        backInBlack.playSong();
        assertEquals(4, song.getStreams());
        assertEquals(1, backInBlack.getStreams());
    }

    @Test
    void testViewSong() {
        assertEquals("\tSong Name: " + song.getSongName() + "\n\tSong Artist: " + song.getArtistName() + "\n\tSong Genre: "
                + song.getGenreType() + "\n\tSong Length (in seconds): " + song.getLengthOfSong(), song.viewSong(song), song.viewSong(song));
    }

    @Test
    void testPrintSong() {
        assertEquals("\n\tSong Name: " + song.getSongName() + " \n\tSong Artist: "
                + song.getArtistName() + " \n\tSong Genre: " + song.getGenreType()
                + " \n\tSong Length (in seconds): " + song.getLengthOfSong(), song.printSong());
    }
}
