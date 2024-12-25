package com.example.upload.repository;

import com.example.upload.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    // Lấy danh sách tất cả image theo thời gian tạo giảm dần
    List<Image> findByOrderByCreatedAtDesc();
}
