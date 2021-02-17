package energy;

import javafx.util.Pair;

import java.io.Serializable;

public class Energy implements Serializable {
    private int id;
    private Type type;
    private double price;
    private Pair<Integer, Integer> hourConsumption;

    public Energy(int id, Type type, double price, Pair<Integer, Integer> hourConsumption) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.hourConsumption = hourConsumption;
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

    public Pair<Integer, Integer> getHourConsumption() {
        return hourConsumption;
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
                .append(hourConsumption.getKey() + "h/" + hourConsumption.getValue() + "h]");
        return sb.toString();
    }
}
