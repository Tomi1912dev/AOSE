package consumer;

import energy.Energy;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

/**
 * Receive the energies proposed by the marketplace.
 * Make order to producer.
 *
 * @author Tomi Cottrelle
 * @version 1.0.0
 */
public class MakeOrderBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;
    private int transition;

    public MakeOrderBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        this.transition = 1;
        agent.doWait(1000);
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
        ACLMessage response = agent.receive(mt);
        if(response != null) {
            try {
                Energy[] energies = (Energy[]) response.getContentObject();
                if(energies != null && energies.length > 0) {
                    agent.setEnergyProposed((Energy[]) response.getContentObject());
                    agent.makeOrder();
                    this.transition = 0;
                } else {
                    //search with more budget
                    agent.setBackState(true);
                }
            } catch (UnreadableException e) { e.printStackTrace(); }
        } else if(agent.getBackState()) {
            if(agent.getEnergySelected() <= agent.getEnergyProposed().length) {
                agent.makeOrder();
                this.transition = 0;
                agent.setBackState(false);
            } else {
                agent.setBackState(true);
            }
        }
    }

    public int onEnd() {
        return transition;
    }
}
