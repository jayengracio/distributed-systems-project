package api;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import service.core.ClientApplicationMessage;
import service.core.GameItem;
import service.core.Stats;

import java.io.*;

@Component
public class JMSReceiver {

    @JmsListener(destination = "RESPONSES", containerFactory = "mainFactory")
    public void receiveGameItemResponse(ClientApplicationMessage response) throws IOException {
        String itemsFileName = String.format("%s/items.csv", System.getProperty("user.home"));
        BufferedWriter writer = new BufferedWriter(new FileWriter(itemsFileName, true));
        for (GameItem item : response.getItems()) {
            Stats stats = item.getStats();
            System.out.println(item);
            String formattedItem = String.format("%s,%s,%s,%s,%s\n", item.getGameReference(), item.getName(), stats.getDamage(), stats.getDamageType(), stats.getDurability());
            writer.append(formattedItem);
        }
        writer.close();

        System.out.println("Items saved!");
    }
}
