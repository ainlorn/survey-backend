package com.midgetspinner31.survey.web.request;

import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalUserDetails;
import com.midgetspinner31.survey.dto.BirthDateInfo;
import com.midgetspinner31.survey.enumerable.EducationStatus;
import com.midgetspinner31.survey.enumerable.FamilyStatus;
import com.midgetspinner31.survey.enumerable.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRespondentDetailsRequest extends BaseRequest {
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
     * Дата рождения
     */
    @Valid
    @NotNull
    BirthDateInfo birthDate;

    /**
     * Пол
     * Допустимые значения: male, female
     */
    @NotNull
    Gender gender;

    /**
     * Регион
     */
    @NotEmpty
    String region;

    /**
     * Семейное положение
     * Допустимые значения:
     *     not_married - Не замужем / Не женат
     *     married - Замужем / Женат
     *     divorced - Разведен / Разведена
     *     widowed - Вдовец / Вдова
     */
    @NotNull
    FamilyStatus familyStatus;

    /**
     * Образование
     * Допустимые значения:
     *     none - Нет образования
     *     basic_general - Основное общее
     *     secondary_general - Среднее общее
     *     secondary_professional - Среднее профессиональное
     *     bachelor_degree - Высшее, бакалавр
     *     master_degree - Высшее, магистр
     *     highest_qualification - Высшая квалификация
     */
    @NotNull
    EducationStatus educationStatus;

    /**
     * Уровень доходов в рублях
     */
    @Min(0)
    @Max(10000000)
    @NotNull
    Integer income;

    public AdditionalUserDetails toAdditionalUserDetails() {
        return new AdditionalRespondentDetails(
                firstName,
                lastName,
                birthDate.toDate(),
                gender,
                region,
                familyStatus,
                educationStatus,
                income
        );
    }

    public void setFirstName(String firstName) {
        this.firstName = trim(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName = trim(lastName);
    }

    public void setBirthDate(BirthDateInfo birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setRegion(String region) {
        this.region = trim(region);
    }

    public void setFamilyStatus(FamilyStatus familyStatus) {
        this.familyStatus = familyStatus;
    }

    public void setEducationStatus(EducationStatus educationStatus) {
        this.educationStatus = educationStatus;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }
}
