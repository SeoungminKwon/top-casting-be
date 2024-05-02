package com.ll.topcastingbe.domain.image.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DetailedImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String path; // S3경로 또는 로컬 파일 경로
    @Column(nullable = false)
    private String imageName; //UUID + 상품이름
    @Column(nullable = false)
    private String fullName; //디렉토리 + imageName
    private LocalDateTime createdDate;
}
