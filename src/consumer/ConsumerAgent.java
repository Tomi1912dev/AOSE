package consumer;

import energy.Energy;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class ConsumerAgent extends Agent {
    private static final String BEHAVIOUR_REGISTER = "register";
    private static final String BEHAVIOUR_CHOOSE_PRODUCER = "chooseProducer";
    private static final String BEHAVIOUR_MAKE_ORDER = "makeOrder";
    private static final String BEHAVIOUR_PAY_ORDER = "payOrder";
    private static final String BEHAVIOUR_GET_ORDER = "getOrder";
    private static final String BEHAVIOUR_LAST = "last";

    private Preference[] preference;

    public static AID SYSTEM = new AID("SystemAgent", AID.ISLOCALNAME);

    protected void setup() {
        this.preference = (Preference[]) getArguments();
        if (preference != null && preference.length > 0) {
            FSMBehaviour behaviour = new FSMBehaviour(this);

            // States
            behaviour.registerFirstState(new RegisterBehaviour(this), BEHAVIOUR_REGISTER);
            behaviour.registerState(new ChooseProducerBehaviour(this), BEHAVIOUR_CHOOSE_PRODUCER);
            behaviour.registerState(new MakeOrderBehaviour(this), BEHAVIOUR_MAKE_ORDER);
            behaviour.registerState(new PayOrderBehaviour(this), BEHAVIOUR_PAY_ORDER);
            behaviour.registerState(new GetOrderBehaviour(this), BEHAVIOUR_GET_ORDER);
            behaviour.registerLastState(new LastBehaviour(this), BEHAVIOUR_LAST);

            // Transitions
            behaviour.registerDefaultTransition(BEHAVIOUR_REGISTER, BEHAVIOUR_CHOOSE_PRODUCER);
            behaviour.registerDefaultTransition(BEHAVIOUR_CHOOSE_PRODUCER, BEHAVIOUR_MAKE_ORDER);
            behaviour.registerDefaultTransition(BEHAVIOUR_MAKE_ORDER, BEHAVIOUR_LAST);
            //behaviour.registerTransition(BEHAVIOUR_MAKE_ORDER, BEHAVIOUR_PAY_ORDER, 0);


            addBehaviour(behaviour);
        }
    }

    @Override
    protected void takeDown() {
        //to do
    }

    public void register() {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(SYSTEM);
        message.setConversationId("consumer-register");
        try { message.setContentObject(this.getAID()); }
        catch (IOException e) { e.printStackTrace(); }
        this.send(message);
    }

    public void choose() {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(SYSTEM);
        message.setConversationId("consumer-choose");
        try { message.setContentObject(this.preference[0]); }
        catch (IOException e) { e.printStackTrace(); }
        this.send(message);
    }

    public void makeOrder(Energy energy) {
        //to do
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(energy.getProducer());
        message.setConversationId("make-order");
        try { message.setContentObject(energy); }
        catch (IOException e) { e.printStackTrace(); }
        this.send(message);
    }

}
