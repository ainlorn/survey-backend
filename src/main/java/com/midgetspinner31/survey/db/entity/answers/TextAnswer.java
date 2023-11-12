package com.midgetspinner31.survey.db.entity.answers;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TextAnswer extends AbstractAnswer {
    String text;
}
