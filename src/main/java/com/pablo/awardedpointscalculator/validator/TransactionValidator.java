package com.pablo.awardedpointscalculator.validator;

import com.pablo.awardedpointscalculator.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class TransactionValidator {

    public void validateTransactionDtoList(List<TransactionDto> transactionDtoList) throws IllegalArgumentException{

        transactionDtoList.stream().forEach(this::validateTransactionDto);
    }

    private void validateTransactionDto(TransactionDto transactionDto){

        String customerCode = transactionDto.customerCode.trim();
        if(Objects.isNull(customerCode) || customerCode.length() == 0){
            throw new IllegalArgumentException("Customer code is required");
        }

        if(Objects.isNull(transactionDto.transactionDate)){
            throw new IllegalArgumentException("Transaction date is required");
        }

        if(transactionDto.transactionAmount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Transaction amount must be greater than 0 - current value: " + transactionDto.transactionAmount);
        }
    }
}
