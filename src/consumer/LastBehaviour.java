package consumer;

import jade.core.behaviours.OneShotBehaviour;

public class LastBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;

    public LastBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        System.out.println("consumer finish");
    }
}
