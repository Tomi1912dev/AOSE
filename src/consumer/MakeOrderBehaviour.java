package consumer;

import energy.Energy;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.HashMap;

public class MakeOrderBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;
    private int transition;

    public MakeOrderBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        //receive energies proposed by the marketplace
        agent.doWait(1000);
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
        ACLMessage response = agent.receive(mt);
        if (response != null) {
            try {
                Energy[] energies = (Energy[]) response.getContentObject();
                if(energies != null && energies.length > 0) {
                    agent.makeOrder(energies[0]);
                }
            } catch (UnreadableException e) { e.printStackTrace(); }
        }
    }

    public int onEnd() {
        return transition;
    }
}
