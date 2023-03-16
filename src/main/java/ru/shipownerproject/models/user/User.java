package ru.shipownerproject.models.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name ="users")
public class User {

    @Id
    @NotEmpty(message = "Username should be filled")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "password should be filled")
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "non_expired")
    private boolean nonExpired;

    @Column(name = "non_locked")
    private boolean nonLocked;

    @Column(name = "non_credentials_expired")
    private boolean nonCredentialsExpired;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    public User(){}

    public User(String username, String password, Role role){
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setAllBooleansFieldsToTrue(){
        this.setEnabled(true);
        this.setNonLocked(true);
        this.setNonCredentialsExpired(true);
        this.setNonExpired(true);
    }

    @Override
    public String toString(){
        return username + " password : " + password + " role: " + role;
    }

}
