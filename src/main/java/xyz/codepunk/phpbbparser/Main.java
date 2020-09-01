package xyz.codepunk.phpbbparser;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import xyz.codepunk.phpbbparser.exceptions.ParserException;
import xyz.codepunk.phpbbparser.exceptions.TranslationException;
import xyz.codepunk.phpbbparser.models.PostsPage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;;
import java.util.*;
import java.util.zip.GZIPInputStream;


public class Main {
    public static ArrayList<String> urls = new ArrayList<>(
            Arrays.asList(
                    "https://forum.openoffice.org/en/forum/viewtopic.php?f=5&t=102832",
                    "https://forum.joomla.org/viewtopic.php?f=706&t=976319",
                    "https://forum.ubuntu-it.org/viewtopic.php?f=17&t=637551",
                    "http://www.pixelmator.com/community/viewtopic.php?f=18&t=17730",
                    "https://forum.videolan.org/viewtopic.php?f=12&t=154744",
                    "https://forums.virtualbox.org/viewtopic.php?f=35&t=96608",
                    "http://forums.mozillazine.org/viewtopic.php?f=7&t=3034017",
                    "http://forums.debian.net/viewtopic.php?f=17&t=147212",
                    "https://www.raspberrypi.org/forums/viewtopic.php?f=63&t=187256",
                    "https://adblockplus.org/forum/viewtopic.php?f=10&t=75244",
                    "https://arstechnica.com/civis/viewtopic.php?f=6&t=1444785"
            )
    );


    public static String fetchHtml(String url) throws IOException, InterruptedException {

        CloseableHttpClient client = HttpClients.createDefault();
        final HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36");
        final HttpResponse response = client.execute(request);
        final int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    }

    public static void main(String[] args) throws ParserException, IOException, InterruptedException, TranslationException {
        for(String url: urls) {
            System.out.println(url);
            final String html = fetchHtml(url);
            final PostsPage page = PostsPageParser.parse(html);
            System.out.println("OK");
        }

    }
}
