package ru.shipownerproject.models.user;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Override
    public String toString(){
        return username + " password : " + password + " role: " + role;
    }

}
