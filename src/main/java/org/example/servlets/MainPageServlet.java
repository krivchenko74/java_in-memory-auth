//package org.example.servlets;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebServlet("/")
//public class MainPageServlet extends HttpServlet {
//    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
//
//        String uri = req.getRequestURI();
//
//        // ❗ пропускаем статические страницы
//        if (uri.endsWith(".html")) {
//            req.getRequestDispatcher(uri).forward(req, res);
//            return;
//        }
//
//        HttpSession session = req.getSession(false);
//
//        if (session != null && session.getAttribute("user") != null) {
//            res.sendRedirect("/files");
//        } else {
//            res.sendRedirect("/login.html");
//        }
//    }
//}