package producer;

import energy.Energy;
import energy.Order;
import energy.Status;
import interfaces.SystemAgentManager;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

public class ProducerAgent extends Agent implements SystemAgentManager {
    private static final String BEHAVIOUR_REGISTER = "register";
    private static final String BEHAVIOUR_PUBLISH = "publish";
    private static final String BEHAVIOUR_REQUEST_ORDER = "requestOrder";
    private static final String BEHAVIOUR_CONFIRM_ORDER = "confirmOrder";
    private static final String BEHAVIOUR_LAST = "last";
    public static AID SYSTEM = new AID("SystemAgent", AID.ISLOCALNAME);
    private Energy[] energies;

    protected void setup() {
        this.energies = (Energy[]) getArguments();
        registerO2AInterface(SystemAgentManager.class,this);
        if (energies != null && energies.length > 0) {
            for(Energy energy: energies) { energy.setProducer(this.getAID()); }

            FSMBehaviour behaviour = new FSMBehaviour(this);

            // States
            behaviour.registerFirstState(new RegisterBehaviour(this), BEHAVIOUR_REGISTER);
            behaviour.registerState(new PublishBehaviour(this), BEHAVIOUR_PUBLISH);
            behaviour.registerState(new RequestOrderBehaviour(this), BEHAVIOUR_REQUEST_ORDER);
            behaviour.registerState(new ConfirmOrderBehaviour(this), BEHAVIOUR_CONFIRM_ORDER);
            behaviour.registerLastState(new LastBehaviour(this), BEHAVIOUR_LAST);

            // Transitions
            behaviour.registerDefaultTransition(BEHAVIOUR_REGISTER, BEHAVIOUR_PUBLISH);
            behaviour.registerDefaultTransition(BEHAVIOUR_PUBLISH, BEHAVIOUR_REQUEST_ORDER);
            behaviour.registerDefaultTransition(BEHAVIOUR_REQUEST_ORDER, BEHAVIOUR_CONFIRM_ORDER);
            behaviour.registerDefaultTransition(BEHAVIOUR_CONFIRM_ORDER, BEHAVIOUR_LAST);

            addBehaviour(behaviour);
        }
    }

    @Override
    protected void takeDown() {
        //to do
    }

    public Energy[] getEnergies() {
        return energies;
    }

    public void register() {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(SYSTEM);
        message.setConversationId("producer-register");
        try { message.setContentObject(this.getAID()); }
        catch (IOException e) { e.printStackTrace(); }
        this.send(message);
    }

    public void submitEnergyPrice() {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST_WHEN);
        message.addReceiver(SYSTEM);
        message.setConversationId("producer-publish");
        try { message.setContentObject(this.energies); }
        catch (IOException e) { e.printStackTrace(); }
        this.send(message);
    }

    public void confirmOrder() {
        MessageTemplate mt = MessageTemplate.MatchConversationId("pay-order");
        ACLMessage message = this.receive(mt);
        if (message != null && message.getPerformative() == ACLMessage.REQUEST) {
            try {
                Order order = (Order) message.getContentObject();
                ACLMessage response = message.createReply();
                response.setConversationId("confirm-order");
                response.setPerformative(ACLMessage.DISCONFIRM);
                response.setContent("full");

                for(Energy energy : this.getEnergies()) {
                    if(energy.equals(order.getEnergy()) && energy.getQuantity() > 0) {
                        energy.setQuantity(energy.getQuantity() - 1);
                        order.setStatus(Status.PAID);
                        response.setPerformative(ACLMessage.CONFIRM);
                        try { response.setContentObject(order); }
                        catch (IOException e) { e.printStackTrace(); }
                        break;
                    }
                }
                this.send(response);
            } catch (UnreadableException e) { e.printStackTrace(); }
        }
    }

    @Override
    public String toString(){
        System.out.println("print of the producer's energies");
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Energy engery :energies){
            sb.append(engery.toString());
        }
        sb.append("]");
        return sb.toString();
    }

}
