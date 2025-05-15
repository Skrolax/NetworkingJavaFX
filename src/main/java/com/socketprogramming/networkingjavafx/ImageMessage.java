package com.socketprogramming.networkingjavafx;

public class ImageMessage extends ServerRequest{
    protected ImageMessage(String authorUsername, String receiverUsername, RequestType requestType) {
        super(authorUsername, receiverUsername, RequestType.IMAGEMESSAGE);
    }
}
