package producer;

import jade.core.behaviours.OneShotBehaviour;

/**
 * Behaviour which allows to say that the producer agent has finished its execution.
 *
 * @author Tomi Cottrelle
 * @version 1.0.0
 */
public class LastBehaviour extends OneShotBehaviour {
    private ProducerAgent agent;

    public LastBehaviour(ProducerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        System.out.println("producer finish");
    }
}
