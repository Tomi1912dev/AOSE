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

class SceanarioManyConsumer {

    @Test
    void scenarioManyConsumer() {
        //configuration
        Runtime runtime = Runtime.instance();
        Profile config = new ProfileImpl("localhost", 8889, null);
        config.setParameter("gui", "true");
        //creating of agents
        AgentContainer mc = runtime.createMainContainer(config);
        AgentController systemAgent;
        AgentController producerAgent1;
        AgentController producerAgent2;
        AgentController consumerAgent;
        //creating energies
        try {

            Energy energy1 = new Energy(Type.RENEWABLE, 127.92, 5,8, 10);
            Energy energy2 = new Energy(Type.CLASSIC, 127.20, 5,14, 18);
            Energy energy3 = new Energy(Type.CLASSIC, 127.16, 5, 9, 10);
            //creating preferences
            Preference[] preference = { new Preference(Policy.RENEWABLE,
                    128.0, 10.0,
                    9, 10) };
            //instanciate agents
            systemAgent = mc.createNewAgent("SystemAgent", SystemAgent.class.getName(), null);
            systemAgent.start();
            producerAgent1 = mc.createNewAgent("ProducerAgent1", ProducerAgent.class.getName(), new Energy[]{energy2,energy3});
            producerAgent1.start();
            producerAgent2 = mc.createNewAgent("ProducerAgent2", ProducerAgent.class.getName(), new Energy[]{energy1});
            producerAgent2.start();
            consumerAgent = mc.createNewAgent("ConsumerAgent", ConsumerAgent.class.getName(), preference);
            consumerAgent.start();
            //creating interfacesAgents
            SystemAgentManager o2a = null;
            SystemAgentManager o2a1 = null;
            SystemAgentManager o2a12 = null;
            ConsumerManager o2o2 = null;

            try{
                o2a = systemAgent.getO2AInterface(SystemAgentManager.class);
                o2a1 = producerAgent1.getO2AInterface(SystemAgentManager.class);
                o2a12 = producerAgent2.getO2AInterface(SystemAgentManager.class);
                o2o2 = consumerAgent.getO2AInterface(ConsumerManager.class);
            }catch(StaleProxyException e){e.printStackTrace();}

            assert(o2a!=null);
            assert(o2a1!=null);
            assert(o2a12!=null);
            assert(o2o2!=null);
            Thread.sleep(30000);
            //print datas of agents
            System.out.println("affichage des energies de la market place");
            System.out.println(o2a.toString());
            System.out.println("affichage des energies du producteur1");
            System.out.println(o2a1.toString());
            System.out.println("affichage des energies du producteur2");
            System.out.println(o2a12.toString());
            System.out.println("affichage des preferences du consomateur");
            System.out.println(o2o2.toStringPreferences());
            Thread.sleep(30000);
            System.out.println("affichage du statut de la commande du consomateur");
            System.out.println(o2o2.getOrder().getStatus().toString());

        } catch (StaleProxyException | InterruptedException e) { e.printStackTrace(); }
    }
}
