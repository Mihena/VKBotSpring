package ru.mihena.VKBot.model;

import jakarta.persistence.*;
import ru.mihena.VKBot.commands.core.RoleName;
import java.io.Serializable;

@Entity
@Table(name = "vkusers")
public class UserEntity implements Serializable {

    @Id
    @Column(name = "vkid")
    private Long id;
    @Column(name = "role")
    private String roleName;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastname;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public UserEntity() {
    }

    public UserEntity(Long id, String roleName, String firstName, String lastname) {
        this.id = id;
        this.roleName = roleName;
        this.firstName = firstName;
        this.lastname = lastname;
    }

    public void setId(Long vkid) {
        this.id = vkid;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public Long getId() {
        return this.id;
    }
    public RoleName getRole() {
        if(this.roleName.equalsIgnoreCase("admin")) return RoleName.ADMIN;
        else return RoleName.USER;
    }
}
