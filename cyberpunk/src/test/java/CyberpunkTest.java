import org.junit.*;
import service.core.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CyberpunkTest {
    @Test
    public void generateStats() throws Exception {
        Item item = new Item("Melee");
        GameService service = new Cyberpunk();
        GameItem gameItem = service.generateGameItem(item);
        System.out.println("Item Name: " + item.name);
        System.out.println("Game Company: " + gameItem.name);
        System.out.println("Game Reference: " + gameItem.gameReference);
        System.out.println("Grade: " + item.grade);
        System.out.println("Type: " + gameItem.stats.damageType);
        System.out.println("Damage: " + gameItem.stats.damage);
        System.out.println("Durability: " + gameItem.stats.durability);
        assertNotNull(gameItem.stats);
        assertEquals("Cyberpunk 2077", gameItem.name);
        assertEquals("CP2077", gameItem.gameReference);
    }
}
