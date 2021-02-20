package energy;

import jade.core.AID;

import java.io.Serializable;
import java.util.UUID;

public class Order implements Serializable {
    private String id;
    private AID consumer;
    private Energy energy;
    private Status status;

    public Order(AID consumer, Energy energy) {
        this.id = UUID.randomUUID().toString();
        this.consumer = consumer;
        this.energy = energy;
        this.status = Status.UNPAID;
    }

    public String getId() {
        return id;
    }

    public AID getConsumer() {
        return consumer;
    }

    public Energy getEnergy() {
        return energy;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        sb.append(id + ", ")
                .append(consumer.getName() + ", ")
                .append(energy.toString() + ", ")
                .append(status + "]");
        return sb.toString();
    }
}
