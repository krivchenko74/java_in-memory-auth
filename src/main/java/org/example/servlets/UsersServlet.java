package org.example.servlets;

import com.google.gson.Gson;
import org.example.accounts.AccountService;
import org.example.accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {
    private AccountService accountService;
    private final Gson gson = new Gson();

    @Override
    public void init() {
        this.accountService = AccountService.getInstance();
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");
        String email = req.getParameter("email");
        if (login == null || pass == null || login.isEmpty() || pass.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (accountService.getUserByLogin(login) != null)
        {
            res.setStatus(HttpServletResponse.SC_CONFLICT);
            return;
        }

        String root = "/Users/slava/filemanager/" + login;
        File userDir = new File(root);
        if (!userDir.exists()) {
            userDir.mkdirs();
        }

        UserProfile user = new UserProfile(login, pass, email);
        accountService.addNewUser(user);

        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        res.sendRedirect("/files");
    }
}
