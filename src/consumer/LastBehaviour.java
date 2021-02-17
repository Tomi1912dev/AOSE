package consumer;

import jade.core.behaviours.OneShotBehaviour;

public class LastBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;
    private int transition;

    public LastBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {

    }

    public int onEnd() {
        return transition;
    }
}
