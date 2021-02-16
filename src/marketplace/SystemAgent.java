package marketplace;

import energy.Energy;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.List;

public class SystemAgent extends Agent {
    private MarketPlace marketPlace;

    protected void setup() {
        marketPlace = new MarketPlace();
        ParallelBehaviour behaviour = new ParallelBehaviour(ParallelBehaviour.WHEN_ANY);

        //Consumer
        behaviour.addSubBehaviour(new TickerBehaviour(this, 1000) {
            SystemAgent agent = SystemAgent.this;
            protected void onTick() {
                System.out.println(agent.getLocalName());
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
                ACLMessage message = agent.receive(mt);
                if (message != null) {
                    try { agent.marketPlace.addAll((List<Energy>) message.getContentObject()); }
                    catch ( UnreadableException e) { e.printStackTrace(); }
                }
            }
        });

        //Producer
        behaviour.addSubBehaviour(new TickerBehaviour(this,10000) {
            SystemAgent agent = SystemAgent.this;
            protected void onTick() {
                System.out.println(agent.getLocalName());
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
                ACLMessage message = agent.receive(mt);
                if (message != null) {
                    try { agent.marketPlace.addAll((List<Energy>) message.getContentObject()); }
                    catch ( UnreadableException e) { e.printStackTrace(); }
                }
            }
        });

        addBehaviour(behaviour);
    }

    @Override
    protected void takeDown() {
        //to do
    }
}
