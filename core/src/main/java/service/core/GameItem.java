package service.core;
import java.io.Serializable;

public class GameItem implements Serializable {
    public String gameReference;
    public String name;
    public Stats stats;

    public GameItem(String gameReference, String name, Stats stats) {
        this.gameReference = gameReference;
        this.name = name;
        this.stats = stats;
    }

    public String getGameReference() {
        return gameReference;
    }

    public String getName() {
        return name;
    }

    public Stats getStats() {
        return stats;
    }
}