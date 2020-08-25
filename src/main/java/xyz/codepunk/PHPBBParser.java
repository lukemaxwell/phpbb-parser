package xyz.codepunk;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PHPBBParser {
    Document doc;
    SimpleDateFormat postDateFormatter = new SimpleDateFormat("E MMM dd, yyyy, HH:mm:ss");


    /**
     * Return hashmap of URL parameters.
     * @param urlStr
     * @return
     */
    public static HashMap<String, String> parseUrlParams(String urlStr) {
        final String queryStr = urlStr.split("\\?")[1];
        final String[] paramsList = queryStr.split("\\&");
        final HashMap<String, String> params = new HashMap<String, String>();
        for(String paramString : paramsList) {
            final String[] tokens = paramString.split("=");
            final String key = tokens[0];
            String val = "";
            for(int i=1; i < tokens.length; i++) {
                val += tokens[i];
            }
            params.put(key, val);
        }
        return params;
    }

    /**
     * Return URL parameter value for `key`
     * @param urlStr
     * @param key
     * @return
     */
    public static String parseUrlParam(String urlStr, String key) {
        final HashMap<String, String> params = parseUrlParams(urlStr);
        return params.get(key);
    }

    public static String parseThreadId(String html) {
        final Document document = Jsoup.parse(html);
        try {
            final Element topicLink = document.selectFirst("h2.topic-title > a");
            final String urlString = topicLink.attr("href");
            return parseUrlParam(urlString, "t");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Post parsePost(String html) {
        final Author author = parsePostAuthor(html);
        final String content = parsePostContent(html);
        final String date = parsePostDate(html);
        return new Post(author, date, content);
    }

    public static Author parsePostAuthor(String html) {
        final String username = parsePostAuthorUsername(html);
        final int id = parsePostAuthorId(html);
        final String joined = parsePostAuthorJoinDate(html);
        final int postCount = parsePostAuthorPostCount(html);
        return new Author(id, username, postCount, joined);
    }

    public static String parsePostContent(String html) {
        final Document document = Jsoup.parse(html);
        final Element element = document.selectFirst("div.content");
        return element.text().strip();
    }

    public static String parsePostDate(String html) {
        final Document document = Jsoup.parse(html);
        final Element element = document.selectFirst("p.author");
        return element.text().split("»")[1].strip();
    }

    public static int parsePostAuthorPostCount(String html) {
        final Document document = Jsoup.parse(html);
        final Element element = document.selectFirst("dd.profile-posts");
        final String postCountStr = element.text().replace("Posts:", "").strip();
        return Integer.parseInt(postCountStr);
    }

    public static String parsePostAuthorUsername(String html) {
        final Document document = Jsoup.parse(html);
        final Element element = document.selectFirst("span.username-coloured");
        return element.text().strip();
    }

    public static int parsePostAuthorId(String html) {
        final Document document = Jsoup.parse(html);
        final Element linkElement = document.selectFirst("dd.profile-posts > a");
        final String href = linkElement.attr("href");
        return Integer.parseInt(parseUrlParam(href, "author_id"));
    }

    public static String parsePostAuthorJoinDate(String html) {
        final Document document = Jsoup.parse(html);
        final Element element = document.selectFirst("dd.profile-joined");
        return element.text().replace("Joined:", "").strip();
    }

    public static ArrayList<Post> parsePosts(String html) {
        final Elements postElements = parsePostElements(html);
        ArrayList<Post> posts = new ArrayList<>();
        for(Element postElement: postElements) {
            posts.add(parsePost(postElement.html()));
        }
        return posts;
    }

    public static Elements parsePostElements(String html) {
        final Document document = Jsoup.parse(html);
        return document.select("div.post");
    }
}
