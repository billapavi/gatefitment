package com.billa.springdatajpa.domaine;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private Long ID;

    private String userID;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phoneNo;
    private String profileImageUrl;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    private Date joinDate;
    private String[] roles;// ROLE_USER(READ,UPDATE),ROLE_ADMIN(READ,WRITE,UPDATE,DELETE)
    private String[] authorities;
    private boolean active;
    private boolean locked;

}
