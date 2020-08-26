package xyz.codepunk.phpbbparser;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import xyz.codepunk.phpbbparser.PostsPageParser;
import xyz.codepunk.phpbbparser.exceptions.ParserException;
import xyz.codepunk.phpbbparser.models.Author;
import xyz.codepunk.phpbbparser.models.Post;
import xyz.codepunk.phpbbparser.models.PostsPage;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class PostsPageParserTestTwo extends TestCase {
    private String pageHtml;
    private String postHtml;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        final ClassLoader classLoader = getClass().getClassLoader();
        final File pageHtmlFile = new File(classLoader.getResource("page-two.html").getFile());
        final File postHtmlFile = new File(classLoader.getResource("post-two.html").getFile());
        this.pageHtml = Files.readString(pageHtmlFile.toPath());
        this.postHtml = Files.readString(postHtmlFile.toPath());
    }

    @Test
    public void testParseThreadId() throws ParserException {
        assertEquals(102832, PostsPageParser.parseThreadId(pageHtml));
    }

    @Test
    public void testParsePost() throws ParserException {
        final Post post = PostsPageParser.parsePost(postHtml);
        assertEquals("RoryOF", post.author.username);
        assertEquals(16605, post.author.id);
        assertEquals("Sat Jan 31, 2009 9:30 pm", post.author.joined);
        assertEquals(31308, post.author.postCount);
        assertEquals("is it 2020, or \\20\\20?", post.content);
        assertEquals("Fri Aug 21, 2020 3:59 pm", post.date);
    }

    public void testParsePostAuthor() throws ParserException {
        final Author author = PostsPageParser.parsePostAuthor(postHtml);
        assertEquals("RoryOF", author.username);
        assertEquals(16605, author.id);
        assertEquals("Sat Jan 31, 2009 9:30 pm", author.joined);
    }

    @Test
    public void testParsePostContent() throws ParserException {
        assertEquals("is it 2020, or \\20\\20?", PostsPageParser.parsePostContent(postHtml));
    }

    @Test
    public void testParsePostDate() throws ParserException {
        assertEquals("Fri Aug 21, 2020 3:59 pm", PostsPageParser.parsePostDate(postHtml));
    }

    @Test
    public void testParsePostAuthorPostCount() throws ParserException {
        assertEquals(31308, PostsPageParser.parsePostAuthorPostCount(postHtml));
    }

    @Test
    public void testParsePostAuthorUsername() throws ParserException {
        assertEquals("RoryOF", PostsPageParser.parsePostAuthorUsername(postHtml));
    }

    @Test
    public void testParsePostAuthorId() throws ParserException {
        assertEquals(16605, PostsPageParser.parsePostAuthorId(postHtml));
    }

    @Test
    public void testParsePostAuthorJoinDate() throws ParserException {
        assertEquals("Sat Jan 31, 2009 9:30 pm", PostsPageParser.parsePostAuthorJoinDate(postHtml));
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
        assertEquals(102832, page.threadId);
        assertEquals(2, page.totalThreadPosts);
    }
}