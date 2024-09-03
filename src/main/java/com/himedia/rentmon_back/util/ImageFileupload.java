package com.himedia.rentmon_back.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
@RequiredArgsConstructor
@Log4j2
public class ImageFileupload {

    private final ServletContext context;
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile profileimg , String filpath) throws IOException {
        String originalFilename = profileimg.getOriginalFilename();

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex);
        }
        String newFilename = originalFilename.substring(0, dotIndex) + "_" + timestamp + extension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(profileimg.getSize());
        metadata.setContentType(profileimg.getContentType());

        amazonS3.putObject(bucket, filpath + "/" + newFilename, profileimg.getInputStream(), metadata);
        // 업로드된 파일의 경로와 이름 리턴
        return newFilename;
    }

    public void removeFile(String filename , String filpath){
        try{
        amazonS3.deleteObject(bucket, filpath+"/"+filename);
        }catch(AmazonS3Exception e){
            e.printStackTrace();
        }
    }
}
