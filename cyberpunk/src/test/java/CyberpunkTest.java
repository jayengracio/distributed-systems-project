import org.junit.*;
import service.core.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CyberpunkTest {
    @Test
    public void generateStats() throws Exception {
        Item item = new Item("Melee");
        GameService service = new Cyberpunk();
        Game game = service.generateGame(item);
        System.out.println("Item Name: " + item.name);
        System.out.println("Game Company: " + game.name);
        System.out.println("Game Reference: " + game.reference);
        System.out.println("Grade: " + item.grade);
        System.out.println("Type: " + game.stats.damageType);
        System.out.println("Damage: " + game.stats.damage);
        System.out.println("Durability: " + game.stats.durability);
        assertNotNull(game.stats);
        assertEquals("Cyberpunk 2077", game.name);
        assertEquals("CP2077", game.reference);
    }
}
