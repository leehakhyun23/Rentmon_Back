package com.himedia.rentmon_back.util;

import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

@Repository
@RequiredArgsConstructor
@Log4j2
public class ImageFileupload {

    private final ServletContext context;

    public String saveFile(MultipartFile profileimg , String filpath) {
        String result = "";
        String realpath = context.getRealPath(filpath);
        System.out.println("profile_images-------------------"+realpath);
        File realpathfile = new File(realpath);
        if (!realpathfile.exists()) realpathfile.mkdirs();

        Calendar today = Calendar.getInstance();
        long dt = today.getTimeInMillis();
        String filename = profileimg.getOriginalFilename();
        String fn1 = filename.substring(0, filename.indexOf(".") );
        String fn2 = filename.substring(filename.indexOf(".") );
        String uploadPath = realpath + "/" + fn1 + dt + fn2;
        try {
            profileimg.transferTo( new File(uploadPath) );
            result = fn1 + dt + fn2;

        } catch (IllegalStateException | IOException e) {e.printStackTrace();}
        return result;
    }

    public void removeFile(String filename , String filpath){
        String realpath = context.getRealPath(filpath);
        File removefile = new File(realpath + File.separator + filename);
        removefile.delete();
    }
}
