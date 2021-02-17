package producer;

import producer.ProducerAgent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class RegisterBehaviour extends OneShotBehaviour {
    private ProducerAgent agent;

    public RegisterBehaviour(ProducerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        agent.doWait(1000);
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(agent.SYSTEM);
        message.setConversationId("producer-register");
        try { message.setContentObject(agent.getAID()); }
        catch (IOException e) { e.printStackTrace(); }
        agent.send(message);
    }
}
