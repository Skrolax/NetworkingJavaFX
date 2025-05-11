package com.socketprogramming.networkingjavafx;

public class Message {

    // Author ID

    private String authorID;
    public String getAuthorID() {
        return authorID;
    }

    // Sender ID

    private String senderID;
    public String getSenderID() {
        return senderID;
    }

    // Message

    private String message;
    public String getMessage() {
        return message;
    }

    public Message(String message, String senderID, String authorID) {
        this.message = message;
        this.senderID = senderID;
        this.authorID = authorID;
    }

}
