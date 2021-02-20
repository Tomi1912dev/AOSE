package producer;

import jade.core.behaviours.OneShotBehaviour;

public class RegisterBehaviour extends OneShotBehaviour {
    private ProducerAgent agent;

    public RegisterBehaviour(ProducerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        //agent.doWait(1000);
        agent.doWait(5000);
        agent.register();
    }
}
