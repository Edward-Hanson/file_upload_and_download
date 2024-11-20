package org.hans.upanddownload.service;

import org.hans.upanddownload.entity.Media;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

public interface MediaService {
    String saveMedia(List<MultipartFile> files);
    Media getFile(UUID id);
}