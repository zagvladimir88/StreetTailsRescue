package com.zagvladimir.dao;

import com.zagvladimir.model.Image;

import java.util.List;
import java.util.Optional;

public interface ImageDAO {
    List<Image> findImagesByTailId(Integer imageId);

    Optional<Image> findById(Integer id);

    List<Image> findAll();

    Image create(Image image);

    Image update(Image image);

    void delete(Integer id);
}
