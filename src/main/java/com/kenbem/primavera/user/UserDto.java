package com.kenbem.primavera.user;

import com.kenbem.primavera.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private String password;

    public UserDto (User user){

    }
}
