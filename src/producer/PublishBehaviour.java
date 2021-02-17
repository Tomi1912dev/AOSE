package producer;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class PublishBehaviour extends OneShotBehaviour {
    private ProducerAgent agent;

    public PublishBehaviour(ProducerAgent agent) {
        this.agent = agent;
    }

    @Override
    public void action() {
        agent.doWait(1000);
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(agent.SYSTEM);
        message.setConversationId("producer-publish");
        try { message.setContentObject(agent.submitEnergyPrice()); }
        catch (IOException e) { e.printStackTrace(); }
        agent.send(message);
    }
}
