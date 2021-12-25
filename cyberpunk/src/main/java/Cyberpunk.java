import service.core.*;

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
        List<String> weapons;

        switch (item.type) {
            case "Gun":
                weapons = Arrays.asList("D5 Copperhead", "D5 Sidewinder", "HJSH-18 Masamune", "M2515 Ajax", "Nowaki");
                item.name = generateName(weapons);
                stats.damage = generateDamage(15, 50);
                break;
            case "Melee":
            case "Sword":
                weapons = Arrays.asList("Baseball Bat", "Knife", "Katana", "Jinchi-Maru", "Gold-plated Baseball Bat");
                item.name = generateName(weapons);
                stats.damage = generateDamage(60, 90);
                break;
            case "Cyberware":
                weapons = Arrays.asList("Gorilla Arms", "Mantis Blades", "Monowire", "Projectile Launch System");
                item.name = generateName(weapons);
                stats.damage = generateDamage(75, 102);
                break;
            default:
                System.out.println("Item type does not exist for this service.");
                return new GameItem(PREFIX, GAME, null);
        }

        if (item.grade == '\0')
            item.grade = generateGrade();

        if (item.name == null)
            item.name = generateName(weapons);

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

    private String generateName(List<String> weapons) {
        Random rand = new Random();
        return weapons.get(rand.nextInt(weapons.size()));
    }

    private double modifyDamageByGrade(char grade, double damage) {
        //System.out.println("Before Modifier: " + damage);
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
