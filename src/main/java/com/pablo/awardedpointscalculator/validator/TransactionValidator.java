package com.pablo.awardedpointscalculator.validator;

import com.pablo.awardedpointscalculator.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

@Component
public class TransactionValidator {

    public void validateTransactionDtoList(List<TransactionDto> transactionDtoList) throws IllegalArgumentException{

        transactionDtoList.stream().forEach(this::validateTransactionDto);
    }

    public void validateDatesRange(List<TransactionDto> transactionDtoList) throws IllegalArgumentException{

        if(transactionDtoList.isEmpty()){
            return;
        }

        LocalDate oldestTransactionDate = transactionDtoList.get(0).getTransactionDate();
        LocalDate newestTransactionDate = transactionDtoList.get(transactionDtoList.size() - 1).getTransactionDate();

        Period period = Period.between(oldestTransactionDate, newestTransactionDate);
        int periodMonths = Math.abs(period.getMonths());
        int periodDays = Math.abs(period.getDays());

        if (periodMonths > 3 || (periodMonths == 3 && periodDays > 0)) {
            throw new IllegalArgumentException("Maximum interval between dates is 3 months");
        }
    }

    private void validateTransactionDto(TransactionDto transactionDto){

        String customerCode = transactionDto.getCustomerCode().trim();
        if(Objects.isNull(customerCode) || customerCode.length() == 0){
            throw new IllegalArgumentException("Customer code is required");
        }

        if(Objects.isNull(transactionDto.getTransactionDate())){
            throw new IllegalArgumentException("Transaction date is required");
        }

        if(transactionDto.getTransactionAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Transaction amount must be greater than 0 - current value: " + transactionDto.getTransactionAmount());
        }
    }
}
