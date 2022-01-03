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

    public GameItem() {}

    public String getGameReference() {
        return gameReference;
    }

    public String getName() {
        return name;
    }

    public Stats getStats() {
        return stats;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGameReference(String gameReference) {
        this.gameReference = gameReference;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        return "GameItem{" +
                "gameReference='" + gameReference + '\'' +
                ", name='" + name + '\'' +
                ", stats=" + stats +
                '}';
    }
}