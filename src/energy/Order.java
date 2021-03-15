package energy;

import jade.core.AID;

import java.io.Serializable;
import java.util.UUID;

/**
 * Definition of order.
 * Wrap a energy in the order by adding an id (each order is unique),
 * a JADE Agent Identifier (contains the name and the address) of consumer who wants to buy energy
 * and a status which indicates whether the order is paid or not.
 *
 * @author Tomi Cottrelle
 * @version 1.0.0
 * @see Energy
 * @see Status
 */
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
