package consumer;

import java.io.Serializable;

public class Preference implements Serializable {
    private Policy policy;
    private double budget;
    private double maxBudget;
    private int lowerBoundHour;
    private int upperBoundHour;

    public Preference(Policy policy, double budget, double maxBudget, int lowerBoundHour, int upperBoundHour) {
        this.policy = policy;
        this.budget = budget;
        this.maxBudget = maxBudget;
        this.lowerBoundHour = lowerBoundHour;
        this.upperBoundHour = upperBoundHour;
    }

    public Policy getPolicy() {
        return policy;
    }

    public double getBudget() {
        return budget;
    }

    public int getLowerBoundHour() {
        return lowerBoundHour;
    }

    public int getUpperBoundHour() {
        return upperBoundHour;
    }

    public void updateBudget() {
        this.budget = this.maxBudget;
    }
}
