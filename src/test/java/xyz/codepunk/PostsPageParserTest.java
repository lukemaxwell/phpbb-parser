package xyz.codepunk;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import xyz.codepunk.exceptions.ParserException;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PostsPageParserTest extends TestCase {
    private String pageHtml;
    private String postHtml;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        final ClassLoader classLoader = getClass().getClassLoader();
        final File pageHtmlFile = new File(classLoader.getResource("page.html").getFile());
        final File postHtmlFile = new File(classLoader.getResource("post.html").getFile());
        this.pageHtml = Files.readString(pageHtmlFile.toPath());
        this.postHtml = Files.readString(postHtmlFile.toPath());
    }

    @Test
    public void testParseThreadId() throws ParserException {
        assertEquals(154691, PostsPageParser.parseThreadId(pageHtml));
    }

    @Test
    public void testParsePost() throws ParserException {
        final Post post = PostsPageParser.parsePost(postHtml);
        assertEquals("Rémi Denis-Courmont", post.author.username);
        assertEquals(1687, post.author.id);
        assertEquals("07 Jun 2004 16:01", post.author.joined);
        assertEquals(13143, post.author.postCount);
        assertEquals("This is really not a good venue for shell script debug help.", post.content);
        assertEquals("24 Aug 2020 10:37", post.date);
    }

    public void testParsePostAuthor() throws ParserException {
        final Author author = PostsPageParser.parsePostAuthor(postHtml);
        assertEquals("Rémi Denis-Courmont", author.username);
        assertEquals(1687, author.id);
        assertEquals("07 Jun 2004 16:01", author.joined);
    }

    @Test
    public void testParsePostContent() throws ParserException {
        assertEquals("This is really not a good venue for shell script debug help.", PostsPageParser.parsePostContent(postHtml));
    }

    @Test
    public void testParsePostDate() throws ParserException {
        assertEquals("24 Aug 2020 10:37", PostsPageParser.parsePostDate(postHtml));
    }

    @Test
    public void testParsePostAuthorPostCount() throws ParserException {
        assertEquals(13143, PostsPageParser.parsePostAuthorPostCount(postHtml));
    }

    @Test
    public void testParsePostAuthorUsername() throws ParserException {
        assertEquals("Rémi Denis-Courmont", PostsPageParser.parsePostAuthorUsername(postHtml));
    }

    @Test
    public void testParsePostAuthorId() throws ParserException {
        assertEquals(1687, PostsPageParser.parsePostAuthorId(postHtml));
    }

    @Test
    public void testParsePostAuthorJoinDate() throws ParserException {
        assertEquals("07 Jun 2004 16:01", PostsPageParser.parsePostAuthorJoinDate(postHtml));
    }

    public void testParsePosts() throws ParserException {
        final ArrayList<Post> posts = PostsPageParser.parsePosts(pageHtml);
        assertEquals(2, posts.size());
    }

    public void testParseTotalThreadPosts() throws ParserException {
        assertEquals(2, PostsPageParser.parseTotalThreadPosts(pageHtml));
    }

    @Test
    public void testParsePostElements() throws ParserException {
        assertEquals(2, PostsPageParser.parsePostElements(pageHtml).size());
    }

    public void testParse() throws ParserException {
        PostsPage page = PostsPageParser.parse(pageHtml);
        assertEquals(2, page.posts.size());
        assertEquals(154691, page.threadId);
        assertEquals(2, page.totalThreadPosts);
    }
}