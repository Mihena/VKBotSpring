package ru.mihena.VKBot.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.mihena.VKBot.repositories.CustomizedUserCrudRepository;

@Service
public class UserService {

    @Autowired
    private CustomizedUserCrudRepository customizedUserCrudRepository;
    public void updateUser(UserEntity userEntity) {
        customizedUserCrudRepository.save(userEntity);
    }
    public boolean hasUserById(Long id) {return customizedUserCrudRepository.existsById(id);}
    @Bean
    @Scope("prototype")
    public UserEntity findById(@Autowired Long id) {
        return customizedUserCrudRepository.findById(id).orElse(null);
    }
}