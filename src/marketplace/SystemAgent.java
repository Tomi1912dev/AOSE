package marketplace;

import consumer.Policy;
import consumer.Preference;
import energy.Energy;
import energy.Type;
import interfaces.SystemAgentManager;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SystemAgent extends Agent implements SystemAgentManager {
    private List<Energy> marketPlace;
    private static final int RATE_REGISTER = 100;
    private List<AID> consumers;
    private List<AID> producers;

    protected void setup() {
        marketPlace = new ArrayList<>();
        consumers = new ArrayList<>();
        producers = new ArrayList<>();
        registerO2AInterface(SystemAgentManager.class,this);
        ParallelBehaviour behaviour = new ParallelBehaviour(ParallelBehaviour.WHEN_ALL);

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
                    } else if(message.getConversationId().equals("consumer-choose")) {
                        try {
                            ACLMessage response = message.createReply();
                            Preference preference = (Preference) message.getContentObject();
                            List<Energy> energies = agent.marketPlace.stream()
                                    .filter(energy -> energy.getPrice() <= preference.getBudget()
                                            && energy.getLowerBoundHour() <= preference.getLowerBoundHour()
                                            && energy.getUpperBoundHour() >= preference.getUpperBoundHour())
                                    .sorted()
                                    .collect(Collectors.toList());
                            if(preference.getPolicy().equals(Policy.RENEWABLE)) {
                                List<Energy> energiesRenewable = energies.stream()
                                        .filter(energy -> energy.getType().equals(Type.RENEWABLE))
                                        .collect(Collectors.toList());
                                try {
                                    response.setPerformative(ACLMessage.PROPOSE);
                                    response.setContentObject(energiesRenewable.toArray(new Energy[0]));
                                } catch (IOException e) { e.printStackTrace(); }

                            } else {
                                try {
                                    response.setPerformative(ACLMessage.PROPOSE);
                                    response.setContentObject(energies.toArray(new Energy[0]));
                                } catch (IOException e) { e.printStackTrace(); }
                            }
                            agent.send(response);
                        }
                        catch (UnreadableException e) { e.printStackTrace(); }
                    } else {
                        System.err.println("Bad conversation id :" + message.getConversationId());
                    }
                }
            }
        });

        behaviour.addSubBehaviour(new TickerBehaviour(this, RATE_REGISTER) {
            SystemAgent agent = SystemAgent.this;
            public void onStart() {
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST_WHEN);
                ACLMessage message = agent.receive(mt);
                if (message != null) {
                    if(message.getConversationId().equals("producer-publish")) {
                        try {
                            Energy[] energies = (Energy[]) message.getContentObject();
                            agent.marketPlace.addAll(Arrays.asList(energies));
                        } catch (UnreadableException e) { e.printStackTrace(); }
                    }
                }
            }
            protected void onTick() {
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST_WHEN);
                ACLMessage message = agent.receive(mt);
                if (message != null) {
                    if(message.getConversationId().equals("producer-publish")) {
                        try {
                            Energy[] energies = (Energy[]) message.getContentObject();
                            agent.marketPlace.addAll(Arrays.asList(energies));
                        } catch (UnreadableException e) { e.printStackTrace(); }
                    }
                }
            }
        });

        addBehaviour(behaviour);
    }


    @Override
    public String toString(){
        System.out.println("print of the market place's energies");
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i<marketPlace.size();i++)
            sb.append( marketPlace.get(i).toString());
        sb.append("]");
        System.out.println(sb.toString());
        return sb.toString();
    }

    @Override
    protected void takeDown() {
        //to do
    }
}
