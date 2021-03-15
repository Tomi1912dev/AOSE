package consumer;

import jade.core.behaviours.OneShotBehaviour;

/**
 * Register on the marketplace.
 * Send a JADE Agent Identifier (contains the name and the address).
 *
 * @author Tomi Cottrelle
 * @version 1.0.0
 * @see ConsumerAgent#register()
 */
public class RegisterBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;

    public RegisterBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        agent.register();
    }
}
