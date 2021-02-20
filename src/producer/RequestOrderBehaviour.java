package producer;

import energy.Energy;
import energy.Order;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class RequestOrderBehaviour extends OneShotBehaviour {
    private ProducerAgent agent;
    private int transition;

    public RequestOrderBehaviour(ProducerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        this.transition = 1;
        agent.doWait(1000);
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage message = agent.receive(mt);
        if (message != null && message.getConversationId().equals("make-order")) {
            try {
                Order order = (Order) message.getContentObject();
                ACLMessage response = message.createReply();
                response.setConversationId("request-order");
                response.setPerformative(ACLMessage.DISCONFIRM);
                response.setContent("full");

                for(Energy energy : agent.getEnergies()) {
                    if(energy.equals(order.getEnergy()) && energy.getQuantity() > 0) {
                        response.setPerformative(ACLMessage.CONFIRM);
                        response.setContent("available");
                        this.transition = 0;
                        break;
                    }
                }
                agent.send(response);
            } catch (UnreadableException e) { e.printStackTrace(); }
        }
    }

    public int onEnd() {
        return transition;
    }
}
