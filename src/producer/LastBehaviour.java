package producer;

import jade.core.behaviours.OneShotBehaviour;

public class LastBehaviour extends OneShotBehaviour {
    private ProducerAgent agent;

    public LastBehaviour(ProducerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        System.out.println("producer finish");
    }
}
