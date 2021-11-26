import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import service.core.GameService;

public class Receiver {
    public static void main(String[] args) {
        String host = args.length == 0 ? "localhost" : args[0];

        if (!host.equals("localhost")) {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        GameService service = new Cyberpunk();

        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory("failover://tcp://" + host + ":61616");
            Connection connection = factory.createConnection();
            connection.setClientID("cyberpunk");
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue queue = session.createQueue("GAMES");
            Topic topic = session.createTopic("APPLICATIONS");
            MessageConsumer consumer = session.createConsumer(topic);
            MessageProducer producer = session.createProducer(queue);

            connection.start();
            // TODO
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
