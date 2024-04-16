package org.hans.upanddownload.service;

import org.hans.upanddownload.entity.Media;
import org.hans.upanddownload.repository.MediaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



@Service
public class MediaService {

    private MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public ResponseEntity<String> saveMedia(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (filename.contains("..")) {
                throw new Exception("The filename is invalid");
            }

            Media media = Media.builder()
                    .fileName(filename)
                    .fileType(file.getContentType())
                    .data(file.getBytes()).build();
            mediaRepository.save(media);


            return ResponseEntity.ok("Upload was successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Upload failed");
        }

    }

    public Optional<Media> findMedia(String id) {
        return mediaRepository.findById(id);
    }
}