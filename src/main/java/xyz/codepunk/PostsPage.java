package xyz.codepunk;

import java.util.ArrayList;

public class PostsPage {
    ArrayList<Post> posts;
    int totalThreadPosts;
    int threadId;

    PostsPage(int threadId, ArrayList<Post> posts, int totalThreadPosts) {
        this.threadId = threadId;
        this.posts = posts;
        this.totalThreadPosts = totalThreadPosts;
    }

    public static boolean isValid(String html) {
        return true;
    }
}
