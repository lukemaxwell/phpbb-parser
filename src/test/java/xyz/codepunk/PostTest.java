package xyz.codepunk;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

public class PostTest extends TestCase {
    @Test
    public void constructor() {
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