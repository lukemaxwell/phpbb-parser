package xyz.codepunk.phpbbparser.models;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Optional;

public class Author {
    public int id;
    public String username;
    public Optional<Integer> postCount;
    public Optional<String> joined;

    public Author(int id, String username, Optional<Integer> postCount, Optional<String> joined) {
        this.id = id;
        this.username = username;
        this.postCount = postCount;
        this.joined = joined;
    }

    /**
     * Factory method for initializing an Author from a JSON string.
     *
     * @param jsonString JSON representation of Author
     * @return Author instance
     */
    public static Author fromJson(String jsonString) {
        JsonObject obj = JsonParser.parseString(jsonString).getAsJsonObject();
        Optional<Integer> postCount;
        Optional<String> joined;

        try {
            postCount = Optional.of(obj.get("postCount").getAsInt());
        } catch(Exception e) {
            postCount = Optional.empty();
        }

        try {
            joined = Optional.of(obj.get("joined").getAsString());
        } catch(Exception e) {
            joined = Optional.empty();
        }

        final Author author = new Author(
                obj.get("id").getAsInt(),
                obj.get("username").getAsString(),
                postCount,
                joined);
        return author;
    }

    /**
     * Return Author instance as HashMap
     *
     * @return HashMap representation of Author
     */
    public HashMap<String, Object> toHashmap() {
        HashMap<String, Object> author = new HashMap<>();
        author.put("id", this.id);
        author.put("username", this.username);
        if(this.postCount.isPresent()) {
            author.put("postCount", this.postCount.get());
        }
        if(this.joined.isPresent()) {
            author.put("joined", this.joined.get());
        }
        return author;
    }

    /**
     * Return Author instance as JSON string
     *
     * @return JSON string representation of Author
     */
    public String tojson() {
        Gson gson = new Gson();
        HashMap<String, Object> author = toHashmap();
        return gson.toJson(author, author.getClass());
    }

}
