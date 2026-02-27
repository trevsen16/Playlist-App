package tests;

import model.Playlist;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import model.Song;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Playlist wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
        try {
            Playlist wr = reader.read();
            assertEquals("My work room", wr.getName());
            assertEquals(0, wr.getNumSongs());
        } catch (IOException e) {
            fail("Couldn't read from the file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralPlaylist.json");
        try {
            Playlist wr = reader.read();
            assertEquals("Top Songs", wr.getName());
            List<Song> songs = wr.getSongs();
            assertEquals(2, songs.size());
            checkSong("Hotel California", "Eagles", "Rock", 150, songs.get(0));
            checkSong("Back in Black", "AC/DC", "Rock", 250, songs.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
