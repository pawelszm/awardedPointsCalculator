package com.pablo.awardedpointscalculator.model;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
public class Reward {
    @NonNull
    public String customerCode;
    @NonNull
    public Map<String, Integer> pointsPerEachMonth;
    @NonNull
    public Integer totalPoints;

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        public String customerCode;
        public Map<String, Integer> pointsPerEachMonth;
        public Integer totalPoints;

        public Builder customerCode(String customerCode) {
            this.customerCode = customerCode;
            return this;
        }

        public Builder pointsPerEachMonth(Map<String, Integer> pointsPerEachMonth) {
            this.pointsPerEachMonth = pointsPerEachMonth;
            return this;
        }

        public Builder totalPoints(Integer totalPoints) {
            this.totalPoints = totalPoints;
            return this;
        }

        public Reward build(){
            return new Reward(customerCode, pointsPerEachMonth, totalPoints);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reward reward = (Reward) o;
        return customerCode.equals(reward.customerCode) &&
                pointsPerEachMonth.equals(reward.pointsPerEachMonth) &&
                totalPoints.equals(reward.totalPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerCode, pointsPerEachMonth, totalPoints);
    }
}
