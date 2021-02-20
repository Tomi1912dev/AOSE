package consumer;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

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
