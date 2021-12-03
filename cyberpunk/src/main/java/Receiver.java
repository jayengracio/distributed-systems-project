import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import service.core.*;

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
            Topic topic = session.createTopic("ITEMS");
            MessageConsumer consumer = session.createConsumer(topic);
            MessageProducer producer = session.createProducer(queue);

            connection.start();
            while (true) {
                // Get the next message from the ITEMS topic
                Message message = consumer.receive();
                // Check it is the right type of message
                if (message instanceof ObjectMessage) {
                    // It’s an Object Message
                    Object content = ((ObjectMessage) message).getObject();
                    if (content instanceof GameRequestMessage) {
                        // It’s a Stats Request Message
                        GameRequestMessage request = (GameRequestMessage) content;
                        // Generate stats of an item and send a game response message…
                        Game game = service.generateGame(request.item);
                        Message response = session
                                .createObjectMessage(new GameResponseMessage(request.id, game));
                        producer.send(response);
                    }
                } else {
                    System.out.println("Unknown message type: " + message.getClass().getCanonicalName());
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
