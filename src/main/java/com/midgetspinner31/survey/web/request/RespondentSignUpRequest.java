package com.midgetspinner31.survey.web.request;

import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import com.midgetspinner31.survey.dto.UserSignUpInfo;
import com.midgetspinner31.survey.enumerable.AccountType;
import com.midgetspinner31.survey.validation.constraint.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RespondentSignUpRequest extends BaseRequest {
    /**
     * Имя
     */
    @Pattern(regexp = "^[a-zA-Zа-яА-Я- ]{1,32}$")
    @NotEmpty
    String firstName;

    /**
     * Фамилия
     */
    @Pattern(regexp = "^[a-zA-Zа-яА-Я- ]{1,32}$")
    @NotEmpty
    String lastName;

    /**
     * Адрес электронной почты
     */
    @Email
    @Size(max = 255)
    @NotEmpty
    String email;

    /**
     * Номер телефона в формате +7xxxxxxxxxx
     */
    @PhoneNumber
    @NotEmpty
    String phoneNumber;

    /**
     * Пароль
     */
    @Size(min = 8, max = 72)
    @NotEmpty
    String password;

    public UserSignUpInfo toUserSignUpInfo() {
        return new UserSignUpInfo(
                AccountType.respondent,
                email,
                phoneNumber,
                password,
                new AdditionalRespondentDetails(firstName, lastName)
        );
    }

    public void setFirstName(String firstName) {
        this.firstName = trim(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = trim(lastName);
    }

    public void setEmail(String email) {
        this.email = trim(email);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = trim(phoneNumber);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
