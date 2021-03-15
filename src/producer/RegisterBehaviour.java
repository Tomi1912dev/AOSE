package producer;

import jade.core.behaviours.OneShotBehaviour;

/**
 * Register on the marketplace.
 * Send a JADE Agent Identifier (contains the name and the address).
 *
 * @author Tomi Cottrelle
 * @version 1.0.0
 * @see ProducerAgent#register()
 */
public class RegisterBehaviour extends OneShotBehaviour {
    private ProducerAgent agent;

    public RegisterBehaviour(ProducerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        agent.register();
    }
}
