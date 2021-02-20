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
        AgentController producerAgent;
        AgentController consumerAgent;
        try {
            Energy[] energies = {
                    new Energy(Type.RENEWABLE, 127.92, 1,8, 10),
                    new Energy(Type.CLASSIC, 127.20, 0,14, 18),
                    new Energy(Type.CLASSIC, 127.16, 0, 9, 10),
                    new Energy(Type.RENEWABLE, 139.21, 0, 7, 12)
            };
            Preference[] preference = { new Preference(Policy.RENEWABLE,
                    128.0, 140.0,
                    9, 10) };

            systemAgent = mc.createNewAgent("SystemAgent", SystemAgent.class.getName(), null);
            systemAgent.start();
            producerAgent = mc.createNewAgent("ProducerAgent", ProducerAgent.class.getName(), energies);
            producerAgent.start();
            consumerAgent = mc.createNewAgent("ConsumerAgent", ConsumerAgent.class.getName(), preference);
            consumerAgent.start();
        } catch (StaleProxyException e) { e.printStackTrace(); }
    }
}