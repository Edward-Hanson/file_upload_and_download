package org.hans.upanddownload.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.hans.upanddownload.entity.Media;
import org.springframework.stereotype.Repository;


@Repository
public interface MediaRepository extends JpaRepository<Media, String>{

}