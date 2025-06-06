package com.socketprogramming.networkingjavafx;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataSaving {

    //Firstly, the user joins and accesses the directory which is represented by their username
    //After the directory has been accessed, the user has now to select a friend to exchange messages with
    //So the main directory represented by their username will contain other directories represented by their friends username
    //Each directory will contain a JSON file where all the messages between each other are stored

    private static final String path = "src/main/resources/Message Logs/";
    private static FileWriter fileWriter;
    private static FileReader fileReader;
    private static final Gson gson = new Gson();
    private static List<String> messages;
    private static String chatPath;

    public static File createDirectory(String directoryName){
        File file = new File(path + directoryName);
        file.mkdir();
        return file;
    }

    public static boolean createJSON(String directoryName) throws IOException {
        try {
            fileWriter = new FileWriter(path + directoryName + ".json");
            return true;
        }catch (Exception e){
            System.out.println("Couldn't create the JSON file");
        }
        return false;
    }

    private static void getMessagesFromFile(String clientUsername, String friendUsername) throws IOException {
        messages = new ArrayList<>();
        chatPath = path + clientUsername + "/" + friendUsername + ".json";
        Type listType = new TypeToken<List<String>>(){}.getType();
        fileReader = new FileReader(chatPath);
        messages = gson.fromJson(fileReader, listType);
        messages = Objects.requireNonNullElseGet(messages, ArrayList::new);
    }

    public static void updateConversation(String clientUsername, String friendUsername, VBox messageArea) throws IOException {
        chatPath = path + clientUsername + "/" + friendUsername + ".json";
        fileWriter = new FileWriter(chatPath);
        for(Node messageBox: messageArea.getChildren()){
            if(messageBox instanceof VBox vbox){
                for(Node message: vbox.getChildren()) {
                    if (message instanceof Label label) {
                        try{
                            messages.add(label.getText());
                        }catch (Exception e){
                            System.out.println("Couldn't get the message data");
                        }
                    } else if (message instanceof ImageView imageView) {
                        try{
                            messages.add(ImageBase64.encodeImageToBase64(new File(imageView.getImage().getUrl())));
                        }catch (Exception e){
                            System.out.println("Couldn't get the message data");
                        }
                    }
                }
            }
        }

        gson.toJson(messages, fileWriter);
        messages.clear();
        fileWriter.flush();

    }

    public static void loadConversation(String clientUsername, String friendUsername) throws IOException {
        getMessagesFromFile(clientUsername, friendUsername);
    }


}
