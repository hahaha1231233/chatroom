package com.chatroom.servlet;

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

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("username");
            if (username != null) {
                Set<String> users = getUserSet(getServletContext());
                synchronized (users) {
                    users.remove(username);
                }
            }
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}