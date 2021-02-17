package consumer;

import jade.core.behaviours.OneShotBehaviour;

public class ChooseProducerBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;
    private int transition;

    public ChooseProducerBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {

    }

    public int onEnd() {
        return transition;
    }
}
