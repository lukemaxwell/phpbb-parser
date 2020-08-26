package xyz.codepunk.phpbbparser.models;

import java.util.ArrayList;

public class PostsPage {
    public ArrayList<Post> posts;
    public int totalThreadPosts;
    public int threadId;

    public PostsPage(int threadId, ArrayList<Post> posts, int totalThreadPosts) {
        this.threadId = threadId;
        this.posts = posts;
        this.totalThreadPosts = totalThreadPosts;
    }

}
