import consumer.ConsumerAgent;
import consumer.Policy;
import consumer.Preference;
import energy.Energy;
import energy.Time;
import energy.Type;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import jade.wrapper.State;
import marketplace.SystemAgent;
import producer.ProducerAgent;


public class Launcher {

    public static void test1(){
        Runtime runtime = Runtime.instance();
        Profile config = new ProfileImpl("localhost", 8888, null);
        config.setParameter("gui", "true");
        AgentContainer mc = runtime.createMainContainer(config);
        AgentController systemAgent;
        AgentController producerAgent1;
        AgentController producerAgent2;
        AgentController producerAgent3;
        AgentController consumerAgent;
        try {
            Energy[] energies = {
                    new Energy(Type.RENEWABLE, 127.92, 5,8, 10),
                    new Energy(Type.CLASSIC, 127.20, 5,14, 18),
                    new Energy(Type.CLASSIC, 127.16, 5, 9, 10),
                    new Energy(Type.RENEWABLE, 139.21, 5, 14, 17)
            };
            Preference[] preference = {new Preference(Policy.RENEWABLE,
                    128.0, 10.0,
                    9, 10)};

            systemAgent = mc.createNewAgent("SystemAgent", SystemAgent.class.getName(), null);
            systemAgent.start();
            producerAgent1 = mc.createNewAgent("ProducerAgent1", ProducerAgent.class.getName(), energies);
            producerAgent1.start();
            producerAgent2 = mc.createNewAgent("ProducerAgent2", ProducerAgent.class.getName(), energies);
            producerAgent2.start();
            producerAgent3 = mc.createNewAgent("ProducerAgent2", ProducerAgent.class.getName(), energies);
            producerAgent3.start();
            consumerAgent = mc.createNewAgent("ConsumerAgent", ConsumerAgent.class.getName(), preference);
            consumerAgent.start();
            State s = consumerAgent.getState();

            System.out.println(s.toString());
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }//fin test1
    public static void main(String[] args) {

        test1();

    }
}
