package com.midgetspinner31.survey.web.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignInRequest {
    /**
     * Адрес электронной почты или номер телефона
     */
    @Size(max = 255)
    @NotEmpty
    String username;

    /**
     * Пароль
     */
    @Size(max = 72)
    @NotEmpty
    String password;

    public void setUsername(String username) {
        this.username = username.strip();
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
