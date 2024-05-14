package com.midgetspinner31.survey.db.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.util.List;

@Document("wallets")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Wallet {
    @Id
    String id;

    @Field("user_id")
    @NotNull
    String userId;

    @Field(value = "balance", targetType = FieldType.DECIMAL128)
    @NotNull
    BigDecimal balance;

    @Field(value = "transactions")
    List<WalletTransaction> transactions;
}
