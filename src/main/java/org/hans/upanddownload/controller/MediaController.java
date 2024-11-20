package org.hans.upanddownload.controller;

import lombok.RequiredArgsConstructor;
import org.hans.upanddownload.entity.Media;
import org.hans.upanddownload.service.MediaService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
public class MediaController {

  private final MediaService mediaService;


  @PostMapping("/upload")
  @ResponseStatus(CREATED)
  public String upload(@RequestParam("file") List<MultipartFile> files) {
        return mediaService.saveMedia(files);
  }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable("id") UUID id)  {
      Media media = mediaService.getFile(id);
      return ResponseEntity.ok()
              .contentType(MediaType.parseMediaType(media.getFileType()))
              .header(HttpHeaders.CONTENT_DISPOSITION,
                      "downloadMedia; filename=\"" + media.getFileName()
                              + "\"")
              .body(new ByteArrayResource(media.getData()));
    }
}
