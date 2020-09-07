package xyz.codepunk.phpbbparser.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;


public class Post {
    public Author author;
    public String date;
    public String content;
    public String html;


    public Post(Author author, String date, String content, String html) {
        this.author = author;
        this.date = date;
        this.content = content;
        this.html = html;
    }

    /**
     * Factory method for initializing a Post from a JSON string.
     *
     * @param jsonString JSON representation of Post
     * @return post instance
     */
    public static Post fromJson(String jsonString) {
        JsonObject obj = JsonParser.parseString(jsonString).getAsJsonObject();
        Author author = Author.fromJson(obj.get("author").getAsJsonObject().toString());
            final Post post = new Post(author, obj.get("date").getAsString(), obj.get("content").getAsString(), obj.get("html").getAsString());
        return post;
    }

    /**
     * Return Post instance as HashMap
     *
     * @return HashMap representation of Post
     */
    public HashMap<String, Object> toHashmap() {
        HashMap<String, Object> post = new HashMap<>();
        post.put("author", this.author.toHashmap());
        post.put("date", this.date);
        post.put("content", this.content);
        post.put("html", this.html);
        return post;
    }

    /**
     * Return Post instance as JSON string
     *
     * @return JSON string representation of Post
     */
    public String tojson() {
        Gson gson = new Gson();
        HashMap<String, Object> post = toHashmap();
        return gson.toJson(post, post.getClass());
    }
}
