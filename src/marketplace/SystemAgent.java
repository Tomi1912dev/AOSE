package marketplace;

import energy.Energy;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.ArrayList;
import java.util.List;

public class SystemAgent extends Agent {
    private MarketPlace marketPlace;
    private static final int RATE_REGISTER = 100;
    private List<AID> consumers;
    private List<AID> producers;

    protected void setup() {
        marketPlace = new MarketPlace();
        consumers = new ArrayList<>();
        producers = new ArrayList<>();

        ParallelBehaviour behaviour = new ParallelBehaviour(ParallelBehaviour.WHEN_ANY);

        //Register
        behaviour.addSubBehaviour(new TickerBehaviour(this, RATE_REGISTER) {
            SystemAgent agent = SystemAgent.this;
            protected void onTick() {
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
                ACLMessage message = agent.receive(mt);
                if(message != null) {
                    if(message.getConversationId().equals("consumer-register")) {
                        try { agent.consumers.add((AID) message.getContentObject()); }
                        catch (UnreadableException e) { e.printStackTrace(); }
                    } else if(message.getConversationId().equals("producer-register")) {
                        try { agent.producers.add((AID) message.getContentObject()); }
                        catch (UnreadableException e) { e.printStackTrace(); }
                    } else if(message.getConversationId().equals("producer-publish")) {
                        try { agent.marketPlace.addAll((Energy[]) message.getContentObject()); }
                        catch (UnreadableException e) { e.printStackTrace(); }
                        System.out.println(marketPlace);
                    } else {
                        System.err.println("Bad conversation id");
                    }
                }
            }
        });

        //Producer
        /*behaviour.addSubBehaviour(new TickerBehaviour(this,10000) {
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
        });*/

        addBehaviour(behaviour);
    }

    @Override
    protected void takeDown() {
        //to do
    }
}
