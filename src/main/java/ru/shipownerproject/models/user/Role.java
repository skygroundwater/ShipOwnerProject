package ru.shipownerproject.models.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ROLE_ADMIN(1, "Administrator"), ROLE_USER(2,"User"), ROLE_GUEST(3,"Guest");

    private final int id;

    private final String role;

    Role(Byte id, String role){
        this.id=id;
        this.role= role;
    }
}
