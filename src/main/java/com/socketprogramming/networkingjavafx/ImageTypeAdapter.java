package com.socketprogramming.networkingjavafx;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/*public class ImageTypeAdapter extends TypeAdapter<ImageMessage> {
    @Override
    public void write(JsonWriter jsonWriter, ImageMessage imageMessage) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("authorUsername").value(imageMessage.getAuthorUsername());
        jsonWriter.name("receiverUsername").value(imageMessage.getReceiverUsername());
        jsonWriter.name("imageFile").value(imageMessage.getImageFile().getAbsolutePath());
        json
    }

    @Override
    public ImageMessage read(JsonReader jsonReader) throws IOException {
        return null;
    }
}*/
