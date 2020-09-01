package xyz.codepunk.phpbbparser;

import junit.framework.TestCase;
import org.junit.Test;
import xyz.codepunk.phpbbparser.models.Author;

import java.util.Optional;

public class AuthorTest extends TestCase {

    @Test
    public void testAuthor() {
        final String joined = "Tue Oct 29, 2019 07:09";
        final Author author = new Author(123, "Mr T", Optional.of(25), Optional.of(joined));
        assertEquals(123, author.id);
        assertEquals("Mr T", author.username);
        assertSame(author.joined, joined);
        assertEquals(25, author.postCount);
    }
}