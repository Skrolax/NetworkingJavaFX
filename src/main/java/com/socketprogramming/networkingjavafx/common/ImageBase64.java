package com.socketprogramming.networkingjavafx.common;

import java.io.*;
import java.util.Base64;

public class ImageBase64 {

    public static String encodeImageToBase64(File imageFile) throws IOException {
        FileInputStream fis = new FileInputStream(imageFile);
        byte[] bytes = fis.readAllBytes();
        fis.close();
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] decodeBase64ToImage(String imageBase64) throws IOException {
        return Base64.getDecoder().decode(imageBase64);
    }

}
