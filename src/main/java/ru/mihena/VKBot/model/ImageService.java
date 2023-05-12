package ru.mihena.VKBot.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.mihena.VKBot.repositories.CustomizedImageCrudRepository;

@Service
public class ImageService {

    @Autowired
    private CustomizedImageCrudRepository customizedImageCrudRepository;
    public void updateImage(ImageEntity imageEntity) {
        customizedImageCrudRepository.save(imageEntity);
    }
    public boolean hasImageById(Long id) {return customizedImageCrudRepository.existsById(id);}
    @Bean("image")
    @Scope("prototype")
    public ImageEntity findById(Long id) {
        return customizedImageCrudRepository.findById(id).orElse(null);
    }
}