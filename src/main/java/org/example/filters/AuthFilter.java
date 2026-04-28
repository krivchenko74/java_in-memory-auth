package org.example.filters;

import org.example.accounts.UserDataSet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/files")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(false);
        UserDataSet user = (session != null)
                ? (UserDataSet) session.getAttribute("user")
                : null;

        if (user == null) {
            response.sendRedirect("/login.html");
            return;
        }

        chain.doFilter(req, res);
    }
}