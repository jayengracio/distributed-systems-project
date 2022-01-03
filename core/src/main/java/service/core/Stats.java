package service.core;
import java.io.Serializable;

public class Stats implements Serializable {
    public double damage;
    public String damageType;
    public double durability;

    public Stats(double damage, String damageType, double durability) {
        this.damage = damage;
        this.damageType = damageType;
        this.durability = durability;
    }

    public Stats() {}

    public double getDamage() {
        return damage;
    }

    public double getDurability() {
        return durability;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public void setDurability(double durability) {
        this.durability = durability;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "damage=" + damage +
                ", damageType='" + damageType + '\'' +
                ", durability=" + durability +
                '}';
    }
}