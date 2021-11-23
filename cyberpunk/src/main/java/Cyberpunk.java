import service.core.*;

public class Cyberpunk {
    public static final String PREFIX = "CP2077";
    public static final String GAME = "Cyberpunk 2077";

    public Item generateItem(User user) {
        switch(user.item) {
            case "Gun":
                // TODO
            case "Sword":
                // TODO
            case "Axe":
                // TODO
            default:
                // TODO
        }
        return new Item("Gun", 'B', "D5 Sidewinder");
    }
}
