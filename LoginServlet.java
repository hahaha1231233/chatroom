package com.chatroom.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private Set<String> getUserSet(ServletContext context) {
        Object users = context.getAttribute("onlineUsers");
        if (users == null) {
            Set<String> set = new LinkedHashSet<>();
            context.setAttribute("onlineUsers", set);
            return set;
        }
        return (Set<String>) users;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("error", "请输入昵称");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
            return;
        }
        String trimmed = username.trim();
        HttpSession session = request.getSession();
        session.setAttribute("username", trimmed);

        Set<String> users = getUserSet(getServletContext());
        synchronized (users) {
            users.add(trimmed);
        }

        response.sendRedirect(request.getContextPath() + "/chat");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
