package com.midgetspinner31.survey.web.request;

import com.midgetspinner31.survey.db.entity.userdetails.AdditionalSurveyCreatorDetails;
import com.midgetspinner31.survey.db.entity.userdetails.AdditionalUserDetails;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCreatorDetailsRequest extends BaseRequest {
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

    public AdditionalUserDetails toAdditionalUserDetails() {
        return new AdditionalSurveyCreatorDetails(name, about, slogan);
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
}
