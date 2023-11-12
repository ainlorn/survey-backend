package com.midgetspinner31.survey.db.entity.answers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TextAnswer extends Answer {
    String text;
}
