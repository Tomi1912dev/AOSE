package consumer;

import jade.core.behaviours.OneShotBehaviour;

public class MakeOrderBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;
    private int transition;

    public MakeOrderBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {

    }

    public int onEnd() {
        return transition;
    }
}
