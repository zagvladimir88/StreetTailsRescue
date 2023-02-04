package com.zagvladimir.dao;

import com.zagvladimir.model.Image;
import com.zagvladimir.repository.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ImageDAOImpl implements ImageDAO{

    ImageRepository repository;

    @Override
    public List<Image> findImagesByTailId(Integer imageId) {
        return repository.findImagesByTailId(imageId);
    }

    @Override
    public Optional<Image> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Image> findAll() {
        return repository.findAll();
    }

    @Override
    public Image create(Image image) {
        return repository.save(image);
    }

    @Override
    public Image update(Image image) {
        return repository.save(image);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
