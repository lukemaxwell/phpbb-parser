package xyz.codepunk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.Assert.*;

public class PHPBBParserTest {
    private String html;
    private final String postHtml = "<div class=\"post-container\">" +
            "<a id=\"unread\" class=\"anchor\"></a>" +
            "<div id=\"p420055\" class=\"post has-profile bg2 unreadpost\">" +
            "<div class=\"inner\">" +
            "<dl class=\"postprofile\" id=\"profile420055\">" +
            "<dt class=\"has-profile-rank has-avatar\">" +
            "<div class=\"avatar-container\">" +
            "<span class=\"avatar\"><img class=\"avatar\" src=\"./download/file.php?avatar=62747_1572418958.jpg\" alt=\"User avatar\" width=\"72\" height=\"100\"></span></div>" +
            "<strong><span style=\"color: #006699;\" class=\"username-coloured\">Bunny</span></strong></dt>" +
            "<dd class=\"profile-rank\">Artist<br><img src=\"./images/ranks/artist.png\" alt=\"Artist\" title=\"Artist\"></dd><dd class=\"profile-rank\">" +
            "Activist" +
            "<br><img src=\"./images/ranks/rank6.png\" alt=\"Activist\" title=\"Activist\">" +
            "</dd>" +
            "<dd class=\"profile-posts\"><strong>Posts:</strong> <a href=\"./search.php?author_id=62747&amp;sr=posts\">377</a></dd><dd class=\"profile-joined\"><strong>Joined:</strong> Tue Oct 29, 2019 07:09</dd>" +
            "<dd class=\"profile-contact\">" +
            "<strong>Contact:</strong>" +
            "<div class=\"dropdown-container dropdown-left\">" +
            "<a href=\"#\" class=\"dropdown-trigger\" title=\"Contact Bunny\">" +
            "<i class=\"icon fa-commenting-o fa-fw icon-lg\" aria-hidden=\"true\"></i><span class=\"sr-only\">Contact Bunny</span>" +
            "</a>" +
            "<div class=\"dropdown\">" +
            "<div class=\"pointer\"><div class=\"pointer-inner\"></div></div>" +
            "<div class=\"dropdown-contents contact-icons\">" +
            "<div>" +
            "<a href=\"./ucp.php?i=pm&amp;mode=compose&amp;action=quotepost&amp;p=420055\" title=\"Send private message\" class=\"last-cell\">" +
            "<span class=\"contact-icon pm-icon\">Send private message</span>" +
            "</a>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</dd>" +
            "" +
            "</dl>" +
            "" +
            "<div class=\"postbody\">" +
            "<div id=\"post_content420055\">" +
            "" +
            "<h5 class=\"first\"><a href=\"#p420055\">Silly sub section.</a></h5>" +
            "" +
            "<ul class=\"post-buttons\">" +
            "<li>" +
            "<a href=\"/app.php/post/420055/report\" title=\"Report this post\" class=\"button button-icon-only\">" +
            "<i class=\"icon fa-exclamation fa-fw\" aria-hidden=\"true\"></i><span class=\"sr-only\">Report this post</span>" +
            "</a>" +
            "</li>" +
            "<li>" +
            "<a href=\"./posting.php?mode=quote&amp;f=110&amp;p=420055\" title=\"Reply with quote\" class=\"button button-icon-only\">" +
            "<i class=\"icon fa-quote-left fa-fw\" aria-hidden=\"true\"></i><span class=\"sr-only\">Quote</span>" +
            "</a>" +
            "</li>" +
            "</ul>" +
            "" +
            "<p class=\"author\">" +
            "<a class=\"unread\" href=\"./viewtopic.php?p=420055#p420055\" title=\"Unread post\">" +
            "<i class=\"icon fa-file fa-fw icon-red icon-md\" aria-hidden=\"true\"></i><span class=\"sr-only\">Unread post</span>" +
            "</a>" +
            "<span class=\"responsive-hide\">by <strong><span style=\"color: #006699;\" class=\"username-coloured\">Bunny</span></strong> » </span>Fri Jun 12, 2020 09:07" +
            "</p>" +
            "<div class=\"content\">Morn merry munchkins, What gives with the bad gateway ?</div>" +
            "<div id=\"list_thanks420055\">" +
            "</div>" +
            "<div id=\"div_post_reput420055\">" +
            "</div>" +
            "" +
            "</div>" +
            "" +
            "</div>" +
            "" +
            "<div class=\"back2top\">" +
            "<a href=\"#top\" class=\"top\" title=\"Top\">" +
            "<i class=\"icon fa-chevron-circle-up fa-fw icon-gray\" aria-hidden=\"true\"></i>" +
            "<span class=\"sr-only\">Top</span>" +
            "</a>" +
            "</div>" +
            "" +
            "</div>" +
            "</div>" +
            "" +
            "<hr class=\"divider\">" +
            "<pre id=\"qr_time420055\" style=\"display: none;\">1591952861</pre>" +
            "<pre id=\"qr_author_p420055\" style=\"display: none;\" data-url=\"\"><span style=\"color: #006699;\" class=\"username-coloured\">Bunny</span></pre>" +
            "<pre id=\"decoded_p420055\" style=\"display: none;\">Morn merry munchkins, What gives with the bad gateway ?</pre>" +
            "</div>";;

