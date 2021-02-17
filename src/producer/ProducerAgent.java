package producer;

import energy.Energy;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;

import java.util.List;

public class ProducerAgent extends Agent {
    private static final String BEHAVIOUR_REGISTER = "register";
    private static final String BEHAVIOUR_PUBLISH = "publish";
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
            behaviour.registerLastState(new LastBehaviour(this), BEHAVIOUR_LAST);

            // Transitions
            behaviour.registerDefaultTransition(BEHAVIOUR_REGISTER, BEHAVIOUR_PUBLISH);
            behaviour.registerDefaultTransition(BEHAVIOUR_PUBLISH, BEHAVIOUR_LAST);

            addBehaviour(behaviour);
        }
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

    public Energy[] submitEnergyPrice() {
        return this.energies;
    }

    public void confirmOrder() {
        //to do
    }

}
