package xyz.codepunk.phpbbparser;

import com.google.gson.Gson;
import xyz.codepunk.phpbbparser.exceptions.ParserException;
import xyz.codepunk.phpbbparser.models.PostsPage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static ArrayList<String> urls = new ArrayList<>(
            Arrays.asList(
                    "https://forum.openoffice.org/en/forum/viewtopic.php?f=5&t=102832",
                    //"https://forum.opencart.com/viewtopic.php?f=10&t=157098",
                    "https://forum.ubuntu-it.org/viewtopic.php?f=17&t=637551",
                    "http://www.pixelmator.com/community/viewtopic.php?f=18&t=17730"
            )
    );
    public static String fetchHtml(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static void main(String[] args) throws ParserException, IOException, InterruptedException {
        for(String url: urls) {
            final String html = fetchHtml(url);
            final PostsPage page = PostsPageParser.parse(html);
            System.out.println(url + ": OK");
        }

    }
}
