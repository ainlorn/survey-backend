package com.midgetspinner31.survey.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WalletInfo {
    String id;
    BigDecimal balance;
    List<WalletTransactionInfo> transactions;
}
