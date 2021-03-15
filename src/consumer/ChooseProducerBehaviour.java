package consumer;

import jade.core.behaviours.OneShotBehaviour;

/**
 * Choose an energy of producer.
 * Receive the energies proposed by the marketplace which corresponds to his default preferences.
 * He makes a request for the energy that interests him.
 * If there is no more energy available, he requests a list of energy from the marketplace
 * with a higher budget and tries again.
 *
 * @author Tomi Cottrelle
 * @version 1.0.0
 * @see ConsumerAgent#choose()
 */
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
