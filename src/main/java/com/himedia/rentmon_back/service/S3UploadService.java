package com.himedia.rentmon_back.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class S3UploadService {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
        // 업로드된 파일의 경로와 이름 리턴
        return originalFilename;
    }

    public String saveFile(MultipartFile multipartFile, String filpath) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex);
        }
        String newFilename = originalFilename.substring(0, dotIndex) + "_" + timestamp + extension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, filpath + "/" + newFilename, multipartFile.getInputStream(), metadata);
        // 업로드된 파일의 경로와 이름 리턴
        return originalFilename;
    }


}
