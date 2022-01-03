import org.junit.*;
import service.core.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CyberpunkTest {
    @Test
    public void generateStats() {
        Item item = new Item("Melee");
        GameService service = new Cyberpunk();
        GameItem gameItem = service.generateGameItem(item);
        assertNotNull(gameItem.stats);
        assertEquals("Cyberpunk 2077", gameItem.name);
        assertEquals("CP2077", gameItem.gameReference);
    }
}
