package org.example.servlets;

import org.example.accounts.AccountService;
import org.example.accounts.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AccountService service = AccountService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        service.InitUsers();
        req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = service.getUserByLogin(req.getParameter("login"));
        if(user == null) {
            resp.getWriter().println("User not found");
            return;
        }
        if (!user.getPassword().equals(req.getParameter("password"))) {
            resp.getWriter().println("Incorrect password");
            return;
        }

        String sessionId = req.getSession().getId();
        service.addSession(sessionId, user);
        resp.sendRedirect("files?path=");
    }
}
