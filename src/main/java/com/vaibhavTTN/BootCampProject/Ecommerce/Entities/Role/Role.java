package com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Role;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<User> user = new HashSet<>();

    private String authority;

    private Boolean isDeleted;

}
