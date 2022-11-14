package com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Role;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
    )
    private List<User> user;

    private String authority;

    private Boolean isDeleted;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                '}';
    }
}
