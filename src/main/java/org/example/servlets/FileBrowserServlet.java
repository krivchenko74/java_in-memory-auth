package org.example.servlets;

import org.example.accounts.UserProfile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Date;

@WebServlet("/files")
public class FileBrowserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            res.sendRedirect("/login.html");
            return;
        }
        UserProfile user = (UserProfile) session.getAttribute("user");

        String root = "/Users/slava/filemanager/" + user.getLogin();

        String path = req.getParameter("path");
        File currentDir;
        if (path == null || path.isEmpty()) {
            currentDir = new File(root);
        } else {
            currentDir = new File(path);
        }

        File rootDir = new File(root);
        File canonicalRoot = rootDir.getCanonicalFile();
        File canonicalCurrent = currentDir.getCanonicalFile();

        if (!canonicalCurrent.getPath().startsWith(canonicalRoot.getPath())) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        if (currentDir.isFile()) {
            res.setContentType("application/octet-stream");
            String encodedFileName = java.net.URLEncoder.encode(currentDir.getName(), StandardCharsets.UTF_8)
                    .replaceAll("\\+", "%20");
            res.setHeader("Content-Disposition",
                    "attachment; filename=\"file\"; filename*=UTF-8''" + encodedFileName);

            Files.copy(currentDir.toPath(), res.getOutputStream());
            return;
        }

        File[] files = currentDir.listFiles();
        System.out.println("DEBUG FILES NUM IN DIR");
        if (files == null)
            System.out.println("NO FILES IN DIR");
        else {
            System.out.println(files.length);
        }
        req.setAttribute("files", files);
        req.setAttribute("currentPath", currentDir.getAbsolutePath());
        req.setAttribute("parentPath", currentDir.getParent());
        req.setAttribute("now", new Date());

        req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, res);
    }
}