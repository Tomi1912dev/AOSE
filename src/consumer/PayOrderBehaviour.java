package consumer;

import jade.core.behaviours.OneShotBehaviour;

public class PayOrderBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;
    private int transition;

    public PayOrderBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {

    }

    public int onEnd() {
        return transition;
    }
}
