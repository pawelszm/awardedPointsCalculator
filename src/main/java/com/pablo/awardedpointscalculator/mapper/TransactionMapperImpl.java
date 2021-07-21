package com.pablo.awardedpointscalculator.mapper;

import com.pablo.awardedpointscalculator.dto.TransactionDto;
import com.pablo.awardedpointscalculator.model.TransactionInfo;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class TransactionMapperImpl implements TransactionMapper {
    private static final String SEPARATOR = "-";

    @Override
    public TransactionInfo dtoToModel(TransactionDto transactionDto) {
        LocalDate transactionDate = transactionDto.getTransactionDate();
        String monthWithYear = transactionDate.getMonth() + SEPARATOR + transactionDate.getYear();

        return new TransactionInfo(transactionDto.getCustomerCode(), monthWithYear, transactionDto.getTransactionAmount());
    }
}
