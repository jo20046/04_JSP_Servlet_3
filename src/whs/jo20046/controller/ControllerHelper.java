package whs.jo20046.controller;

import whs.jo20046.beans.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ControllerHelper extends HelperBase {

    protected Data data = new Data();

    public ControllerHelper(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public void doPost() throws IOException {

        if (request.getSession().getAttribute("helper") == null) {
            request.getSession().setAttribute("helper", this);
        }

        if (request.getSession().getAttribute("Data") == null) {
            request.getSession().setAttribute("Data", data);
        }

        checkURLsAndRedirect();
    }

    private void checkURLsAndRedirect() throws IOException {

        data.setUrl(0, request.getParameter("url1"));
        data.setUrl(1, request.getParameter("url2"));
        data.setUrl(2, request.getParameter("url3"));
        boolean allConnectionsOK = true;

        for (int i = 0, urlsLength = data.getUrls().length; i < urlsLength; i++) {
            String urlInput = data.getUrl(i);

            if (!urlInput.startsWith("https://")) {
                urlInput = "https://" + urlInput;
            }

            try {
                URL url = new URL(urlInput);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("GET");
                huc.getResponseCode();  // throws Exception if URL not found
                data.setNotFoundText(i, "");
            } catch (Exception e) {
                allConnectionsOK = false;
                data.setNotFoundText(i, "Eingebene URL konnte nicht gefunden werden.");
            }
        }

        if (allConnectionsOK) {
            response.sendRedirect("whs/jo20046/ausgabe.jsp");
        } else {
            response.sendRedirect("whs/jo20046/eingabe.jsp");
        }
    }

}
