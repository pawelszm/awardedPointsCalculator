package com.pablo.awardedpointscalculator.mapper;

import com.pablo.awardedpointscalculator.dto.TransactionDto;
import com.pablo.awardedpointscalculator.model.TransactionInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TransactionMapper {

    TransactionInfo dtoToModel(final TransactionDto transactionDto);
}