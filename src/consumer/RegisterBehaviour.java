package consumer;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

public class RegisterBehaviour extends OneShotBehaviour {
    private ConsumerAgent agent;

    public RegisterBehaviour(ConsumerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        agent.doWait(1000);
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(agent.SYSTEM);
        message.setConversationId("consumer-register");
        //message.setReplyWith("quote"+System.currentTimeMillis());
        try { message.setContentObject(agent.getAID()); }
        catch (IOException e) { e.printStackTrace(); }
        agent.send(message);
        //agent.setMessageTemplate(MessageTemplate.and(MessageTemplate.MatchConversationId("apple-store"), MessageTemplate.MatchInReplyTo(message.getReplyWith())));
        //agent.getOrder().next();
    }
}
