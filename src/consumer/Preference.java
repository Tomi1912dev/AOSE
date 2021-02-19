package consumer;

import java.io.Serializable;

public class Preference implements Serializable {
    private Policy policy;
    private double budget;
    private double maxBudget;
    private int upperBoundHour;
    private int lowerBoundHour;

    public Preference(Policy policy, double budget, double maxBudget, int upperBoundHour, int lowerBoundHour) {
        this.policy = policy;
        this.budget = budget;
        this.maxBudget = maxBudget;
        this.upperBoundHour = upperBoundHour;
        this.lowerBoundHour = lowerBoundHour;
    }

    public Policy getPolicy() {
        return policy;
    }

    public double getBudget() {
        return budget;
    }

    public double getMaxBudget() {
        return maxBudget;
    }

    public int getUpperBoundHour() {
        return upperBoundHour;
    }

    public int getLowerBoundHour() {
        return lowerBoundHour;
    }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(policy.toString()+", ");
        sb.append(budget+", ");
        sb.append(maxBudget+", ");
        sb.append(upperBoundHour+", ");
        sb.append(lowerBoundHour+", ");
        sb.append("]");
        return sb.toString();
    }
}
