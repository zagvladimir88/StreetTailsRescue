package com.zagvladimir.repository;

import com.zagvladimir.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Integer> {

    List<Image> findImagesByTailId(int imageId);
}
