package producer;

import jade.core.behaviours.OneShotBehaviour;

public class PublishBehaviour extends OneShotBehaviour {
    private ProducerAgent agent;

    public PublishBehaviour(ProducerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        agent.doWait(1000);
        agent.submitEnergyPrice();
    }
}
