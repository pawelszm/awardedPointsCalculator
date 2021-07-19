package com.pablo.awardedpointscalculator.mapper;

import com.pablo.awardedpointscalculator.dto.TransactionDto;
import com.pablo.awardedpointscalculator.model.TransactionInfo;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public TransactionInfo dtoToModel(TransactionDto transactionDto) {
        StringBuilder monthWithYear = new StringBuilder();
        monthWithYear.append(transactionDto.transactionDate.getMonth()).append(transactionDto.transactionDate.getYear());

        return new TransactionInfo(transactionDto.customerCode, monthWithYear.toString(), transactionDto.transactionAmount);
    }
}
