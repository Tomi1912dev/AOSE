package consumer;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;

public class ConsumerAgent extends Agent {
    private static final String BEHAVIOUR_REGISTER = "register";
    private static final String BEHAVIOUR_CHOOSE_PRODUCER = "chooseProducer";
    private static final String BEHAVIOUR_MAKE_ORDER = "makeOrder";
    private static final String BEHAVIOUR_PAY_ORDER = "payOrder";
    private static final String BEHAVIOUR_GET_ORDER = "getOrder";
    private static final String BEHAVIOUR_LAST = "last";

    public static AID SYSTEM = new AID("SystemAgent", AID.ISLOCALNAME);

    protected void setup() {
        FSMBehaviour behaviour = new FSMBehaviour(this);

        // Etats
        behaviour.registerFirstState(new RegisterBehaviour(this), BEHAVIOUR_REGISTER);
        behaviour.registerState(new ChooseProducerBehaviour(this), BEHAVIOUR_CHOOSE_PRODUCER);
        behaviour.registerState(new MakeOrderBehaviour(this), BEHAVIOUR_MAKE_ORDER);
        behaviour.registerState(new PayOrderBehaviour(this), BEHAVIOUR_PAY_ORDER);
        behaviour.registerState(new GetOrderBehaviour(this), BEHAVIOUR_GET_ORDER);
        behaviour.registerLastState(new LastBehaviour(this), BEHAVIOUR_LAST);

        // Transitions
        behaviour.registerDefaultTransition(BEHAVIOUR_REGISTER, BEHAVIOUR_LAST);
        behaviour.registerTransition(BEHAVIOUR_MAKE_ORDER, BEHAVIOUR_PAY_ORDER, 0);

        addBehaviour(behaviour);
    }

    @Override
    protected void takeDown() {
        //to do
    }

    public void register() {
        //to do

    }

    public void consultMarketplace() {
        //to do
    }

    public void makeOrder() {
        //to do
    }

}
