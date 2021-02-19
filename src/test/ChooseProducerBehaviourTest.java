package test;

import consumer.ConsumerAgent;
import consumer.Policy;
import consumer.Preference;
import energy.Energy;
import energy.Type;
import interfaces.SystemAgentManager;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.event.AgentListener;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import jade.wrapper.State;
import marketplace.SystemAgent;
import org.junit.jupiter.api.Test;
import producer.ProducerAgent;

import static org.junit.jupiter.api.Assertions.*;

class ChooseProducerBehaviourTest {

    @Test
    void chooseProducerBehaviourTest() {
        Runtime runtime = Runtime.instance();
        Profile config = new ProfileImpl("localhost", 8889, null);
        config.setParameter("gui", "true");
        AgentContainer mc = runtime.createMainContainer(config);
        AgentController systemAgent;
        AgentController producerAgent;
        AgentController consumerAgent;
        try {
            Energy[] energies = {
                    new Energy(0, Type.RENEWABLE, 127.92, 8, 10),
                    new Energy(1, Type.CLASSIC, 127.20, 14, 18),
                    new Energy(2, Type.CLASSIC, 127.16, 9, 10),
                    new Energy(3, Type.RENEWABLE, 139.21, 14, 17)
            };
            Preference[] preference = { new Preference(Policy.RENEWABLE,
                    128.0, 10.0,
                    9, 10) };

            systemAgent = mc.createNewAgent("SystemAgent", SystemAgent.class.getName(), null);
            systemAgent.start();
            producerAgent = mc.createNewAgent("ProducerAgent", ProducerAgent.class.getName(), energies);
            producerAgent.start();
            consumerAgent = mc.createNewAgent("ConsumerAgent", ConsumerAgent.class.getName(), preference);
            consumerAgent.start();

            SystemAgentManager o2a = null;
            SystemAgentManager o2a1 = null;


            try{
                o2a = systemAgent.getO2AInterface(SystemAgentManager.class);
                o2a1 = producerAgent.getO2AInterface(SystemAgentManager.class);
            }catch(StaleProxyException e){e.printStackTrace();}
            assert(o2a!=null);
            assert(o2a1!=null);
            Thread.sleep(30000);
            assertEquals(o2a.toString(),"[[0, RENEWABLE, 127.92, 8h/10h, "+producerAgent.getName()+"][1, CLASSIC, 127.2, 14h/18h, ProducerAgent@172.17.0.1:8889/JADE][2, CLASSIC, 127.16, 9h/10h, ProducerAgent@172.17.0.1:8889/JADE][3, RENEWABLE, 139.21, 14h/17h, ProducerAgent@172.17.0.1:8889/JADE]]");
            //assertEquals(o2a1.toString(),);

            //State s = consumerAgent.getState();
            //System.out.println(s.toString());
           // assertEquals(s.toString(),"Initiated(1)");

        } catch (StaleProxyException | InterruptedException e) { e.printStackTrace(); }
    }
}
