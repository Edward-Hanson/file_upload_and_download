package org.hans.upanddownload.controller;

import org.hans.upanddownload.entity.Media;
import org.hans.upanddownload.service.MediaService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
public class MediaController {

    private MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws Exception {

        mediaService.saveMedia(file);
        return ResponseEntity.ok("Upload complete.");
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") String id) throws Exception {
        Optional<Media> media = mediaService.findMedia(id);
        if (!media.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Media downloadMedia = media.get();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(downloadMedia.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "downloadMedia; filename=\"" + downloadMedia.getFileName()
                        + "\"")
                .body(new ByteArrayResource(downloadMedia.getData()));

    }
}
