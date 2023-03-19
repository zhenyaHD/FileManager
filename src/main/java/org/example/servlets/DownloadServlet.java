package org.example.servlets;

import org.example.accounts.AccountService;
import org.example.accounts.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    private AccountService service = AccountService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String sessionId = req.getSession().getId();
        User user = service.getUserBySessionId(sessionId);
        if (user == null) {
            resp.sendRedirect("login");
            return;
        }
        String defaultPath = System.getProperty("user.home");

        defaultPath = defaultPath + System.getProperty("file.separator") + "filemanager" + System.getProperty("file.separator") + user.getLogin();
        String parameter = req.getParameter("path");
        String fileName = Paths.get(parameter).getFileName().toString();

        resp.setHeader("Content-disposition","attachment; filename=" + fileName);

        if(!parameter.contains(defaultPath))
        {
            resp.sendRedirect("./");
        }
        File myFile = new File(parameter);

        OutputStream out = resp.getOutputStream();
        FileInputStream in = new FileInputStream(myFile);

        byte[] buffer = new byte[4096];
        int length;

        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }

        in.close();
        out.flush();
    }
}