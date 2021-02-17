import consumer.ConsumerAgent;
import energy.Energy;
import energy.Time;
import energy.Type;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.util.Pair;
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
                    new Energy(0, Type.RENEWABLE, 127.92, new Pair<>(8, 10)),
                    new Energy(1, Type.CLASSIC, 127.20, new Pair<>(14, 18)),
                    new Energy(2, Type.CLASSIC, 127.16, new Pair<>(9, 10)),
                    new Energy(3, Type.RENEWABLE, 139.21, new Pair<>(14, 17))
            };
            systemAgent = mc.createNewAgent("SystemAgent", SystemAgent.class.getName(), null);
            systemAgent.start();
            producerAgent = mc.createNewAgent("ProducerAgent", ProducerAgent.class.getName(), energies);
            producerAgent.start();
            consumerAgent = mc.createNewAgent("ConsumerAgent", ConsumerAgent.class.getName(), null);
            consumerAgent.start();
        } catch (StaleProxyException e) { e.printStackTrace(); }
    }
}