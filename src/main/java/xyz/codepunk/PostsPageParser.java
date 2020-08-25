package xyz.codepunk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xyz.codepunk.exceptions.ParserException;

import java.util.ArrayList;
import java.util.Arrays;


public class PostsPageParser extends BaseParser {
    final static ArrayList<String> authorXpaths = new ArrayList<>(Arrays.asList("span.username-coloured", "a.username-coloured", "a.username"));

    /**
     * Parse the thread ID.
     *
     * @param html the full page html
     * @return the thread ID
     * @throws ParserException
     */
    public static int parseThreadId(String html) throws ParserException {
        final Document document = Jsoup.parse(html);
        try {
            final Element topicLink = document.selectFirst("h2.topic-title > a");
            final String urlString = topicLink.attr("href");
            return Integer.parseInt(parseUrlParam(urlString, "t"));
        } catch (Exception e) {
            throw new ParserException("Could not parse thread ID");
        }
    }

    /**
     * Parse a post.
     *
     * @param html the post html
     * @return the {@link Post}
     * @throws ParserException
     */
    public static Post parsePost(String html) throws ParserException {
        try {
            final Author author = parsePostAuthor(html);
            final String content = parsePostContent(html);
            final String date = parsePostDate(html);
            return new Post(author, date, content);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParserException("Could not parse post");
        }

    }

    /**
     * Parse post author.
     *
     * @param html the post html
     * @return an {@link Author} instance
     * @throws ParserException
     */
    public static Author parsePostAuthor(String html) throws ParserException {
        try {
            final String username = parsePostAuthorUsername(html);
            final int id = parsePostAuthorId(html);
            final String joined = parsePostAuthorJoinDate(html);
            final int postCount = parsePostAuthorPostCount(html);
            return new Author(id, username, postCount, joined);
        } catch(Exception e) {
            e.printStackTrace();
            throw new ParserException("Could not parse post author");
        }

    }

    /**
     * Parse post content.
     *
     * @param html the post htmll
     * @return the post content text
     * @throws ParserException
     */
    public static String parsePostContent(String html) throws ParserException {
       try {
           final Document document = Jsoup.parse(html);
           final Element element = document.selectFirst("div.content");
           return element.text().strip();
       } catch(Exception e) {
           throw new ParserException("Could not parse post content");
       }
    }

    /**
     * Parse post date.
     *
     * @param html the post html
     * @return the post date as a string
     * @throws ParserException
     */
    public static String parsePostDate(String html) throws ParserException {
      try {
          final Document document = Jsoup.parse(html);
          final Element element = document.selectFirst("p.author");
          return element.text().split("»")[1].strip();
      } catch(Exception e) {
          throw new ParserException("Could not parse post date");
      }
    }

    /**
     * Parse post author post count.
     *
     * @param html the post html
     * @return the integer post count
     * @throws {@link ParserException}
     */
    public static int parsePostAuthorPostCount(String html) throws ParserException {
        try {
            final Document document = Jsoup.parse(html);
            final Element element = document.selectFirst("dd.profile-posts");
            final String postCountStr = element.text().replace("Posts:", "").strip();
            return Integer.parseInt(postCountStr);
        } catch(Exception e) {
            throw new ParserException("Could not parse author post count");
        }
    }

    /**
     * Parse post author username.
     *
     * @param html the post html
     * @return the author username
     * @throws {@link ParserException}
     */
    public static String parsePostAuthorUsername(String html) throws ParserException {

        final Document document = Jsoup.parse(html);
        for(String xpath: authorXpaths) {
            try {
                final Element element = document.selectFirst(xpath);
                return element.text().strip();
            } catch(Exception e) {
                // Do nothing, throw exception after loop
            }
        }
        throw new ParserException("Could not parse author username");
    }

    /**
     * Parse post author ID.
     *
     * @param html the post html
     * @return the integer ID
     * @throws {@link ParserException}
     */
    public static int parsePostAuthorId(String html) throws ParserException {
        try {
            final Document document = Jsoup.parse(html);
            final Element linkElement = document.selectFirst("dd.profile-posts > a");
            final String href = linkElement.attr("href");
            return Integer.parseInt(parseUrlParam(href, "author_id"));
        } catch(Exception e) {
            throw new ParserException("Could not parse author ID");
        }
    }

    /**
     * Parse post author join date.
     *
     * @param html the post html
     * @return the author join date as string
     * @throws {@link ParserException}
     */
    public static String parsePostAuthorJoinDate(String html) throws ParserException {
        try {
            final Document document = Jsoup.parse(html);
            final Element element = document.selectFirst("dd.profile-joined");
            return element.text().replace("Joined:", "").strip();
        } catch(Exception e) {
            throw new ParserException("Could not parse author join date");
        }
    }

    /**
     * Parse posts.
     *
     * @param html the full page html
     * @return ArrayList of {@link Post} objects
     * @throws ParserException
     */
    public static ArrayList<Post> parsePosts(String html) throws ParserException {
       try {
           final Elements postElements = parsePostElements(html);
           ArrayList<Post> posts = new ArrayList<>();
           for(Element postElement: postElements) {
               posts.add(parsePost(postElement.outerHtml()));
           }
           return posts;
       } catch(Exception e) {
           throw new ParserException("Could not parse posts");
       }
    }

    /**
     * Parse total thread posts.
     *
     * @param html the full page html
     * @return the integer post count
     * @throws {@link ParserException}
     */
    public static int parseTotalThreadPosts(String html) throws ParserException {
        try {
            final Document document = Jsoup.parse(html);
            final String elText = document.select("div.pagination").text().split("posts")[0].strip();
            return Integer.parseInt(elText);
        } catch(Exception e) {
            throw new ParserException("Could not parse total thread posts");
        }
    }

    /**
     * Parse post elements.
     *
     * @param html the full page html
     * @return instance of jsoup {@link Elements}
     * @throws {@link ParserException}
     */
    public static Elements parsePostElements(String html) throws ParserException {
        try {
            final Document document = Jsoup.parse(html);
            return document.select("div.post");
        } catch(Exception e) {
            throw new ParserException("Could not parse post elements");
        }
    }

    /**
     * Parse page of posts.
     *
     * @param html the full page html
     * @return an instance of {@link PostsPage}
     * @throws ParserException
     */
    public static PostsPage parse(String html) throws ParserException {
        int threadId = parseThreadId(html);
        int totalThreadPosts = parseTotalThreadPosts(html);
        ArrayList<Post> posts = parsePosts(html);
        return new PostsPage(threadId, posts, totalThreadPosts);
    }
}
