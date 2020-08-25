package xyz.codepunk;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class PostsPageTest extends TestCase {

    @Test
    public void testPostsPage() {
        final ArrayList<Post> posts = new ArrayList<>();
        final String joined = "Tue Oct 29, 2019 07:09";
        final String postDate = "Wed Oct 30, 2019 07:09";
        final int threadId = 454;
        final int totalThreadPosts = 10;
        final Author author = new Author(123, "bob", 1234, joined);
        final Post postOne = new Post(author, postDate, "Oops upside your head");
        final Post postTwo = new Post(author, postDate, "Oops upside your leg");
        posts.add(postOne);
        posts.add(postTwo);
        final PostsPage page = new PostsPage(threadId, posts,  totalThreadPosts);
        assertEquals(totalThreadPosts, page.totalThreadPosts);
        assertEquals(threadId, page.threadId);
        assertEquals(posts, page.posts);
    }
}