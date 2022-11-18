package com.kravets.hotels.rpnjava.misc;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class CoverPhoto {
    private static final String uploadPath = "/C:/Users/Administrator/IdeaProjects/rpnjava/files/uploads/";

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
