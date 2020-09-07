package xyz.codepunk.phpbbparser.models;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class PostsPage {
    public ArrayList<Post> posts;
    public Optional<Integer> totalThreadPosts;
    public int threadId;
    public String threadTitle;
    public String html;

    public PostsPage(int threadId, String threadTitle, ArrayList<Post> posts, Optional<Integer> totalThreadPosts, String html) {
        this.threadId = threadId;
        this.threadTitle = threadTitle;
        this.posts = posts;
        this.totalThreadPosts = totalThreadPosts;
        this.html = html;
    }

    /**
     * Factory method for initializing an PostsPage from a JSON string.
     *
     * @param jsonString JSON representation of PostsPage
     * @return PostsPage instance
     */
    public static PostsPage fromJson(String jsonString) {
        JsonObject obj = JsonParser.parseString(jsonString).getAsJsonObject();
        ArrayList<Post> posts = new ArrayList<>();
        Optional<Integer> totalThreadPosts;

        try {
            totalThreadPosts = Optional.of(obj.get("totalThreadPosts").getAsInt());
        } catch(Exception e) {
            totalThreadPosts = Optional.empty();
        }

        for(Object post: obj.get("posts").getAsJsonArray()) {
            posts.add(Post.fromJson(post.toString()));
        }
        final PostsPage postsPage = new PostsPage(obj.get("threadId").getAsInt(), obj.get("threadTitle").getAsString(), posts, totalThreadPosts, obj.get("html").getAsString());
        return postsPage;
    }

    /**
     * Return PostsPage instance as HashMap
     *
     * @return HashMap representation of PostsPage
     */
    public HashMap<String, Object> toHashmap() {
        HashMap<String, Object> postsPage = new HashMap<>();
        postsPage.put("threadId", this.threadId);
        postsPage.put("threadTitle", this.threadTitle);
        if(this.totalThreadPosts.isPresent()) {
            postsPage.put("totalThreadPosts", this.totalThreadPosts.get());
        }
        ArrayList<HashMap<String, Object>> posts = new ArrayList<>();
        for(Post post: this.posts) {
            posts.add(post.toHashmap());
        }
        return postsPage;
    }

    /**
     * Return PostsPage instance as JSON string
     *
     * @return JSON string representation of PostsPage
     */
    public String tojson() {
        Gson gson = new Gson();
        HashMap<String, Object> post = toHashmap();
        return gson.toJson(post, post.getClass());
    }

}
