package service.core;
import java.io.Serializable;

public class Item implements Serializable {
    public String type;
    public char grade;
    public String name;

    public Item(String type, char grade, String name) {
        this.type = type;
        this.grade = grade;
        this.name = name;
    }
}