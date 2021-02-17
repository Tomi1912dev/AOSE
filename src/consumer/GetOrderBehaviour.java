package consumer;

import jade.core.behaviours.OneShotBehaviour;

public class GetOrderBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;
    private int transition;

    public GetOrderBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {

    }

    public int onEnd() {
        return transition;
    }
}
