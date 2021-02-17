package producer;

import jade.core.behaviours.OneShotBehaviour;

public class LastBehaviour extends OneShotBehaviour {
    private ProducerAgent agent;
    private int transition;

    public LastBehaviour(ProducerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {

    }

    public int onEnd() {
        return transition;
    }
}
