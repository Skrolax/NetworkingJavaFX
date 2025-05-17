package com.socketprogramming.networkingjavafx.messages;

import com.socketprogramming.networkingjavafx.common.RequestType;

import java.io.Serializable;

public class FriendRequest extends ServerRequest implements Serializable {

    //Constructor
    public FriendRequest(String authorUsername, String receiverUsername) {
        super(authorUsername, receiverUsername, RequestType.FRIENDREQUEST);
    }

}
