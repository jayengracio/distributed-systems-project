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

    public Item(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public char getGrade() {
        return grade;
    }

    public String getType() {
        return type;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }
}