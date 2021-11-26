import org.junit.*;
import service.core.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CyberpunkTest {
    @Test
    public void generateStats() throws Exception {
        Item item = new Item("Gun", 'C', "AGun");
        GameService service = new Cyberpunk();
        Stats stats = service.generateStats(item);
        assertEquals("AGun", item.name);
        System.out.println("Type: " + stats.damageType);
        System.out.println("Damage: " + stats.damage);
        System.out.println("Durability: " + stats.durability);
        assertNotNull(stats);
    }
}
