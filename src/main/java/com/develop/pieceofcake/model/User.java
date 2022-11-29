package com.develop.pieceofcake.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


import lombok.Data;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    
    @Column(nullable = false, unique = true)
    @NotEmpty
    @Email(message = "{errors.invalid_email}")
    private String email;

    private String password;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER) //One user can have multiple roles and one role can have multiple user
    @JoinTable(
        name = "user_role",
        joinColumns = {@JoinColumn(name= "USER_ID", referencedColumnName = "ID")}, 
        inverseJoinColumns= {@JoinColumn(name="ROLE_ID", referencedColumnName = "ID")}
    ) //Merging primary key of one table with foreign key of other table

    private List<Role> roles;

    // Creating a constructor
    public User(User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();

    }

    public User() {
    }
}
