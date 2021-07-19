package com.pablo.awardedpointscalculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionInfo {
    public String customerCode;
    public String monthWithYear;
    public BigDecimal transactionAmount;
}
