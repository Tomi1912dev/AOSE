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

public class Scenario1Test {

    @Test
    void scenario1Test() {
        //configuration
        Runtime runtime = Runtime.instance();
        Profile config = new ProfileImpl("localhost", 8890, null);
        config.setParameter("gui", "true");
        //creating of agents
        AgentContainer mc = runtime.createMainContainer(config);
        AgentController systemAgent;
        AgentController producerAgent;
        AgentController consumerAgent;
        //creating energies
        try {
            Energy[] energies = {
                    new Energy(Type.RENEWABLE, 127.92, 1,8, 10),
                    new Energy(Type.CLASSIC, 127.20, 0,14, 18),
                    new Energy(Type.CLASSIC, 127.16, 0, 9, 10),
                    new Energy(Type.RENEWABLE, 139.21, 0, 14, 17)
            };
            //creating preferences
            Preference[] preference = { new Preference(Policy.RENEWABLE,
                    128.0, 10.0,
                    9, 10) };
            //instanciate agents
            systemAgent = mc.createNewAgent("SystemAgent", SystemAgent.class.getName(), null);
            systemAgent.start();
            producerAgent = mc.createNewAgent("ProducerAgent", ProducerAgent.class.getName(), energies);
            producerAgent.start();
            consumerAgent = mc.createNewAgent("ConsumerAgent", ConsumerAgent.class.getName(), preference);
            consumerAgent.start();
            //creating interfacesAgents
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

            //print datas of agents
            System.out.println("affichage des energies de la market place");
            System.out.println(o2a.toString());
            //assertEquals(o2a.toString(),"[[RENEWABLE, 127.92€, 1 Qty, 8h/10h, "+producerAgent.getName()+"][CLASSIC, 127.2€, 0 Qty, 14h/18h, "+producerAgent.getName()+"][CLASSIC, 127.16€, 0 Qty, 9h/10h, "+producerAgent.getName()+"][RENEWABLE, 139.21€, 0 Qty, 14h/17h, "+producerAgent.getName()+"]]");
            //The market place contained all the energies.
            System.out.println("affichage des energies du producteur");
            System.out.println(o2a1.toString());
            assertEquals(o2a1.toString(),"[[RENEWABLE, 127.92€, 0 Qty, 8h/10h, "+producerAgent.getName()+"][CLASSIC, 127.2€, 0 Qty, 14h/18h, "+producerAgent.getName()+"][CLASSIC, 127.16€, 0 Qty, 9h/10h, "+producerAgent.getName()+"][RENEWABLE, 139.21€, 0 Qty, 14h/17h, "+producerAgent.getName()+"]]");

            System.out.println("affichage des preferences du consomateur");
            System.out.println(o2o2.toStringPreferences());
            //Thread.sleep(30000);

            System.out.println("affichage du statut de la commande du consomateur");
            System.out.println(o2o2.getOrder().getStatus().toString());
            //assertEquals(o2o2.getOrder().getStatus().toString(),"PAID");



        } catch (StaleProxyException | InterruptedException e) { e.printStackTrace(); }
    }


}
