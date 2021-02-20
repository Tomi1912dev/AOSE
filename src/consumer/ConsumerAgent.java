package consumer;

import energy.Energy;
import energy.Order;
import interfaces.ConsumerManager;
import interfaces.SystemAgentManager;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

public class ConsumerAgent extends Agent implements ConsumerManager {
    private static final String BEHAVIOUR_REGISTER = "register";
    private static final String BEHAVIOUR_CHOOSE_PRODUCER = "chooseProducer";
    private static final String BEHAVIOUR_MAKE_ORDER = "makeOrder";
    private static final String BEHAVIOUR_PAY_ORDER = "payOrder";
    private static final String BEHAVIOUR_GET_ORDER = "getOrder";
    private static final String BEHAVIOUR_LAST = "last";

    private Preference[] preference;
    private Order order;

    private boolean backState;
    private int energySelected;
    private Energy[] energyProposed;

    public static AID SYSTEM = new AID("SystemAgent", AID.ISLOCALNAME);

    protected void setup() {
        this.backState = false;
        this.energySelected = 0;

        this.preference = (Preference[]) getArguments();
        registerO2AInterface(ConsumerManager.class,this);
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
            behaviour.registerTransition(BEHAVIOUR_MAKE_ORDER, BEHAVIOUR_PAY_ORDER, 0);
            behaviour.registerTransition(BEHAVIOUR_MAKE_ORDER, BEHAVIOUR_CHOOSE_PRODUCER, 1);
            behaviour.registerTransition(BEHAVIOUR_PAY_ORDER, BEHAVIOUR_GET_ORDER, 0);
            behaviour.registerTransition(BEHAVIOUR_PAY_ORDER, BEHAVIOUR_MAKE_ORDER, 1);
            behaviour.registerDefaultTransition(BEHAVIOUR_GET_ORDER, BEHAVIOUR_LAST);



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

    public void makeOrder() {
        if(energySelected < energyProposed.length) {
            //System.out.println(energyProposed[energySelected]);
            this.order = new Order(this.getAID(), energyProposed[energySelected]);
            ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
            message.addReceiver(this.order.getEnergy().getProducer());
            message.setConversationId("make-order");
            try { message.setContentObject(this.order); }
            catch (IOException e) { e.printStackTrace(); }
            this.send(message);
            this.energySelected += 1;
        }
    }

    public void payOrder() {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(this.order.getEnergy().getProducer());
        message.setConversationId("pay-order");
        try { message.setContentObject(this.order); }
        catch (IOException e) { e.printStackTrace(); }
        this.send(message);
    }

    public boolean getBackState() {
        return backState;
    }

    public void setBackState(boolean backState) {
        this.backState = backState;
    }

    public int getEnergySelected() {
        return energySelected;
    }

    public Energy[] getEnergyProposed() {
        return energyProposed;
    }

    public void setEnergyProposed(Energy[] energyProposed) {
        this.energyProposed = energyProposed;
    }

    public Preference getPreference() {
        return preference[0];
    }

    @Override
    public Order getOrder(){
        return this.order;
    }

    @Override
    public String toStringPreferences(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Preference p : preference){
            sb.append(p.toString());
        }
        sb.append("]");
        return sb.toString();
    }
}
