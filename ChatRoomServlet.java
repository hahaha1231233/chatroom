package com.chatroom.servlet;

import com.chatroom.model.Message;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/chat")
public class ChatRoomServlet extends HttpServlet {

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    @SuppressWarnings("unchecked")
    private List<Message> getMessageList(ServletContext context) {
        Object messages = context.getAttribute("messages");
        if (messages == null) {
            List<Message> list = new ArrayList<>();
            context.setAttribute("messages", list);
            return list;
        }
        return (List<Message>) messages;
    }

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = getServletContext();
        List<Message> messageList = getMessageList(context);
        List<String> userList = new ArrayList<>(getUserSet(context));

        request.setAttribute("messages", messageList);
        request.setAttribute("users", userList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/chat.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        String username = (String) session.getAttribute("username");
        String content = request.getParameter("message");

        if (content != null && !content.trim().isEmpty()) {
            List<Message> messageList = getMessageList(getServletContext());
            synchronized (messageList) {
                // Keep only last 100 messages to prevent memory issues
                if (messageList.size() > 100) {
                    messageList.remove(0);
                }
                messageList.add(new Message(username, content.trim(), LocalDateTime.now()));
            }
        }
        response.sendRedirect(request.getContextPath() + "/chat");
    }
}