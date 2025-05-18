package com.socketprogramming.networkingjavafx.messages;

import com.socketprogramming.networkingjavafx.common.RequestType;

public class ImageMessage extends ServerRequest {

    private String imageBase64;
    private boolean accepted;

    public String getImageBase64() {
        return imageBase64;
    }

    public void setStatus(boolean accepted){
        this.accepted = accepted;
    }

    public boolean getStatus(){
        return accepted;
    }


    public ImageMessage(String imageBase64, String authorUsername, String receiverUsername, RequestType requestType) {
        super(authorUsername, receiverUsername, RequestType.IMAGEMESSAGE);
        this.imageBase64 = imageBase64;
    }
}
