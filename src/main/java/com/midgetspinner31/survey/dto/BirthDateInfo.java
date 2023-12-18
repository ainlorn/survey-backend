package com.midgetspinner31.survey.dto;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.midgetspinner31.survey.exception.IncorrectDateException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BirthDateInfo {
    @Min(1)
    @Max(31)
    @NotNull
    Integer day;

    @Min(1)
    @Max(12)
    @NotNull
    Integer month;

    @Min(1900)
    @Max(2100)
    @NotNull
    Integer year;

    public Date toDate() {
        var calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month - 1, day);
        if (calendar.get(Calendar.DATE) != day
                || calendar.get(Calendar.MONTH) + 1 != month
                || calendar.get(Calendar.YEAR) != year)
            throw new IncorrectDateException();
        return calendar.getTime();
    }

    public static BirthDateInfo fromDate(Date date) {
        var calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new BirthDateInfo(
                calendar.get(Calendar.DATE),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR)
        );
    }

    public static class BirthDateConverter implements Converter<Date, BirthDateInfo> {
        @Override
        public BirthDateInfo convert(Date value) {
            return BirthDateInfo.fromDate(value);
        }

        @Override
        public JavaType getInputType(TypeFactory typeFactory) {
            return TypeFactory.defaultInstance().constructType(Date.class);
        }

        @Override
        public JavaType getOutputType(TypeFactory typeFactory) {
            return TypeFactory.defaultInstance().constructType(BirthDateInfo.class);
        }
    }
}
