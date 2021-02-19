package consumer;

import energy.Order;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class GetOrderBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;

    public GetOrderBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        agent.doWait(1000);
        MessageTemplate mt = MessageTemplate.MatchConversationId("confirm-order");
        ACLMessage message = agent.receive(mt);
        if (message != null && message.getPerformative() == ACLMessage.CONFIRM) {
            try {
                Order order = (Order) message.getContentObject();
                System.out.println(order.getEnergy().getPrice() + "€ - " + order.getStatus());
            } catch (UnreadableException e) { e.printStackTrace(); }
        }
    }
}
