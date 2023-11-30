package com.midgetspinner31.survey.web.request;

import com.midgetspinner31.survey.db.entity.userdetails.AdditionalSurveyCreatorDetails;
import com.midgetspinner31.survey.dto.UserSignUpInfo;
import com.midgetspinner31.survey.enumerable.AccountType;
import com.midgetspinner31.survey.validation.constraint.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SurveyCreatorSignUpRequest extends BaseRequest {
    /**
     * Название компании
     */
    @Size(max=128)
    @NotEmpty
    String name;

    /**
     * О себе
     */
    @Size(max=5000)
    @NotEmpty
    String about;

    /**
     * Слоган
     */
    @Size(max=500)
    @NotEmpty
    String slogan;

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
                AccountType.survey_creator,
                email,
                phoneNumber,
                password,
                new AdditionalSurveyCreatorDetails(name, about, slogan)
        );
    }

    public void setName(String name) {
        this.name = trim(name);
    }

    public void setAbout(String about) {
        this.about = trim(about);
    }

    public void setSlogan(String slogan) {
        this.slogan = trim(slogan);
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
