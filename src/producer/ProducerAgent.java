package producer;

import energy.Energy;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.List;

public class ProducerAgent<p> extends Agent {
    private static final String BEHAVIOUR_REGISTER = "register";
    private static final String BEHAVIOUR_PUBLISH = "publish";
    private static final String BEHAVIOUR_REQUEST_ORDER = "requestOrder";
    private static final String BEHAVIOUR_LAST = "last";
    public static AID SYSTEM = new AID("SystemAgent", AID.ISLOCALNAME);
    private Energy[] energies;

    protected void setup() {
        this.energies = (Energy[]) getArguments();
        if (energies != null && energies.length > 0) {
            FSMBehaviour behaviour = new FSMBehaviour(this);

            // States
            behaviour.registerFirstState(new RegisterBehaviour(this), BEHAVIOUR_REGISTER);
            behaviour.registerState(new PublishBehaviour(this), BEHAVIOUR_PUBLISH);
            behaviour.registerState(new RequestOrderBehaviour(this), BEHAVIOUR_REQUEST_ORDER);
            behaviour.registerLastState(new LastBehaviour(this), BEHAVIOUR_LAST);

            // Transitions
            behaviour.registerDefaultTransition(BEHAVIOUR_REGISTER, BEHAVIOUR_PUBLISH);
            behaviour.registerDefaultTransition(BEHAVIOUR_PUBLISH, BEHAVIOUR_REQUEST_ORDER);
            behaviour.registerDefaultTransition(BEHAVIOUR_REQUEST_ORDER, BEHAVIOUR_LAST);

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

    public void consultMarketplace() {
        //to do
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
        //to do
    }

}
