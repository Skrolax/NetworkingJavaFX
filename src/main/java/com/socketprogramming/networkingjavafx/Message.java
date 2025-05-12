package com.socketprogramming.networkingjavafx;

import java.io.Serializable;

public class Message implements Serializable {

    // Author ID

    private final String authorID;
    public String getAuthorID() {
        return authorID;
    }

    // Sender ID

    private /*final*/ String receiverID;
    public String getReceiverID() {
        return receiverID;
    }

    // Message

    private final String message;
    public String getMessage() {
        return message;
    }

    public Message(String message, String authorID /*String receiverID,*/) {
        this.message = message;
        this.authorID = authorID;
        //this.receiverID = receiverID;
    }

}
