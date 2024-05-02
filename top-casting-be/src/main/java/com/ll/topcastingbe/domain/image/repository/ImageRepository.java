package com.ll.topcastingbe.domain.image.repository;

import com.ll.topcastingbe.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}

