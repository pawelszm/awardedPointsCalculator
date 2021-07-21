package com.pablo.awardedpointscalculator.service;

import com.pablo.awardedpointscalculator.dto.TransactionDto;
import com.pablo.awardedpointscalculator.model.Reward;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RewardServiceTest {

    @Autowired
    private RewardService rewardService;

    private static final String CUSTOMER_MARK = "Mark";
    private static final String CUSTOMER_TOM = "Tom";
    private static final String CUSTOMER_KATE = "Kate";
    private static final LocalDate MARCH_10 = LocalDate.of(2021, 3, 10);
    private static final LocalDate APRIL_12 = LocalDate.of(2021, 4, 12);
    private static final LocalDate MAY_12 = LocalDate.of(2021, 5, 12);
    private static final LocalDate JUNE_12 = LocalDate.of(2021, 6, 12);
    private static final String APRIL2021 = "APRIL-2021";
    private static final String MAY2021 = "MAY-2021";
    private static final String JUNE2021 = "JUNE-2021";

    @Test
    void shouldReturnEmptyRewardsList(){

        // given
        // when
        List<Reward> rewards = rewardService.calculateReward(prepareDto());
        // then
        assertEquals(rewards, Collections.emptyList());
    }

    @Test
    void shouldReturnOneReward() {

        // given
        TransactionDto firstTransaction = createTransactionDto(CUSTOMER_MARK, APRIL_12, BigDecimal.valueOf(90L));

        Map<String, Integer> monthsPoints = Map.of(APRIL2021, 40);
        Reward reward = createReward(CUSTOMER_MARK, monthsPoints, 40);

        // when
        List<Reward> rewards = rewardService.calculateReward(prepareDto(firstTransaction));
        // then
        assertEquals(rewards, Collections.singletonList(reward));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionBecauseOfIncorrectAmount() {

        // given
        TransactionDto firstTransaction = createTransactionDto(CUSTOMER_MARK, APRIL_12, BigDecimal.valueOf(-90L));
        String expectedMessage = "Transaction amount must be greater than 0 - current value: -90";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.calculateReward(prepareDto(firstTransaction));
        });
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionBecauseOfIncorrectCustomerCode() {

        // given
        TransactionDto firstTransaction = createTransactionDto("", APRIL_12, BigDecimal.valueOf(90L));
        String expectedMessage = "Customer code is required";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.calculateReward(prepareDto(firstTransaction));
        });
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionBecauseOfTooLongInterval() {

        // given
        TransactionDto firstTransaction = createTransactionDto(CUSTOMER_MARK, MARCH_10, BigDecimal.valueOf(90L));
        TransactionDto secondTransaction = createTransactionDto(CUSTOMER_MARK, JUNE_12, BigDecimal.valueOf(90L));
        String expectedMessage = "Maximum interval between dates is 3 months";

        // when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.calculateReward(prepareDto(firstTransaction, secondTransaction));
        });
        String actualMessage = exception.getMessage();

        // then
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldReturnRewardsFromThreeMonthForOneCustomer(){

        // given
        TransactionDto firstTransaction = createTransactionDto(CUSTOMER_MARK, APRIL_12, BigDecimal.valueOf(90L));
        TransactionDto secondTransaction = createTransactionDto(CUSTOMER_MARK, MAY_12, BigDecimal.valueOf(70L));
        TransactionDto thirdTransaction = createTransactionDto(CUSTOMER_MARK, JUNE_12, BigDecimal.valueOf(200L));

        Map<String, Integer> monthsPoints = Map.of(
                APRIL2021, 40,
                MAY2021, 20,
                JUNE2021, 250
        );

        Reward reward = createReward(CUSTOMER_MARK, monthsPoints, 310);

        // when
        List<Reward> rewards = rewardService.calculateReward(prepareDto(firstTransaction, secondTransaction, thirdTransaction));
        // then
        assertEquals(rewards, Collections.singletonList(reward));
    }

    @Test
    void shouldReturnMultiRewardsForMultiCustomers(){

        // given
        TransactionDto firstTransaction = createTransactionDto(CUSTOMER_MARK, APRIL_12, BigDecimal.valueOf(90L));
        TransactionDto secondTransaction = createTransactionDto(CUSTOMER_TOM, MAY_12, BigDecimal.valueOf(70L));
        TransactionDto thirdTransaction = createTransactionDto(CUSTOMER_KATE, JUNE_12, BigDecimal.valueOf(200L));

        Map<String, Integer> monthsPointsForMark = Map.of(APRIL2021, 40);
        Reward rewardForMark = createReward(CUSTOMER_MARK, monthsPointsForMark, 40);

        Map<String, Integer> monthsPointsForTom = Map.of(MAY2021, 20);
        Reward rewardForTom = createReward(CUSTOMER_TOM, monthsPointsForTom, 20);

        Map<String, Integer> monthsPointsForKate = Map.of(JUNE2021, 250);
        Reward rewardForKate = createReward(CUSTOMER_KATE, monthsPointsForKate, 250);

        List<Reward> expectedRewards = Arrays.asList(rewardForMark, rewardForTom, rewardForKate);

        // when
        List<Reward> rewards = rewardService.calculateReward(prepareDto(firstTransaction, secondTransaction, thirdTransaction));
        // then
        assertEquals(rewards, expectedRewards);
    }

    @Test
    void shouldReturnRewardPointsOnlyForOneCustomer(){

        // given
        TransactionDto firstTransaction = createTransactionDto(CUSTOMER_MARK, APRIL_12, BigDecimal.valueOf(30L));
        TransactionDto secondTransaction = createTransactionDto(CUSTOMER_TOM, MAY_12, BigDecimal.valueOf(70L));
        TransactionDto thirdTransaction = createTransactionDto(CUSTOMER_KATE, JUNE_12, BigDecimal.valueOf(10L));

        Map<String, Integer> monthsPointsForMark = Map.of(APRIL2021, 0);
        Reward rewardForMark = createReward(CUSTOMER_MARK, monthsPointsForMark, 0);

        Map<String, Integer> monthsPointsForTom = Map.of(MAY2021, 20);
        Reward rewardForTom = createReward(CUSTOMER_TOM, monthsPointsForTom, 20);

        Map<String, Integer> monthsPointsForKate = Map.of(JUNE2021, 0);
        Reward rewardForKate = createReward(CUSTOMER_KATE, monthsPointsForKate, 0);

        List<Reward> expectedRewards = Arrays.asList(rewardForMark, rewardForTom, rewardForKate);

        // when
        List<Reward> rewards = rewardService.calculateReward(prepareDto(firstTransaction, secondTransaction, thirdTransaction));
        // then
        assertEquals(rewards, expectedRewards);
    }

    private List<TransactionDto> prepareDto(TransactionDto... transactionDto){

        List<TransactionDto> transactionDtoList = Arrays.asList(transactionDto);
        return transactionDtoList;
    }

    private TransactionDto createTransactionDto(String customerCode, LocalDate transactionDate, BigDecimal transactionAmount){
        return new TransactionDto(customerCode, transactionDate, transactionAmount);
    }

    private Reward createReward(String customerCode, Map<String, Integer> pointsPerEachMonth, Integer totalPoints){

        Reward reward = Reward.builder()
                .customerCode(customerCode)
                .pointsPerEachMonth(pointsPerEachMonth)
                .totalPoints(totalPoints)
                .build();
        return reward;
    }
}
