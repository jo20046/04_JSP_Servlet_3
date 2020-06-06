package whs.jo20046.controller;

import whs.jo20046.beans.Data;
import whs.jo20046.feedreader.Feedreader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ControllerHelper extends HelperBase {

    protected Data data = new Data();
    private boolean allConnectionsOk;

    public ControllerHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void doPost() throws IOException {

        // TODO: NullPointer an dieser Stelle wenn man zu oft sumbittet (?)
        if (request.getSession().getAttribute("helper") == null) {
            request.getSession().setAttribute("helper", this);
        }

        if (request.getSession().getAttribute("Data") == null) {
            request.getSession().setAttribute("Data", data);
        }

        checkURLs();
        if (allConnectionsOk) {
            feed();
        }
        redirect();
    }

    private void checkURLs() {

        allConnectionsOk = true;

        data.setMembers(Integer.parseInt(request.getParameter("member")));
        for (int i = 0, members = data.getMembers(); i < members; i++) {
            data.addUrl(request.getParameter("url" + i));
        }

        for (int i = 0, urlsLength = data.getUrls().size(); i < urlsLength; i++) {
            String urlInput = data.getUrl(i);

            if (!urlInput.startsWith("https://")) {
                urlInput = "https://" + urlInput;
            }

            try {
                URL url = new URL(urlInput);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("GET");
                huc.getResponseCode();  // throws Exception if URL not found
                data.removeNotFound(i);
            } catch (Exception e) {
                allConnectionsOk = false;
                data.addNotFound(i);
            }
        }
    }

    private void feed() {
        Feedreader feedreader = new Feedreader(data.getUrls());
        data.setArticles(feedreader.getRssContent());
    }

    private void redirect() throws IOException {

        if (allConnectionsOk) response.sendRedirect("whs/jo20046/ausgabe.jsp");
        else {
            data.setNotFoundUrls("Folgende URLs konnten nicht erreicht werden:<br>");
            for (Integer i : data.getNotFound()) {
                data.setNotFoundUrls(data.getNotFoundUrls() + data.getUrl(i) + "<br>");
            }
            data.clearUrls();
            response.sendRedirect("whs/jo20046/hinweis.jsp");
        }
    }

}
