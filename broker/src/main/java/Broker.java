import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.Queue;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import service.core.*;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Implementation of the broker service that uses the Service Registry.
 *
 * @author Rem
 *
 */
public class Broker implements Runnable {
    static int SEED_ID = 0;
    static Map<Long, Item> cache = new HashMap<Long, Item>();
    static Map<Long, ClientApplicationMessage> gameItemsCache = new HashMap<Long, ClientApplicationMessage>();
    static String host;

    static ConnectionFactory connectionFactory;
    static Connection connection;

    public static void main(String[] args) {
        host = args.length == 0 ? "localhost":args[0];
        connectionFactory = new ActiveMQConnectionFactory("failover://tcp://"+host+":61616");

        try {
            connection = connectionFactory.createConnection();
            connection.setClientID("broker");
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue requestsQueue = session.createQueue("REQUESTS");
            MessageConsumer consumer = session.createConsumer(requestsQueue);

            Topic topic = session.createTopic("GAMES");
            MessageProducer producer = session.createProducer(topic);

            Queue responsesQueue = session.createQueue("RESPONSES");
            MessageProducer responsesProducer = session.createProducer(responsesQueue);

            connection.start();

            Broker broker = new Broker();
            Thread thread = new Thread(broker);
            thread.start();
            while (true) {
                Message message = consumer.receive();
                if (message instanceof ObjectMessage) {
                    Object content = ((ObjectMessage) message).getObject();
                    if (content instanceof Item) {
                        Item item = (Item) content;
                        GameRequestMessage gameItemRequest = new GameRequestMessage(SEED_ID++, item);
                        Message request = session.createObjectMessage(gameItemRequest);
                        cache.put(gameItemRequest.id, gameItemRequest.item);
                        producer.send(request);

                        try {
                            Thread.sleep(8000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        Message response = session.createObjectMessage(gameItemsCache.get(gameItemRequest.id));

                        responsesProducer.send(response);
                    }
                }
                message.acknowledge();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue queue = session.createQueue("ITEMS");
            Queue responsesQueue = session.createQueue("RESPONSES");
            MessageProducer responsesProducer = session.createProducer(responsesQueue);
            MessageConsumer serviceConsumer = session.createConsumer(queue);
            connection.start();
            while (true) {
                Message message = serviceConsumer.receive();
                if (message instanceof ObjectMessage) {
                    Object content = ((ObjectMessage) message).getObject();
                    if (content instanceof GameResponseMessage) {
                        GameResponseMessage gameItemResponse = (GameResponseMessage) content;

                        ClientApplicationMessage quotationsMessage = gameItemsCache.get(gameItemResponse.id);

                        if (quotationsMessage instanceof ClientApplicationMessage) {
                            quotationsMessage.items.add(gameItemResponse.gameItem);
                        } else {
                            List<GameItem> items = new ArrayList<GameItem>(Arrays.asList(gameItemResponse.gameItem));
                            ClientApplicationMessage responseMessage = new ClientApplicationMessage(gameItemResponse.id, cache.get(gameItemResponse.id), items);

                            gameItemsCache.put(gameItemResponse.id, responseMessage);
                        }
                    }
                }
                message.acknowledge();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}