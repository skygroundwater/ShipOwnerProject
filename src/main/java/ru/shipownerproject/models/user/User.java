package ru.shipownerproject.models.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name ="users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @NotEmpty(message = "Username should be filled")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "password should be filled")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private Role role;

    @Column(name = "nonExpired")
    private boolean nonExpired;

    @Column(name = "nonLocked")
    private boolean nonLocked;

    @Column(name = "nonCredentialsExpired")
    private boolean nonCredentialsExpired;

    @Column(name = "nonEnabled")
    private boolean nonEnabled;

    @Override
    public String toString(){
        return username + " password : " + password + " role: " + role;
    }

}
