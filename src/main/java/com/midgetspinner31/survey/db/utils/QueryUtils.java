package com.midgetspinner31.survey.db.utils;

import com.midgetspinner31.survey.db.entity.userdetails.AdditionalRespondentDetails;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.query.Criteria;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

public class QueryUtils {
    private QueryUtils() {
    }

    public static Criteria createRespondentRestrictionsCriteria(AdditionalRespondentDetails details) {

        int age = Period.between(details.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()).getYears();
        int income = details.getIncome();

        return new Criteria().andOperator(
                notExistsOr(Criteria.where("respondentRestrictions.minAge").lte(age)),
                notExistsOr(Criteria.where("respondentRestrictions.maxAge").gte(age)),
                notExistsOr(Criteria.where("respondentRestrictions.minIncome").lte(income)),
                notExistsOr(Criteria.where("respondentRestrictions.maxIncome").gte(income)),
                notExistsOrEmptyArrayOr(Criteria.where("respondentRestrictions.allowedGenders").in(List.of(details.getGender()))),
                notExistsOrEmptyArrayOr(Criteria.where("respondentRestrictions.allowedRegions").in(List.of(details.getRegion()))),
                notExistsOrEmptyArrayOr(Criteria.where("respondentRestrictions.allowedEducation").in(List.of(details.getEducationStatus()))),
                notExistsOrEmptyArrayOr(Criteria.where("respondentRestrictions.allowedFamilyStatus").in(List.of(details.getFamilyStatus())))
        );
    }

    private static Criteria notExistsOr(@NotNull Criteria criteria) {
        if (criteria.getKey() == null) {
            throw new NullPointerException("Не указан ключ в Criteria");
        }

        return new Criteria().orOperator(
                Criteria.where(criteria.getKey()).exists(false),
                criteria);
    }

    private static Criteria notExistsOrEmptyArrayOr(@NotNull Criteria criteria) {
        if (criteria.getKey() == null) {
            throw new NullPointerException("Не указан ключ в Criteria");
        }

        return new Criteria().orOperator(
                Criteria.where(criteria.getKey()).exists(false),
                Criteria.where(criteria.getKey()).size(0),
                criteria
        );
    }

}
