package ru.mihena.VKBot.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mihena.VKBot.model.UserEntity;

@Repository
public interface CustomizedUserCrudRepository extends CrudRepository<UserEntity, Long> {
}