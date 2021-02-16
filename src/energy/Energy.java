package energy;

import javafx.util.Pair;

public class Energy {
    private int id;
    private Type type;
    private double price;
    private Pair<Time, Time> hourConsumption;

    public Energy(int id, Type type, double price, Pair<Time, Time> hourConsumption) {
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

    public Pair<Time, Time> getHourConsumption() {
        return hourConsumption;
    }
}
