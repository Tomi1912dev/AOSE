import consumer.ConsumerAgent;
import consumer.Policy;
import consumer.Preference;
import energy.Energy;
import energy.Type;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import marketplace.SystemAgent;
import producer.ProducerAgent;


public class Launcher {
    public static void main(String[] args) {
        Runtime runtime = Runtime.instance();
        Profile config = new ProfileImpl("localhost", 8888, null);
        config.setParameter("gui", "true");
        AgentContainer mc = runtime.createMainContainer(config);
        AgentController systemAgent;
        AgentController producerAgent1;
        AgentController producerAgent2;
        AgentController producerAgent3;
        AgentController consumerAgent1;
        AgentController consumerAgent2;
        try {
            Energy[] energies = {
                    new Energy(Type.RENEWABLE, 127.92, 4,8, 10),
                    new Energy(Type.CLASSIC, 127.20, 1,14, 18),
                    new Energy(Type.CLASSIC, 127.16, 1, 9, 10),
                    new Energy(Type.RENEWABLE, 139.21, 1, 7, 12)
            };
            Preference[] preference = { new Preference(Policy.RENEWABLE,
                    128.0, 140.0,
                    9, 10) };

            systemAgent = mc.createNewAgent("SystemAgent", SystemAgent.class.getName(), null);
            systemAgent.start();
            producerAgent1 = mc.createNewAgent("ProducerAgent1", ProducerAgent.class.getName(), energies);
            producerAgent1.start();
            consumerAgent1 = mc.createNewAgent("ConsumerAgent1", ConsumerAgent.class.getName(), preference);
            consumerAgent1.start();
            producerAgent2 = mc.createNewAgent("ProducerAgent2", ProducerAgent.class.getName(), energies);
            producerAgent2.start();
            consumerAgent2 = mc.createNewAgent("ConsumerAgent2", ConsumerAgent.class.getName(), preference);
            consumerAgent2.start();
            producerAgent3 = mc.createNewAgent("ProducerAgent3", ProducerAgent.class.getName(), energies);
            producerAgent3.start();

        } catch (StaleProxyException e) { e.printStackTrace(); }
    }
}
