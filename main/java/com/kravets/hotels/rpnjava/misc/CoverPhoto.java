package com.kravets.hotels.rpnjava.misc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class CoverPhoto {
    @Value("${upload.path}")
    private static String uploadPath;

    public static String upload(MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String newFileName = UUID.randomUUID() + ".jpg";
        file.transferTo(new File(uploadPath + newFileName));

        return newFileName;
    }

    public static void remove(String name) {
        File file = new File(uploadPath + name);
        file.delete();
    }
}
