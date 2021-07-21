package com.pablo.awardedpointscalculator.service;

import com.pablo.awardedpointscalculator.dto.TransactionDto;
import com.pablo.awardedpointscalculator.mapper.TransactionMapper;
import com.pablo.awardedpointscalculator.model.Reward;
import com.pablo.awardedpointscalculator.model.TransactionInfo;
import com.pablo.awardedpointscalculator.validator.TransactionValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Range;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.pablo.awardedpointscalculator.constant.Constant.FIRST_THRESHOLD_50;
import static com.pablo.awardedpointscalculator.constant.Constant.SECOND_THRESHOLD_100;

@Service
@Slf4j
public class RewardService {

    @Autowired
    private TransactionMapper mapper;

    @Autowired
    private TransactionValidator validator;

    public RewardService(){
        mapper = Mappers.getMapper(TransactionMapper.class);
    }

    public List<Reward> calculateReward(List<TransactionDto> transactionDtoList) throws IllegalArgumentException {

        validator.validateTransactionDtoList(transactionDtoList);

        List<TransactionDto> sortedTransactionDtoList = transactionDtoList.stream()
                .sorted(Comparator.comparing(TransactionDto::getTransactionDate))
                .collect(Collectors.toList());

        validator.validateDatesRange(sortedTransactionDtoList);

        Map<String, Map<String, Integer>> pointsMap = new LinkedHashMap<>();

        sortedTransactionDtoList.stream()
                .map(mapper::dtoToModel)
                .forEach(transactionInfo -> addToPointsMap(transactionInfo, pointsMap));

        return pointsMap.entrySet().stream()
                .map(RewardService::apply)
                .collect(Collectors.toList());
    }

    private void addToPointsMap(TransactionInfo transactionInfo, Map<String, Map<String, Integer>> customerPoints) {

        Map<String, Integer> pointsPerMonths = customerPoints.getOrDefault(transactionInfo.getCustomerCode(), new LinkedHashMap<>());

        Integer rewardPointsBeforeUpdate = pointsPerMonths.getOrDefault(transactionInfo.getMonthWithYear(), 0);
        Integer newRewardPoints = calculateRewardPoints(transactionInfo.getTransactionAmount());
        pointsPerMonths.put(transactionInfo.getMonthWithYear(), rewardPointsBeforeUpdate + newRewardPoints);

        customerPoints.put(transactionInfo.getCustomerCode(), pointsPerMonths);
    }

    private static Reward apply(Map.Entry<String, Map<String, Integer>> entry) {
        Map<String, Integer> pointsPerEachMonth = entry.getValue();

        Integer totalPoints = pointsPerEachMonth.values().stream()
                .mapToInt(Integer::valueOf)
                .sum();

        return new Reward(entry.getKey(), pointsPerEachMonth, totalPoints);
    }

    private Integer calculateRewardPoints(BigDecimal transactionAmount){

        Range<BigDecimal> standardPointsRange = Range.between(FIRST_THRESHOLD_50, SECOND_THRESHOLD_100);

        if(standardPointsRange.contains(transactionAmount)){
            return transactionAmount.subtract(FIRST_THRESHOLD_50).intValue();
        }
        else if(transactionAmount.compareTo(SECOND_THRESHOLD_100) > 0){
            Integer doubledPoints = transactionAmount.subtract(SECOND_THRESHOLD_100).intValue()*2;
            Integer standardPoints = FIRST_THRESHOLD_50.intValue();
            return doubledPoints + standardPoints;
        }
        return 0;
    }
}