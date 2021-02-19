package producer;

import jade.core.behaviours.OneShotBehaviour;

public class ConfirmOrderBehaviour extends OneShotBehaviour {
    private ProducerAgent agent;

    public ConfirmOrderBehaviour(ProducerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        agent.doWait(1000);
        agent.confirmOrder();
    }
}
