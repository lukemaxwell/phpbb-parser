package xyz.codepunk.phpbbparser;

import junit.framework.TestCase;
import org.junit.Test;
import xyz.codepunk.phpbbparser.models.Author;
import xyz.codepunk.phpbbparser.models.Post;

import java.util.Optional;

public class PostTest extends TestCase {
    @Test
    public void testPost() {
        final String joined = "Sat Nov 24, 2007 6:48 am";
        final String postDate = "Mon Sep 07, 2020 11:57 am";
        final String html = "<div id=\"p498126\" class=\"post bg2 online\">" +
                "<div class=\"inner\"><span class=\"corners-top\"><span></span></span>" +
                "<div class=\"postbody\">" +
                "<h3><a href=\"#p498126\">Re: Solid Small Circular Bullets not Displaying</a></h3>" +
                "<p class=\"author\"><a href=\"./viewtopic.php?p=498126#p498126\"><img src=\"./styles/prosilver/imageset/icon_post_target.gif\" width=\"11\" height=\"9\" alt=\"Post\" title=\"Post\"></a>by <strong><a href=\"./memberlist.php?mode=viewprofile&amp;u=231\">Bill</a></strong> » Mon Sep 07, 2020 11:57 am </p>" +
                "<div class=\"content\">There's a long discussion of this problem here:<br><br><a href=\"https://forum.openoffice.org/en/forum/viewtopic.php?f=7&amp;t=85669&amp;p=435874\" class=\"postlink\">Corrupted Bullets</a></div>" +
                "<div id=\"sig498126\" class=\"signature\">AOO 4.1.7, LO 6.3.6.2 and LO 6.4.3.2 on Kubuntu 20.04</div>" +
                "</div>" +
                "<dl class=\"postprofile\" id=\"profile498126\">" +
                "<dt>" +
                "<a href=\"./memberlist.php?mode=viewprofile&amp;u=231\">Bill</a>" +
                "</dt>" +
                "<dd>Volunteer</dd>" +
                "<dd>&nbsp;</dd>" +
                "<dd><strong>Posts:</strong> 7833</dd><dd><strong>Joined:</strong> Sat Nov 24, 2007 6:48 am</dd>" +
                "</dl>" +
                "<div class=\"back2top\"><a href=\"#wrap\" class=\"top\" title=\"Top\">Top</a></div>" +
                "<span class=\"corners-bottom\"><span></span></span></div>" +
                "</div>";
        final Author author = new Author(123, "Bill", Optional.of(7833), Optional.of(joined));
        final String content = "There's a long discussion of this problem here:";
        final Post post = new Post(author, postDate, content, html);
        assertEquals(post.date, postDate);
        assertEquals(post.content, content);
        assertEquals(post.author, author);
        assertEquals(post.html, html);
    }
}