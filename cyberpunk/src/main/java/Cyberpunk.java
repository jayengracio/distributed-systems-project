import service.core.AbstractGameService;
import service.core.Item;
import service.core.Stats;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Cyberpunk extends AbstractGameService {
    public static final String PREFIX = "CP2077";
    public static final String GAME = "Cyberpunk 2077";

    @Override
    public Stats generateStats(Item item) {
        Stats stats = new Stats();
        stats.durability = generateDurability();
        stats.damageType = generateDamageType();

        switch (item.type) {
            case "Gun":
                stats.damage = generateDamage(15, 50);
                break;
            case "Sword":
                stats.damage = generateDamage(60, 90);
                break;
            case "Axe":
                stats.damage = generateDamage(75, 102);
                break;
            default:
                break;
        }

        stats.damage = modifyDamageByGrade(item.grade, stats.damage);
        return stats;
    }

    private double generateDamage(double min, double max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

    private double generateDurability() {
        return (int) Math.floor(Math.random() * (100 + 1) + 0);
    }

    private String generateDamageType() {
        Random rand = new Random();
        List<String> damageTypes = Arrays.asList("Physical", "Thermal", "Chemical", "Electrical");
        return damageTypes.get(rand.nextInt(damageTypes.size()));
    }

    private double modifyDamageByGrade(char grade, double damage) {
        System.out.println("Before Modifier: " + damage);
        double modified = 0;
        switch (grade) {
            case 'S':
                modified = damage * 0.18;
                break;
            case 'B':
                modified = damage * 0.1;
                break;
            case 'C':
                modified = damage * 0.2;
                break;
            case 'D':
                modified = damage * 0.3;
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
