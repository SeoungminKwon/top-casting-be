package com.ll.topcastingbe.domain.image.repository;

import com.ll.topcastingbe.domain.image.entity.DetailedImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailedImageRepository extends JpaRepository<DetailedImage, Long> {
}
