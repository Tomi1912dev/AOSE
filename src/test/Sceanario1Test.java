package test;

import consumer.ConsumerAgent;
import consumer.Policy;
import consumer.Preference;
import energy.Energy;
import energy.Type;
import interfaces.ConsumerManager;
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

class Sceanario1Test {

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
                    new Energy(Type.RENEWABLE, 127.92, 5,8, 10),
                    new Energy(Type.CLASSIC, 127.20, 5,14, 18),
                    new Energy(Type.CLASSIC, 127.16, 5, 9, 10),
                    new Energy(Type.RENEWABLE, 139.21, 5, 14, 17)
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
            ConsumerManager o2o2 = null;


            try{
                o2a = systemAgent.getO2AInterface(SystemAgentManager.class);
                o2a1 = producerAgent.getO2AInterface(SystemAgentManager.class);
                o2o2 = consumerAgent.getO2AInterface(ConsumerManager.class);
            }catch(StaleProxyException e){e.printStackTrace();}
            assert(o2a!=null);
            assert(o2a1!=null);
            assert(o2o2!=null);
            Thread.sleep(30000);
            System.out.println(o2a.toString());
            System.out.println(o2a1.toString());
            System.out.println(o2o2.getOrder().getStatus().toString());




        } catch (StaleProxyException | InterruptedException e) { e.printStackTrace(); }
    }
}
