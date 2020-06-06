package whs.jo20046.controller;

import whs.jo20046.beans.Data;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "Check")
public class Check extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ControllerHelper helper;
        if (request.getSession().getAttribute("helper") == null) {
            helper = new ControllerHelper(request, response);
        } else {
            helper = (ControllerHelper) request.getSession().getAttribute("helper");
        }
        helper.doPost();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
