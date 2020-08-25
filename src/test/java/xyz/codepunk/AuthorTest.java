package xyz.codepunk;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

public class AuthorTest extends TestCase {

    @Test
    public void constructor() {
        final String joined = "Tue Oct 29, 2019 07:09";
        final Author author = new Author(123, "Mr T", 25, joined);
        assertEquals(123, author.id);
        assertEquals("Mr T", author.username);
        assertSame(author.joined, joined);
        assertEquals(25, author.postCount);
    }
}