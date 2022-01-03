import service.core.AbstractGameService;
import service.core.GameItem;
import service.core.Item;
import service.core.Stats;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Cyberpunk extends AbstractGameService {
    public static final String PREFIX = "CP2077";
    public static final String GAME = "Cyberpunk 2077";

    @Override
    public GameItem generateGameItem(Item item) {
        Stats stats = new Stats();
        stats.durability = generateDurability();
        stats.damageType = generateDamageType();

        switch (item.type) {
            case "Gun":
                stats.damage = generateDamage(15, 50);
                break;
            case "Melee":
            case "Sword":
                stats.damage = generateDamage(60, 90);
                break;
            case "Cyberware":
                stats.damage = generateDamage(75, 102);
                break;
            default:
                System.out.println("Item type does not exist for this service.");
                return null;
        }

        if (item.grade == '\0')
            item.grade = generateGrade();

        stats.damage = modifyDamageByGrade(item.grade, stats.damage);
        return new GameItem(PREFIX, GAME, stats);
    }

    private double generateDamage(double min, double max) {
        return Math.floor(Math.random() * (max - min + 1) + min);
    }

    private double generateDurability() {
        return Math.floor(Math.random() * (100 + 1) + 0);
    }

    private char generateGrade() {
        Random rand = new Random();
        List<Character> grades = Arrays.asList('A', 'B', 'C', 'D', 'F', 'S');
        return grades.get(rand.nextInt(grades.size()));
    }

    private String generateDamageType() {
        Random rand = new Random();
        List<String> damageTypes = Arrays.asList("Physical", "Thermal", "Chemical", "Electrical");
        return damageTypes.get(rand.nextInt(damageTypes.size()));
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
