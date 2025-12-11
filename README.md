# Online Chatroom

一个基于 Java Web 技术的简单在线聊天室系统，使用 Servlet、JSP 和 Java 实现。

## 📋 项目简介

这是一个基础的在线聊天室应用，支持多用户实时聊天、在线用户列表显示、消息历史记录等功能。项目采用传统的 Java Web 技术栈，适合学习和教学使用。

## 🏗️ 项目架构

### 技术栈
- **后端**: Java Servlet 4.0, JSP
- **前端**: HTML, CSS, JavaScript
- **服务器**: Apache Tomcat 11.0.11
- **Java版本**: JDK 17+
- **数据存储**: ServletContext 应用级存储

### 系统架构
```
MVC 架构模式:
- 模型(Model): Message.java - 数据实体
- 视图(View): JSP页面 (index.jsp, chat.jsp) - 用户界面
- 控制器(Controller): Servlet类 - 业务逻辑处理
```

### 目录结构
```
online-chatroom/
├── src/main/java/com/chatroom/
│   ├── model/
│   │   └── Message.java              # 消息实体类
│   ├── servlet/
│   │   ├── LoginServlet.java         # 用户登录控制器
│   │   ├── LogoutServlet.java        # 用户退出控制器
│   │   └── ChatRoomServlet.java      # 聊天室主控制器
├── webapp/
│   ├── WEB-INF/
│   │   └── web.xml                   # Web应用配置文件
│   ├── index.jsp                     # 登录页面
│   └── chat.jsp                      # 主聊天室页面
└── pom.xml                           # Maven配置文件(可选)
```

## ✨ 功能特性

### 1. 用户管理
- **用户登录**: 输入昵称即可进入聊天室
- **在线状态**: 实时显示在线用户列表
- **会话管理**: 30分钟会话超时
- **用户退出**: 清理在线状态并重定向到登录页

### 2. 消息系统
- **实时聊天**: 所有在线用户可见的公共聊天
- **消息历史**: 保存最近的聊天记录
- **自动滚动**: 新消息自动滚动到底部
- **消息限制**: 最多保存100条历史消息，防止内存溢出

### 3. 界面特性
- **响应式布局**: 适配不同屏幕尺寸
- **简洁设计**: 清晰的消息展示和用户列表
- **实时更新**: 页面自动刷新显示新消息
- **时间戳**: 每条消息显示发送时间


## 📁 核心文件说明

### 配置文件
- **web.xml**: Web应用部署描述符，配置会话超时、欢迎页面等

### 数据模型
- **Message.java**: 消息数据模型，包含发送者、内容和时间戳

### Servlet控制器
- **LoginServlet.java**: 处理用户登录，验证昵称并添加到在线用户列表
- **ChatRoomServlet.java**: 处理聊天室消息发送和显示，管理消息历史
- **LogoutServlet.java**: 处理用户退出，清理用户会话和在线状态

### JSP视图
- **index.jsp**: 用户登录页面，简洁的表单设计
- **chat.jsp**: 主聊天室页面，包含消息区域和用户列表

## 🔧 关键技术实现

### 并发控制
```java
// 使用 synchronized 关键字保证线程安全
synchronized (users) {
    users.add(trimmed);
}
```

### 数据存储
```java
// 使用 ServletContext 存储应用级数据
context.setAttribute("onlineUsers", set);
context.setAttribute("messages", list);
```

### 消息管理
```java
// 限制消息历史数量（最多100条）
if (messageList.size() > 100) {
    messageList.remove(0);
}
```

### 时间格式化
```java
// 使用 DateTimeFormatter 格式化时间显示
DateTimeFormatter TIME_FORMATTER = 
    DateTimeFormatter.ofPattern("HH:mm:ss");
msg.getTime().format(TIME_FORMATTER)
```

## 🎨 界面设计

### 布局结构
- **头部**: 显示欢迎信息和当前用户
- **聊天区域**: 左侧显示消息历史，右侧显示在线用户列表
- **输入区域**: 底部消息输入框和发送按钮
- **操作区域**: 退出聊天室按钮

### 样式特点
- **配色方案**: 绿色主题，简洁清爽
- **响应式设计**: 自适应不同屏幕尺寸
- **用户友好**: 清晰的视觉层次和交互反馈

## ⚙️ 配置说明

### 会话配置
```xml
<session-config>
    <session-timeout>30</session-timeout>
</session-config>
```

### 欢迎页面
```xml
<welcome-file-list>
    <welcome-file>index.jsp</welcome-file>
</welcome-file-list>
```

### Servlet 注解
项目使用 `@WebServlet` 注解配置 Servlet 映射，无需在 web.xml 中额外配置。


