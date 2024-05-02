package com.ll.topcastingbe.domain.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ll.topcastingbe.domain.image.entity.DetailedImage;
import com.ll.topcastingbe.domain.image.entity.Image;
import com.ll.topcastingbe.domain.image.repository.DetailedImageRepository;
import com.ll.topcastingbe.domain.image.repository.ImageRepository;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ImageService {

    private final AmazonS3 amazonS3;
    private final ImageRepository imageRepository;
    private final DetailedImageRepository detailedImageRepository;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Transactional
    public Image uploadImage(String itemName, String base64) {
        byte[] decodedFile = Base64.getMimeDecoder().decode(base64.substring(base64.indexOf(",") + 1));
        String contentType = base64.substring(base64.indexOf(":"), base64.indexOf(";"));

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(decodedFile.length);
        metadata.setContentType(contentType);

        //S3에 '년/월/일/UUID_파일이름' 으로 저장
        LocalDate now = LocalDate.now();
        String datePath = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd/"));
        String imageName = UUID.randomUUID() + "_" + itemName;
        String keyName = datePath + imageName;

        amazonS3.putObject(
                new PutObjectRequest(bucket, keyName, new ByteArrayInputStream(decodedFile), metadata));
        String imageUrl = amazonS3.getUrl(bucket, keyName).toString();

        Image image = Image.builder()
                .path(imageUrl)
                .imageName(imageName)
                .fullName(keyName)
                .createdDate(LocalDateTime.now())
                .build();

        return imageRepository.save(image);
    }

    @Transactional
    public DetailedImage uploadDetailedImage(String itemName, String base64) {

        byte[] decodedFile = Base64.getMimeDecoder().decode(base64.substring(base64.indexOf(",") + 1));
        String contentType = base64.substring(base64.indexOf(":"), base64.indexOf(";"));

        log.info("decodedFile={}", base64.substring(base64.indexOf(",") + 1));
        log.info("contentType={}", contentType);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(decodedFile.length);
        metadata.setContentType(contentType);

        //S3에 '년/월/일/UUID_파일이름' 으로 저장
        LocalDate now = LocalDate.now();
        String datePath = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd/"));
        String imageName = UUID.randomUUID() + "_" + itemName;
        String keyName = datePath + imageName;

        amazonS3.putObject(
                new PutObjectRequest(bucket, keyName, new ByteArrayInputStream(decodedFile), metadata));
        String imageUrl = amazonS3.getUrl(bucket, keyName).toString();

        DetailedImage detailedImage = DetailedImage.builder()
                .path(imageUrl)
                .imageName(imageName)
                .fullName(keyName)
                .createdDate(LocalDateTime.now())
                .build();

        return detailedImageRepository.save(detailedImage);
    }
}
