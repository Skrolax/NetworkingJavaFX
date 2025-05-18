package com.socketprogramming.networkingjavafx;

import java.io.File;
import java.io.Serializable;
import java.net.URL;

public class ImageMessage extends ServerRequest {

    private String imageBase64;

    public String getImageBase64() {
        return imageBase64;
    }

    public ImageMessage(String imageBase64, String authorUsername, String receiverUsername, RequestType requestType) {
        super(authorUsername, receiverUsername, RequestType.IMAGEMESSAGE);
        this.imageBase64 = imageBase64;
    }
}
