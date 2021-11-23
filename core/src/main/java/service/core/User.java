package service.core;
import java.io.Serializable;

public class User implements Serializable {
    public String name;
    public String item;

    public User(String name, String item) {
        this.name = name;
        this.item = item;
    }
}