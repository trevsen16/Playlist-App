package persistence;

import org.json.JSONObject;

// interface for json objects classes to use
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
