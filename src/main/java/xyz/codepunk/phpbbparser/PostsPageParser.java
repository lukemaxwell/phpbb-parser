package xyz.codepunk.phpbbparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xyz.codepunk.phpbbparser.common.BaseParser;
import xyz.codepunk.phpbbparser.exceptions.ParserException;
import xyz.codepunk.phpbbparser.models.Author;
import xyz.codepunk.phpbbparser.models.Post;
import xyz.codepunk.phpbbparser.models.PostsPage;

import java.util.ArrayList;
import java.util.Arrays;


public class PostsPageParser extends BaseParser {
    final static ArrayList<String> authorXpaths = new ArrayList<>(Arrays.asList("span.username-coloured", "a.username-coloured", "a.username", "p a[href*=\"memberlist\"]"));
    final static ArrayList<String> threadIdXpaths = new ArrayList<>(Arrays.asList("h2.topic-title > a", "div.topic-actions > div > div > a"));
    final static ArrayList<String> authorIdXpaths = new ArrayList<>(Arrays.asList("dd.profile-posts > a", "p a[href*=\"memberlist\"]"));
    /**
     * Parse the thread ID.
     *
     * @param html the full page html
     * @return the thread ID
     * @throws ParserException
     */
    public static int parseThreadId(String html) throws ParserException {
        final Document document = Jsoup.parse(html);
        for(String xpath: threadIdXpaths) {
            try {
                final Element topicLink = document.selectFirst(xpath);
                final String urlString = topicLink.attr("href");
                return Integer.parseInt(parseUrlParam(urlString, "t"));
            } catch(Exception e) {
                // Do nothing, throw exception after loop
            }
        }
        throw new ParserException("Could not parse thread ID");
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
        final Document document = Jsoup.parse(html);
        final Elements elements = document.select("dl.postprofile > dd");
        for(Element element: elements) {
            final String text = element.text();
            if(text.contains("Posts")) {
                return Integer.parseInt(element.text().replace("Posts:", "").strip());
            }
        }
        throw new ParserException("Could not parse author posst count");
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

    public static int parseAuthorIdFromUrl(String url) throws ParserException {
        String authorId;
        if(url.contains("memberlist")) {
            authorId = parseUrlParam(url, "u");
        } else {
            authorId = parseUrlParam(url, "author_id");
        }
       if(authorId == null) {
           throw new ParserException("Could not parse author ID from url");
       }
       return Integer.parseInt(authorId);
    }

    /**
     * Parse post author ID.
     *
     * @param html the post html
     * @return the integer ID
     * @throws {@link ParserException}
     */
    public static int parsePostAuthorId(String html) throws ParserException {
        final Document document = Jsoup.parse(html);
        for(String xpath: authorIdXpaths) {
            try {
                final Element element = document.selectFirst(xpath);
                final Element linkElement = document.selectFirst(xpath);
                final String href = linkElement.attr("href");
                return parseAuthorIdFromUrl(href);
            } catch(Exception e) {
                // Do nothing, throw exception after loop
            }
        }
        throw new ParserException("Could not parse author ID");
    }


    /**
     * Parse post author join date.
     *
     * @param html the post html
     * @return the author join date as string
     * @throws {@link ParserException}
     */
    public static String parsePostAuthorJoinDate(String html) throws ParserException {
        final Document document = Jsoup.parse(html);
        final Elements elements = document.select("dl.postprofile > dd");
        for(Element element: elements) {
            final String text = element.text();
            if(text.contains("Joined")) {
                return element.text().replace("Joined:", "").strip();
            }
        }
        throw new ParserException("Could not parse author join date");
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
