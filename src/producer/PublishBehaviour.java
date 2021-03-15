package producer;

import jade.core.behaviours.OneShotBehaviour;

/**
 * Submit a list of energy to the marketplace.
 *
 * @author Tomi Cottrelle
 * @version 1.0.0
 * @see ProducerAgent#submitEnergyPrice()
 */
public class PublishBehaviour extends OneShotBehaviour {
    private ProducerAgent agent;

    public PublishBehaviour(ProducerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        agent.submitEnergyPrice();
    }
}
