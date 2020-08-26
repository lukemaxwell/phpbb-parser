package xyz.codepunk.phpbbparser;

import junit.framework.TestCase;
import org.junit.Test;
import xyz.codepunk.phpbbparser.models.Author;
import xyz.codepunk.phpbbparser.models.Post;

public class PostTest extends TestCase {
    @Test
    public void testPost() {
        final String joined = "Tue Oct 29, 2019 07:09";
        final String postDate = "Wed Dec 30, 2019 07:09";
        final Author author = new Author(123, "Mr T", 25, joined);
        final String content = "Hello world";
        final Post post = new Post(author, postDate, content);
        assertEquals(post.date, postDate);
        assertEquals(post.content, content);
        assertEquals(post.author, author);
    }
}