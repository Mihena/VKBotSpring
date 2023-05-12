package ru.mihena.VKBot.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mihena.VKBot.model.ImageEntity;

@Repository
public interface CustomizedImageCrudRepository extends CrudRepository<ImageEntity, Long> {
}
