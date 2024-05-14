package com.midgetspinner31.survey.db.entity;

import com.midgetspinner31.survey.enumerable.TransactionType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletTransaction {
    TransactionType type;
    BigDecimal amount;
    LocalDateTime createdAt;
    String payerId;
    String payeeId;

    String reason;
}
