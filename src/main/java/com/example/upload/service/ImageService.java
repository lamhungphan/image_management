package com.example.upload.service;

import com.example.upload.entity.Image;
import com.example.upload.exception.NotFoundException;
import com.example.upload.repository.ImageRepository;
import com.example.upload.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final FileUtil fileUtil;

    public List<Image> getAllImage() {
        return imageRepository.findByOrderByCreatedAtDesc();
    }

    public Image getImageById(Integer id) {
        return imageRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Image not found with id: " + id);
        });
    }

    public Image uploadImage(MultipartFile file) {
        fileUtil.validateFile(file);
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Image image = new Image(fileName, file.getContentType(), file.getBytes());
            return imageRepository.save(image);
        } catch (Exception e) {
            throw new RuntimeException("Error while processing file upload: " + e.getMessage());
        }
    }

    public void deleteImageById(Integer id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Image not found with id: " + id);
        });
        imageRepository.delete(image);
    }
}
