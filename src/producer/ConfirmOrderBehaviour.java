package producer;

import jade.core.behaviours.OneShotBehaviour;

/**
 * Confirm order of consumer.
 * If the producer has no more available energy then he stops his execution
 * otherwise it remains on standby.
 *
 * @author Tomi Cottrelle
 * @version 1.0.0
 * @see ProducerAgent#confirmOrder()
 */
public class ConfirmOrderBehaviour extends OneShotBehaviour {
    private ProducerAgent agent;
    private int transition;

    public ConfirmOrderBehaviour(ProducerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        this.transition = 1;
        agent.doWait(1000);
        agent.confirmOrder();
        if(agent.allEnergyEmpty()) {
            this.transition = 0;
        }
    }

    public int onEnd() {
        return transition;
    }
}
