package org.hans.upanddownload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import org.hans.upanddownload.entity.Media;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface MediaRepository extends JpaRepository<Media, String>{

}