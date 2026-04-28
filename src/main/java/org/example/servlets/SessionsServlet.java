package org.example.servlets;

import com.google.gson.Gson;
import org.example.accounts.AccountService;
import org.example.accounts.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/session")
public class SessionsServlet extends HttpServlet {
    private AccountService accountService;
    private final Gson gson = new Gson();

    @Override
    public void init() {
        this.accountService = AccountService.getInstance();
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        UserDataSet profile = (UserDataSet) session.getAttribute("user");
        if (profile == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String json = gson.toJson(profile);
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(json);
    }

    public void doPost(HttpServletRequest req,
                       HttpServletResponse res) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");

        if (login == null || pass == null) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        UserDataSet profile = accountService.getUserByLogin(login);
        if (profile != null) {
            System.out.println(profile.getLogin());
        }

        if (profile == null || !profile.getPass().equals(pass)) {

            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        HttpSession session = req.getSession();
        System.out.println("Session ID: " + session.getId());
        session.setAttribute("user", profile);

        res.sendRedirect("/files");
    }

    public void doDelete(HttpServletRequest req,
                         HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            session.invalidate();
        }
        res.sendRedirect("/login.html");
    }
}
