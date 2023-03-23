package ru.shipownerproject.utils.$dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import ru.shipownerproject.models.enums.Role;
import ru.shipownerproject.models.User;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO extends DTO {

    private String username;

    private String password;

    private Role role;

    public UserDTO(String username, String password) {
        this.password = password;
        this.username = username;
    }

    @Override
    public String toString() {
        return username + " role: " + role;
    }

    public static UserDTO convertToUserDTO(User user, ModelMapper modelMapper) {
        return modelMapper.map(user, UserDTO.class);
    }

    public static User convertToUser(UserDTO userDTO, ModelMapper modelMapper) {
        return modelMapper.map(userDTO, User.class);
    }
}
