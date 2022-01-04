package api;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Queue;
import javax.jms.Message;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import service.core.*;

@RestController
public class LocalBrokerService {
    static ConnectionFactory connectionFactory;
    static Connection connection;
    static Item baseItem;

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value="/items", method=RequestMethod.GET)
    public ResponseEntity<List<GameItem>> fetchItems() throws URISyntaxException {
        File f = new File(String.format("%s/items.csv", System.getProperty("user.home")));
        ArrayList<GameItem> items = new ArrayList<>();

        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+ "/test";

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));

        try {
            BufferedReader in = new BufferedReader(new FileReader(f));
            String line;
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(",");
                String gameReference = parts[0];
                String name = parts[1];
                double damage = Double.parseDouble(parts[2]);
                String damageType = parts[3];
                double durability = Double.parseDouble(parts[4]);

                Stats stats = new Stats(damage, damageType, durability);
                GameItem item = new GameItem(gameReference, name, stats);

                items.add(item);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(items, headers, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value="/items", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public ResponseEntity<String> createItem(@RequestBody Item item) throws URISyntaxException {
        baseItem = item;
        String path = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()+ "/test";

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(path));

        connectionFactory = new ActiveMQConnectionFactory("failover://tcp://localhost:61616");

        try {
            connection = connectionFactory.createConnection();
            connection.setClientID("client");
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue queue = session.createQueue("REQUESTS");
            MessageProducer requestProducer = session.createProducer(queue);

            connection.start();

            Message request = session.createObjectMessage(baseItem);
            requestProducer.send(request);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

             connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Item creation job submitted!", headers, HttpStatus.CREATED);
    }
}
