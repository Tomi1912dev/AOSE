package producer;

import energy.Energy;
import energy.Order;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class RequestOrderBehaviour extends OneShotBehaviour {
    private ProducerAgent agent;

    public RequestOrderBehaviour(ProducerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        agent.doWait(1000);
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage message = agent.receive(mt);
        if (message != null && message.getConversationId().equals("make-order")) {
            try {
                Order order = (Order) message.getContentObject();
                ACLMessage response = message.createReply();
                response.setConversationId("request-order");

                if(order.getEnergy().getQuantity() > 0) {
                    response.setPerformative(ACLMessage.CONFIRM);
                    response.setContent("available");
                } else {
                    response.setPerformative(ACLMessage.DISCONFIRM);
                    response.setContent("full");
                }

                agent.send(response);

                /*
                Energy energy = (Energy) message.getContentObject();
                response.setPerformative(ACLMessage.DISCONFIRM);
                response.setContent("full");
                for(Energy e: agent.getEnergies()) {
                    if(e.equals(energy)) {
                        response.setPerformative(ACLMessage.CONFIRM);
                        response.setContent("available");
                    }
                }*/


            } catch (UnreadableException e) { e.printStackTrace(); }
        }
    }
}
