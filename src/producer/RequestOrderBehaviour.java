package producer;

import energy.Energy;
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
        System.out.println("ok1");
        if (message != null) {
            System.out.println("ok2");
            try {
                Energy energy = (Energy) message.getContentObject();
                ACLMessage response = message.createReply();
                response.setPerformative(ACLMessage.REFUSE);
                response.setContent("not available");
                for(Energy e: agent.getEnergies()) {
                    if(e.equals(energy)) { // equals à définir
                        response.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                        response.setContent("ok");
                        //System.out.println("Available >>> " + e);
                    }
                }
                agent.send(response);
            } catch (UnreadableException e) { e.printStackTrace(); }
        }
    }
}
