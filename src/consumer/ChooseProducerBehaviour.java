package consumer;

import jade.core.behaviours.OneShotBehaviour;

public class ChooseProducerBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;

    public ChooseProducerBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        agent.doWait(10000);
        if(agent.getBackState()) {
            agent.getPreference().updateBudget();
            agent.choose();
            agent.setBackState(false);
        } else {
            agent.choose();
        }
    }
}
