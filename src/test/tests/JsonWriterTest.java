package tests;

import model.Playlist;
import model.Song;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Playlist wr = new Playlist("My work room");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            wr = reader.read();
            assertEquals("My work room", wr.getName());
            assertEquals(0, wr.getNumSongs());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPlaylist() {
        try {
            Playlist wr = new Playlist("Top Songs");
            wr.addSong(new Song("Hotel California", "Eagles", "Rock", 150));
            wr.addSong(new Song("Back in Black", "AC/DC", "Rock", 250));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPlaylist.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPlaylist.json");
            wr = reader.read();
            assertEquals("Top Songs", wr.getName());
            List<Song> songs = wr.getSongs();
            assertEquals(2, songs.size());
            checkSong("Hotel California", "Eagles", "Rock", 150, songs.get(0));
            checkSong("Back in Black", "AC/DC", "Rock", 250, songs.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
