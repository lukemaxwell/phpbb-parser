package xyz.codepunk.phpbbparser;

import junit.framework.TestCase;
import org.junit.Test;
import xyz.codepunk.phpbbparser.models.Author;
import xyz.codepunk.phpbbparser.models.Post;
import xyz.codepunk.phpbbparser.models.PostsPage;

import java.util.ArrayList;
import java.util.Optional;

public class PostsPageTest extends TestCase {

    @Test
    public void testPostsPage() {
        final ArrayList<Post> posts = new ArrayList<>();
        final String joined = "Tue Oct 29, 2019 07:09";
        final String postDate = "Wed Oct 30, 2019 07:09";
        final int threadId = 454;
        final Optional<Integer> totalThreadPosts = Optional.of(10);
        //final int totalThreadPosts = 10;
        final Author author = new Author(123, "bob", Optional.of(1234), Optional.of(joined));
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