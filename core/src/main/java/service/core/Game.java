package service.core;
import java.io.Serializable;

public class Game implements Serializable {
    public String reference;
    public String name;
    public Stats stats;

    public Game(String reference, String name, Stats stats) {
        this.reference = reference;
        this.name = name;
        this.stats = stats;
    }
}