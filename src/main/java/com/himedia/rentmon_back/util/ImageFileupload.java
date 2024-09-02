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
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(profileimg.getSize());
        metadata.setContentType(profileimg.getContentType());
        amazonS3.putObject(bucket, filpath+"/"+originalFilename, profileimg.getInputStream(), metadata);

        return originalFilename;
    }

    public void removeFile(String filename , String filpath){
        try{
        amazonS3.deleteObject(bucket, filpath+"/"+filename);
        }catch(AmazonS3Exception e){
            e.printStackTrace();
        }
    }
}
