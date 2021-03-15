package consumer;

import jade.core.behaviours.OneShotBehaviour;

/**
 * Behaviour which allows to say that the consumer agent has finished its execution.
 *
 * @author Tomi Cottrelle
 * @version 1.0.0
 */
public class LastBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;

    public LastBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        System.out.println("consumer finish");
    }
}
