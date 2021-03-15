package consumer;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * If the energy in request is available then the consumer pay order.
 * But if the energy is not available then he try to send a request with another energy.
 *
 * @author Tomi Cottrelle
 * @version 1.0.0
 * @see ConsumerAgent#payOrder()
 */
public class PayOrderBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;
    private int transition;

    public PayOrderBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        this.transition = 1;
        MessageTemplate mt = MessageTemplate.MatchConversationId("request-order");
        ACLMessage message = agent.receive(mt);
        if (message != null) {
            if (message.getPerformative() == ACLMessage.CONFIRM) {
                agent.payOrder();
                this.transition = 0;
            } else {
                //when producer is full
                agent.setBackState(true);
            }
        }
    }

    public int onEnd() {
        return transition;
    }
}
