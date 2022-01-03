import service.core.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Zelda extends AbstractGameService{
    public static final String PREFIX = "ZBOTW";
    public static final String GAME = "Zelda: Breath of the Wild";
    public static Item[] items;

    @Override
    public GameItem generateGameItem(Item item) {
        Stats stats = new Stats();
        stats.setDurability(100);
        stats.setDamage(generateDamage(10, 100));

        switch (item.type) {
            case "Spear":
                stats.setDamageType("Spear");
                break;
            case "Sword":
                stats.setDamageType("Sword");
                break;
            case "Bow":
                stats.setDamageType("Bow");
                break;
            default:
                System.out.println("Item type does not exist for this game.");
                return null;
        }

        if (item.getGrade() == '\0')
            item.setGrade(generateGrade());

        stats.setDamage(modifyDamageByGrade(item.grade, stats.getDamage()));
        return new GameItem(PREFIX, GAME, stats);
    }

    private char generateGrade() {
        Random rand = new Random();
        List<Character> grades = Arrays.asList('A', 'B', 'C', 'D', 'F', 'S');
        return grades.get(rand.nextInt(grades.size()));
    }

    private double modifyDamageByGrade(char grade, double damage) {
        double modified = 0;
        switch (grade) {
            case 'S':
                modified = damage * 0.1;
                break;
            case 'B':
                modified = damage * 0.07;
                break;
            case 'C':
                modified = damage * 0.09;
                break;
            case 'D':
                modified = damage * 0.11;
                break;
            case 'F':
                modified = damage * 0.13;
                break;
            default:
                break;
        }

        if (grade == 'S')
            return damage + modified;
        else
            return damage - modified;
    }

    private double generateDamage(double min, double max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

}