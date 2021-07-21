package com.pablo.awardedpointscalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@AllArgsConstructor
public class TransactionDto {
    @NonNull
    private String customerCode;
    @NonNull
    private LocalDate transactionDate;
    @NonNull
    private BigDecimal transactionAmount;
}
