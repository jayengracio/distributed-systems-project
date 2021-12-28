import org.apache.activemq.ActiveMQConnectionFactory;
import service.core.GameItem;
import service.core.GameRequestMessage;
import service.core.GameResponseMessage;

import javax.jms.*;

public class Receiver {
    static Zelda game = new Zelda();
    public static void main(String[] args) {
        String host = args.length == 0 ? "localhost":args[0];

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover://tcp://"+host+":61616");

        try {
            Connection connection = connectionFactory.createConnection();
            connection.setClientID("zelda");
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("ITEMS");
            Topic topic = session.createTopic("GAMES");
            MessageConsumer consumer = session.createConsumer(topic);
            MessageProducer producer = session.createProducer(queue);

            connection.start();

            while (true) {
                Message message = consumer.receive();
                if (message instanceof ObjectMessage) {
                    // It’s an Object Message
                    Object content = ((ObjectMessage) message).getObject();
                    if (content instanceof GameRequestMessage) {
                        // It’s a Quotation Request Message
                        GameRequestMessage request = (GameRequestMessage) content;
                        // Generate a quotation and send a quotation response message…
                        GameItem gameItem = game.generateGameItem(request.item);
                        Message response = session.createObjectMessage(
                                new GameResponseMessage(request.id, gameItem)
                        );
                        producer.send(response);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
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
