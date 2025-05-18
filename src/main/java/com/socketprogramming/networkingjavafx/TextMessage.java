package com.socketprogramming.networkingjavafx;

import java.io.Serializable;

public class TextMessage extends ServerRequest implements Serializable {

    //TextMessage
    private final String message;
    public String getMessage() {
        return message;
    }

    //Constructor
    public TextMessage(String message, String authorUsername, String receiverUsername) {
        super(authorUsername, receiverUsername, RequestType.TEXTMESSAGE);
        this.message = message;
    }

}
