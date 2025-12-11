package com.chatroom.model;

import java.time.LocalDateTime;

public class Message {
    private final String sender;
    private final String content;
    private final LocalDateTime time;

    public Message(String sender, String content, LocalDateTime time) {
        this.sender = sender;
        this.content = content;
        this.time = time;
    }

    public String getSender() { return sender; }
    public String getContent() { return content; }
    public LocalDateTime getTime() { return time; }
}