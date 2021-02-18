package energy;

import jade.core.AID;
import javafx.util.Pair;

import java.io.Serializable;

public class Energy implements Serializable, Comparable<Energy> {
    private int id;
    private Type type;
    private double price;
    private int lowerBoundHour;
    private int upperBoundHour;
    private AID producer;

    public Energy(int id, Type type, double price, int lowerBoundHour, int upperBoundHour) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.lowerBoundHour = lowerBoundHour;
        this.upperBoundHour = upperBoundHour;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public double getPrice() {
        return price;
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
        sb.append(id)
                .append(", ")
                .append(type)
                .append(", ")
                .append(price)
                .append(", ")
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
                && this.getProducer() == energy.getProducer();
    }

    @Override
    public int compareTo(Energy energy) {
        return this.getId() < energy.getId() ? 1 : 0;
    }
}
