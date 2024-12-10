package org.hans.upanddownload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.hans.upanddownload.entity.Media;
import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID>{
}