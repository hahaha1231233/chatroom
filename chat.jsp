<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.chatroom.model.Message,java.util.List,java.time.format.DateTimeFormatter" %>
<%
    List<Message> messages = (List<Message>) request.getAttribute("messages");
    if (messages == null) {
        messages = java.util.Collections.emptyList();
    }
    List<String> users = (List<String>) request.getAttribute("users");
    if (users == null) {
        users = java.util.Collections.emptyList();
    }
    jakarta.servlet.http.HttpSession currentSession = request.getSession(false);
    String username = currentSession == null ? null : (String) currentSession.getAttribute("username");

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>聊天室</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f5f5f5;
        }
        .header {
            background-color: #4CAF50;
            color: white;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .container {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }
        .chat-area {
            flex: 3;
            background: white;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
            height: 400px;
            overflow-y: auto;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .users-area {
            flex: 1;
            background: white;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
            height: 400px;
            overflow-y: auto;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .message {
            margin-bottom: 10px;
            padding: 8px;
            border-bottom: 1px solid #eee;
        }
        .message .sender {
            font-weight: bold;
            color: #4CAF50;
        }
        .message .time {
            color: #888;
            font-size: 0.9em;
            margin-left: 10px;
        }
        .message .content {
            margin-top: 5px;
            color: #333;
        }
        .user-list {
            list-style: none;
            padding: 0;
        }
        .user-list li {
            padding: 8px;
            margin-bottom: 5px;
            background-color: #f9f9f9;
            border-radius: 3px;
        }
        .message-form {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }
        .message-form input {
            flex: 1;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        .message-form button {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .logout-form {
            margin-top: 10px;
            text-align: right;
        }
        .logout-form button {
            padding: 8px 16px;
            background-color: #f44336;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .logout-form button:hover {
            background-color: #d32f2f;
        }
        .message-form button:hover {
            background-color: #45a049;
        }
        h3 {
            color: #333;
            border-bottom: 2px solid #4CAF50;
            padding-bottom: 5px;
        }
    </style>
</head>
<body>
<div class="header">
    <h2>在线聊天室 - 欢迎：<%= username == null ? "游客" : username %></h2>
</div>

<div class="container">
    <div class="chat-area">
        <h3>聊天记录</h3>
        <div id="messages">
            <% for (Message msg : messages) { %>
            <div class="message">
                <div>
                    <span class="sender"><%= msg.getSender() %>：</span>
                    <span class="time"><%= msg.getTime().format(timeFormatter) %></span>
                </div>
                <div class="content"><%= msg.getContent() %></div>
            </div>
            <% } %>
        </div>
    </div>

    <div class="users-area">
        <h3>在线用户 (<%= users.size() %>)</h3>
        <ul class="user-list">
            <% for (String user : users) { %>
            <li><%= user %></li>
            <% } %>
        </ul>
    </div>
</div>

<form class="message-form" action="${pageContext.request.contextPath}/chat" method="post">
    <input type="text" name="message" placeholder="输入消息..." required
           maxlength="200" autocomplete="off">
    <button type="submit">发送</button>
</form>

<form class="logout-form" action="${pageContext.request.contextPath}/logout" method="post">
    <button type="submit">退出聊天室</button>
</form>

<script>
    // Auto scroll to bottom of messages
    window.onload = function() {
        const messagesDiv = document.getElementById('messages');
        if (messagesDiv) {
            messagesDiv.scrollTop = messagesDiv.scrollHeight;
        }
    };
</script>
</body>
</html>