package com.midgetspinner31.survey.dto;

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
public class WalletTransactionInfo {

    TransactionType type;
    BigDecimal amount;
    LocalDateTime createdAt;

    String reason;
}
