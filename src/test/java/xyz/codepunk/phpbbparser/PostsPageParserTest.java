package xyz.codepunk.phpbbparser;

import junit.framework.TestCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import xyz.codepunk.phpbbparser.models.Post;
import xyz.codepunk.phpbbparser.models.PostsPage;
import xyz.codepunk.phpbbparser.exceptions.ParserException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;


public class PostsPageParserTest extends TestCase {
    private int dynamicTestCount = 4;

    /**
     * Return test resource file as string
     *
     * @param name name of file
     * @return String
     * @throws IOException if file does not exist
     */
    public String getResourceAsString(String name) throws IOException {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File resourceFile = new File(classLoader.getResource(name).getFile());
        return Files.readString(resourceFile.toPath());
    }

    /**
     * Dynamically generate tests for all html test resources.
     *
     * Creates tests for resources in range 1 < dynamicTestCount
     *
     * @return Collection of dynamic tests
     * @throws IOException if file for iteration index cannot be found
     * @throws ParserException when required parsing functions fail
     */
    @TestFactory
    Collection<DynamicTest> testPostsPageParser() throws IOException, ParserException {
        final Collection<DynamicTest> tests = new ArrayList<>();
        for(int i=1; i < dynamicTestCount+1; i++) {
            final String pageHtml = getResourceAsString(String.format("page-%d.html", i));
            final PostsPage postsPage = PostsPage.fromJson(getResourceAsString(String.format("page-%d.json", i)));
            // Create tests...
            // ...

            // Parse threadId
            tests.add(dynamicTest(String.format("parseThreadId-%d", i), () -> assertEquals(postsPage.threadId, PostsPageParser.parseThreadId(pageHtml))));
            // Parse threadId
            tests.add(dynamicTest(String.format("parseThreadTitle-%d", i), () -> assertEquals(postsPage.threadTitle, PostsPageParser.parseThreadTitle(pageHtml))));
            // Parse total thread post count
            tests.add(dynamicTest(String.format("parseTotalThreadPosts-%d", i), () -> assertEquals(postsPage.totalThreadPosts, PostsPageParser.parseTotalThreadPosts(pageHtml))));
            // Posts length
            tests.add(dynamicTest(String.format("postsLength-%d", i), () -> assertEquals(postsPage.posts.size(), PostsPageParser.parse(pageHtml).posts.size())));
            // Post parsing
            ArrayList<Post> parsedPosts = PostsPageParser.parsePosts(pageHtml);
            for(int j=0; j<parsedPosts.size(); j++) {
                Post expectedPost = postsPage.posts.get(j);
                Post parsedPost = parsedPosts.get(j);
                // Post date
                tests.add(dynamicTest(String.format("parsePostDate-%d", i), () -> assertEquals(expectedPost.date, parsedPost.date)));
                // Post content
                tests.add(dynamicTest(String.format("parsePostContent-%d", i), () -> assertEquals(expectedPost.content, parsedPost.content)));
                // Post Author username
                tests.add(dynamicTest(String.format("parsePostAuthorUsername-%d", i), () -> assertEquals(expectedPost.author.username, parsedPost.author.username)));
                // Post Author ID
                tests.add(dynamicTest(String.format("parsePostAuthorUsername-%d", i), () -> assertEquals(expectedPost.author.id, parsedPost.author.id)));
                // Post Author join date
                tests.add(dynamicTest(String.format("parsePostAuthorJoined-%d", i), () -> assertEquals(expectedPost.author.joined, parsedPost.author.joined)));
                // Post Author post count
                tests.add(dynamicTest(String.format("parsePostAuthorPostCount-%d", i), () -> assertEquals(expectedPost.author.postCount, parsedPost.author.postCount)));
            }
       }
        return tests;
    }
}