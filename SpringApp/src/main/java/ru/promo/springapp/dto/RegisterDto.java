package ru.promo.springapp.dto;

import lombok.Data;
import ru.promo.springapp.model.ProfileRole;

import javax.validation.constraints.Email;

@Data
public class RegisterDto {
    private String login;
    private String password;
    private ProfileRole role;
    private String fio;
    @Email
    private String email;
}
