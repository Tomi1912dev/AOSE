package energy;

import jade.core.AID;

import java.io.Serializable;

public class Energy implements Serializable, Comparable<Energy> {
    private Type type;
    private double price;
    private int quantity;
    private int lowerBoundHour;
    private int upperBoundHour;
    private AID producer;

    public Energy(Type type, double price, int quantity, int lowerBoundHour, int upperBoundHour) {
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.lowerBoundHour = lowerBoundHour;
        this.upperBoundHour = upperBoundHour;
    }

    public Type getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLowerBoundHour() {
        return lowerBoundHour;
    }

    public int getUpperBoundHour() {
        return upperBoundHour;
    }

    public AID getProducer() {
        return producer;
    }

    public void setProducer(AID producer) {
        this.producer = producer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append(type)
                .append(", ")
                .append(price)
                .append("€, ")
                .append(quantity)
                .append(" Qty, ")
                .append(lowerBoundHour + "h/" + upperBoundHour + "h, ")
                .append(producer.getName() + "]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        Energy energy = (Energy) obj;
        return this.getType() == energy.getType()
                && this.getPrice() == energy.getPrice()
                && this.getLowerBoundHour() == energy.getLowerBoundHour()
                && this.getUpperBoundHour() == energy.getUpperBoundHour()
                && this.getProducer().equals(energy.getProducer());
    }

    @Override
    public int compareTo(Energy energy) {
        return this.getPrice() < energy.getPrice() ? 1 : 0;
    }
}