    @Before
    public void setUp() throws Exception {
        final ClassLoader classLoader = getClass().getClassLoader();
        final File file = new File(classLoader.getResource("page.html").getFile());
        this.html = Files.readString(file.toPath());
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void main() {
    }

    @Test
    public void parseUrlParams() {
        String urlStr = "/viewtopic.php?f=110&t=30871&start=190";
        HashMap<String, String> params = PHPBBParser.parseUrlParams(urlStr);
        assertTrue(params.containsKey("f") && params.get("f").equals("110"));
        assertTrue(params.containsKey("t") && params.get("t").equals("30871"));
        assertTrue(params.containsKey("start") && params.get("start").equals("190"));
        assertEquals(3, params.keySet().size());
    }

    @Test
    public void parseUrlParam() {
        String urlStr = "/viewtopic.php?f=110&t=30871&start=190";
        assertEquals("30871", PHPBBParser.parseUrlParam(urlStr, "t"));
    }

    @Test
    public void parseThreadId() {
        assertEquals("30871", PHPBBParser.parseThreadId(html));
    }

    @Test
    public void parsePost() {
        final Post post = PHPBBParser.parsePost(postHtml);
        assertEquals("Bunny", post.author.username);
        assertEquals(62747, post.author.id);
        assertEquals("Tue Oct 29, 2019 07:09", post.author.joined);
        assertEquals(377, post.author.postCount);
        assertEquals("Morn merry munchkins, What gives with the bad gateway ?", post.content);
        assertEquals("Fri Jun 12, 2020 09:07", post.date);
    }

    @Test
    public void parsePostElements() {
        assertEquals(8, PHPBBParser.parsePostElements(html).size());
    }


    @Test
    public void parsePosts() {
    }

    @Test
    public void parsePostAuthorUsername() {
        assertEquals("Bunny", PHPBBParser.parsePostAuthorUsername(postHtml));
    }

    @Test
    public void parsePostAuthorId() {
        assertEquals(PHPBBParser.parsePostAuthorId(postHtml), 62747);
    }

    @Test
    public void parsePostAuthorJoinDate() {
        assertEquals("Tue Oct 29, 2019 07:09", PHPBBParser.parsePostAuthorJoinDate(postHtml));
    }

    @Test
    public void parsePostDate() {
        assertEquals("Fri Jun 12, 2020 09:07", PHPBBParser.parsePostDate(postHtml));
    }

    @Test
    public void parsePostAuthorPostCount() {
        assertEquals(377, PHPBBParser.parsePostAuthorPostCount(postHtml));
    }

    @Test
    public void parsePostContent() {
        assertEquals("Morn merry munchkins, What gives with the bad gateway ?", PHPBBParser.parsePostContent(postHtml));
    }
}