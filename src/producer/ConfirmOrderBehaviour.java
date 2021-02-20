package producer;

import jade.core.behaviours.OneShotBehaviour;

public class ConfirmOrderBehaviour extends OneShotBehaviour {
    private ProducerAgent agent;
    private int transition;

    public ConfirmOrderBehaviour(ProducerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        this.transition = 1;
        agent.doWait(10000);
        agent.confirmOrder();
        if(agent.allEnergyEmpty()) {
            this.transition = 0;
        }
    }

    public int onEnd() {
        return transition;
    }
}
