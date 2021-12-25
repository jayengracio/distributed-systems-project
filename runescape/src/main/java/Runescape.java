import service.core.*;

import java.util.*;

public class Runescape extends AbstractGameService {
    public static final String PREFIX = "RS";
    public static final String GAME = "Runescape";

    @Override
    public GameItem generateGameItem(Item item) {
        Stats stats = new Stats();
        stats.setDurability(100);
        stats.setDamage(generateDamage(10, 100));

        switch (item.type) {
            case "Ranged":
                stats.setDamageType("Ranged");
                break;
            case "Melee":
                stats.setDamageType("Melee");
                break;
            case "Magic":
                stats.setDamageType("Magic");
                break;
            default:
                System.out.println("Item type does not exist for this game.");
                return new GameItem(PREFIX, GAME, null);
        }

        if (item.getGrade() == '\0')
            item.setGrade(generateGrade());

        stats.setDamage(modifyDamageByGrade(item.grade, stats.getDamage()));
        return new GameItem(PREFIX, GAME, stats);
    }

    private double generateDamage(double min, double max) {
        return Math.floor(Math.random() * (max - min + 1) + min);
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

}
