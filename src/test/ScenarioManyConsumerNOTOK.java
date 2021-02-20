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


public class ScenarioManyConsumerNOTOK {
    @Test
    void scenarioManyConsumerNOTOK() {
        //configuration
        Runtime runtime = Runtime.instance();
        Profile config = new ProfileImpl("localhost", 8887, null);
        config.setParameter("gui", "true");
        //creating of agents
        AgentContainer mc = runtime.createMainContainer(config);
        AgentController systemAgent;
        AgentController producerAgent;
        AgentController consumerAgent1;
        AgentController consumerAgent2;
        //creating energies
        try {
            Energy[] energies = {
                    new Energy(Type.RENEWABLE, 127.92, 5,8, 10),
            };
            //creating preferences
            Preference[] preference1 = { new Preference(Policy.RENEWABLE, 128.0, 10.0, 9, 10) };
            Preference[] preference2 = { new Preference(Policy.PRICE, 130.0, 10.0, 14, 18) };
            //instanciate agents
            systemAgent = mc.createNewAgent("SystemAgent", SystemAgent.class.getName(), null);
            systemAgent.start();
            producerAgent = mc.createNewAgent("ProducerAgent", ProducerAgent.class.getName(), energies);
            producerAgent.start();
            consumerAgent1 = mc.createNewAgent("ConsumerAgent1", ConsumerAgent.class.getName(), preference1);
            consumerAgent1.start();
            consumerAgent2 = mc.createNewAgent("ConsumerAgent2", ConsumerAgent.class.getName(), preference2);
            consumerAgent2.start();
            //creating interfacesAgents
            SystemAgentManager o2a = null;
            SystemAgentManager o2a1 = null;
            ConsumerManager o2o2 = null;
            ConsumerManager o2a21 = null;

            try{
                o2a = systemAgent.getO2AInterface(SystemAgentManager.class);
                o2a1 = producerAgent.getO2AInterface(SystemAgentManager.class);
                o2o2 = consumerAgent1.getO2AInterface(ConsumerManager.class);
                o2a21 = consumerAgent2.getO2AInterface(ConsumerManager.class);
            }catch(StaleProxyException e){e.printStackTrace();}

            assert(o2a!=null);
            assert(o2a1!=null);
            assert(o2o2!=null);
            assert(o2a21!=null);
            Thread.sleep(30000);

            //print datas of agents
            System.out.println("affichage des energies de la market place");
            System.out.println(o2a.toString());
            assertEquals(o2a.toString(),"[[RENEWABLE, 127.92€, 5 Qty, 8h/10h, "+producerAgent.getName()+"]]");
            //The market place contained all the energies.
            System.out.println("affichage des energies du producteur");
            System.out.println(o2a1.toString());
            assertEquals(o2a1.toString(),"[[RENEWABLE, 127.92€, 5 Qty, 8h/10h, "+producerAgent.getName()+"]]");

            System.out.println("affichage des preferences du consomateur1");
            System.out.println(o2o2.toStringPreferences());
            System.out.println("Affichage des preferences du consomateur 2");
            System.out.println(o2a21.toStringPreferences());
            Thread.sleep(30000);

            System.out.println("affichage du statut de la commande du consomateur1");
            System.out.println(o2o2.getOrder().getStatus().toString());
            //assertEquals(o2o2.getOrder().getStatus().toString(),"PAID");
            //System.out.println("affichage du statut de la commande du consomateur2");
            //System.out.println(o2a21.getOrder().getStatus().toString());
            //assertEquals(o2o21.getOrder().getStatus().toString(),"UNPAID");
            //assertNull(o2a21.getOrder().getStatus());




        } catch (StaleProxyException | InterruptedException e) { e.printStackTrace(); }
    }
}
