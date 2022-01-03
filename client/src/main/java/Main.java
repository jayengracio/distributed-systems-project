import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Queue;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import service.core.*;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Main implements Runnable{
    static String host;
    static ConnectionFactory connectionFactory;
    static Connection connection;
    /**
     * This is the starting point for the application. Here, we must
     * get a reference to the Broker Service and then invoke the
     * getQuotations() method on that service.
     *
     * Finally, you should print out all quotations returned
     * by the service.
     *
     * @param args
     */

    public static void main(String[] args) {

        host = args.length == 0 ? "localhost":args[0];
        connectionFactory = new ActiveMQConnectionFactory("failover://tcp://"+host+":61616");

        try {
            connection = connectionFactory.createConnection();
            connection.setClientID("client");
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue queue = session.createQueue("REQUESTS");
            MessageProducer requestProducer = session.createProducer(queue);

            connection.start();
            Main main = new Main();
            Thread thread = new Thread(main);
            thread.start();
            for (Item item : items) {
                Message request = session.createObjectMessage(item);
                requestProducer.send(request);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        try {
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue responsesQueue = session.createQueue("RESPONSES");
            MessageConsumer consumer = session.createConsumer(responsesQueue);
            while (true) {
                Message message = consumer.receive();
                if (message instanceof ObjectMessage) {
                    Object content = ((ObjectMessage) message).getObject();
                    if (content instanceof ClientApplicationMessage) {
                        ClientApplicationMessage response = (ClientApplicationMessage) content;
                        displayBaseItem(response.baseItem);
                        for (GameItem item: response.items) {
                            displayItem(item);
                        }
                        System.out.println("\n");
                    }
                    message.acknowledge();
                } else {
                    System.out.println("Unknown message type: " +
                            message.getClass().getCanonicalName());
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * Display the client info nicely.
     *
     * @param item
     */
    public static void displayBaseItem(Item item) {
        System.out.println("|=================================================================================================================|");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println(
                "| Name: " + String.format("%1$-29s", item.getName()) +
                " | Type: " + String.format("%1$-27s", item.getType()) +
                " | Grade: " + String.format("%1$-30s", item.getGrade())+" |");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println("|=================================================================================================================|");
    }

    /**
     * Display a quotation nicely - note that the assumption is that the quotation will follow
     * immediately after the profile (so the top of the quotation box is missing).
     *
     * @param item
     */
    public static void displayItem(GameItem item) {
        Stats stats = item.getStats();
        if (stats != null) {
            System.out.println(
                    "| Game: " + String.format("%1$-26s", item.getGameReference()) +
                            "| Name: " + String.format("%1$-24s", item.getName()) +
                            "| Stats: " + String.format("%1$-28s", stats.getDamage() + " |"));
            System.out.println("|=================================================================================================================|");
        }
    }

    /**
     * Test Data
     */
    public static final Item[] items = {
            new Item("Ranged", 'B', "Yew Shortbow"),
            new Item("Melee", 'S', "Armadyl Godsword"),
            new Item("Magic", 'D', "Air Staff"),
            new Item("Gun", 'B', "D5 Copperhead"),
            new Item("Melee", 'S', "Crowbar"),
            new Item("Cyberware", 'D', "Gorilla Arms")
    };
}