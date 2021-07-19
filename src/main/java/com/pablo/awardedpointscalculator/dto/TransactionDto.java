package com.pablo.awardedpointscalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TransactionDto {

    @NonNull
    public String customerCode;

    @NonNull
    public LocalDate transactionDate;

    @NonNull
    public BigDecimal transactionAmount;
}
