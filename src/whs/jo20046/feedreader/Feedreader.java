package whs.jo20046.feedreader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Feedreader {

    private final ArrayList<String> urls;
    private String htmlContent = "";
    private String rssContent = "";
    private String rssFeedURL = "";
    private boolean connectionOK = false;

    public Feedreader(ArrayList<String> urls) {
        this.urls = urls;
    }

    /**
     * Get the articles from the RSS feed of the specified sources (Data.URLs)
     * @return full list of articles (as HTML with links on the titles)
     */
    public String getRssContent() {

        StringBuilder result = new StringBuilder();

        for (String urlString : urls) {

            result.append("<h3>").append("Schlagzeilen von ").append(urlString).append("</h3>");

            try {

                // Mit gegebener URL verbinden, RSS-Feed suchen, mit RSS-Feed verbinden und speichern
                getHttpContent(urlString, true);
                if (!connectionOK) throw new IOException("Couldn't connect to " + urlString);
                getRssURL(htmlContent);
                if (rssFeedURL.isEmpty()) throw new IOException("No RSS Feed found for specified URL");
                getHttpContent(rssFeedURL, false);
                if (!connectionOK) throw new IOException("Couldn't connect to RSS Feed under " + rssFeedURL);

                // RSS-Feed mit Parser durchlaufen und Schlagzeilen auf Bildschirm ausgeben
                Parser parser = new Parser(rssContent);
                ArrayList<Article> articles = parser.getArticles();
                for (Article article : articles) {
                    result.append("<a href=\"").append(article.getLink()).append("\">").append(article.getTitle()).append("</a><br><br>");
                }
                result.append("<br><br><br>");

            } catch (IOException e) {
                e.printStackTrace();
                result.append("Keinen RSS-Feed auf ").append(urlString).append(" gefunden.").append("<br><br><br>");
            }
        }
        return result.toString();
    }

    /**
     * Get the HTML representation of a specified website by connecting via HTTP (or HTTPS).
     *
     * @param urlInput the URL to connect to
     * @param isHTML   true for HTML, false for RSS
     */
    private void getHttpContent(String urlInput, boolean isHTML) {

        try {
            connectionOK = false;
            HttpURLConnection huc = buildHttpConnection(urlInput);
            StringBuilder content = new StringBuilder();

            if (huc.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println(huc.getResponseMessage());
                throw new IOException();
            } else {
                connectionOK = true;
                System.out.println(huc.getResponseMessage());
                InputStream is = huc.getInputStream();
                Scanner in = new Scanner(is);
                for (String line; in.hasNextLine(); ) {
                    line = in.nextLine();
                    content.append(line).append('\n');
                }
            }
            huc.disconnect();
            if (isHTML)
                htmlContent = content.toString();
            else
                rssContent = content.toString();
        } catch (IOException e) {
            connectionOK = false;
        }
    }

    /**
     * Build up a HTTP(S) connection to the specified URL with the GET command. If necessary, adds "http://" or "https://" the URL.
     *
     * @param urlInput the URL to connect to
     * @return prepared HttpURLConnection object
     */
    private HttpURLConnection buildHttpConnection(String urlInput) throws IOException {

        if (!urlInput.startsWith("https://")) urlInput = "https://" + urlInput;
        URL url = new URL(urlInput);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        huc.setRequestMethod("GET");

        return huc;
    }

    /**
     * Find the RSS feed url in a HTML representation of a website
     *
     * @param html the HTML content
     */
    private void getRssURL(String html) {
        Scanner scanner = new Scanner(html);
        for (String line; scanner.hasNextLine(); ) {
            line = scanner.nextLine();
            if (line.matches(".*href=\".*rss.*")) {
                rssFeedURL = line.substring(line.indexOf("href=")).split("\"")[1];
                break;
            }
        }
        scanner.close();
    }
}
