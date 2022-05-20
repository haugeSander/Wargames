package no.ntnu.idatg2001.sandeth.Army.Units;

/**
 * Interface to manage attack and resist bonuses of different units.
 */
public interface Bonuses {
    enum terrain {HILL, PLAINS, FOREST}
    int getAttackBonus();
    int getResistBonus();
}
