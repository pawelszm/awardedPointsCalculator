package com.pablo.awardedpointscalculator.controller;

import com.pablo.awardedpointscalculator.dto.TransactionDto;
import com.pablo.awardedpointscalculator.exceptions.ExceptionHandling;
import com.pablo.awardedpointscalculator.model.Reward;
import com.pablo.awardedpointscalculator.service.RewardService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CalculatorController extends ExceptionHandling {

    private final RewardService rewardService;

    @PostMapping("/calculate")
    @Operation(description = "calculate reward", summary = "calculate reward")
    public ResponseEntity<List<Reward>> calculateReward(@RequestBody List<TransactionDto> transactionDtoList) throws IllegalArgumentException {
        return new ResponseEntity<>(rewardService.calculateReward(transactionDtoList), HttpStatus.OK);
    }
}
