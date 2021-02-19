package consumer;

import jade.core.behaviours.OneShotBehaviour;

public class RegisterBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;

    public RegisterBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        agent.doWait(5000);
        agent.register();
    }
}
