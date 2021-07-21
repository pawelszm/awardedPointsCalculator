package com.pablo.awardedpointscalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
@Getter
@AllArgsConstructor
public class TransactionInfo {
    @NonNull
    private String customerCode;
    @NonNull
    private String monthWithYear;
    @NonNull
    private BigDecimal transactionAmount;
}
