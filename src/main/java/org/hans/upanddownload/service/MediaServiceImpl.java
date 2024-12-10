package org.hans.upanddownload.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hans.upanddownload.constants.ResponseConstant;
import org.hans.upanddownload.entity.Media;
import org.hans.upanddownload.exception.MediaException;
import org.hans.upanddownload.repository.MediaRepository;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @Transactional
    public String saveMedia(List<MultipartFile> files) {
        try {
            List<Media> dbFiles = files.stream()
                    .map(this::uploadFile)
                            .toList();
            mediaRepository.saveAll(dbFiles);
            return ResponseConstant.FILES_UPLOADED;
        } catch (Exception e) {
            throw new MediaException(e.getMessage());
        }
    }

    @Override
    public Media getFile(UUID id) {
        return mediaRepository.findById(id).orElseThrow(() -> new MediaException(ResponseConstant.FILE_NOT_FOUND));
    }

    @Transactional
    @Override
    public String updateMedia(UUID id, MultipartFile file) {
        validateMedia(file);
        try {
                byte[] bytes = file.getBytes();
                mediaRepository.findById(id).ifPresentOrElse(media -> {
                media.setFileName(file.getOriginalFilename());
                media.setFileType(file.getContentType());
                media.setData(bytes);
                mediaRepository.save(media);
                }, () -> new MediaException(ResponseConstant.FILE_NOT_FOUND));
        }catch (IOException e){
              throw new MediaException(e.getMessage());
        }
        return ResponseConstant.FILE_UPDATE_SUCCESS;
    }



    private void validateMedia(MultipartFile media) {
        String filename = StringUtils.cleanPath(media.getOriginalFilename());
        if (media.getOriginalFilename() == null || media.getOriginalFilename().isEmpty() || filename.contains("..")) {
            throw new MediaException(ResponseConstant.INVALID_FILENAME);
        }
    }


    private Media uploadFile(MultipartFile file) {
        validateMedia(file);
        try {
            return Media.builder()
                    .fileName(file.getOriginalFilename())
                    .fileType(file.getContentType())
                    .data(file.getBytes())
                    .build();

        } catch (IOException e) {
            throw new MediaException(e.getMessage());
        }
    }
}
