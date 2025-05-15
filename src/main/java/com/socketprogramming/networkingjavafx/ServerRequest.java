package com.socketprogramming.networkingjavafx;

import java.io.Serializable;

public abstract class ServerRequest implements Serializable {

    //Author ID
    private final String authorUsername;
    public String getAuthorUsername() {
        return authorUsername;
    }

    //Sender ID
    private final String receiverUsername;
    public String getReceiverUsername() {
        return receiverUsername;
    }

    //Request type

    private final RequestType requestType;
    public RequestType getRequestType(){
        return requestType;
    }

    //Constructor
    protected ServerRequest(String authorUsername, String receiverUsername, RequestType requestType) {
        this.authorUsername = authorUsername;
        this.receiverUsername = receiverUsername;
        this.requestType = requestType;
    }

}
