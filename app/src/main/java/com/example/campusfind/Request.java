package com.example.campusfind;

public class Request {

    int id, postId;
    String message, status, sender; // 🔥 NEW

    public Request(int id, int postId, String message, String status, String sender) {
        this.id = id;
        this.postId = postId;
        this.message = message;
        this.status = status;
        this.sender = sender;
    }

    public int getId() { return id; }
    public int getPostId() { return postId; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public String getSender() { return sender; } // 🔥 NEW
}