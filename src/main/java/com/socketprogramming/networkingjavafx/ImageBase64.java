package com.socketprogramming.networkingjavafx;

import java.io.*;
import java.util.Base64;

public class ImageBase64 {

    public static String encodeImageToBase64(File imageFile) throws IOException {
        FileInputStream fis = new FileInputStream(imageFile);
        byte[] bytes = fis.readAllBytes();
        fis.close();
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static File decodeBase64ToImage(String imageBase64, String outputPath) throws IOException {
        byte[] decodeImage = Base64.getDecoder().decode(imageBase64);
        File outputFile = new File(outputPath);

        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(decodeImage);
        }

        return outputFile;
    }

}
