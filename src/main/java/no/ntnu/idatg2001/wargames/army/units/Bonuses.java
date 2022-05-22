package no.ntnu.idatg2001.wargames.Army.Units;

/**
 * Interface to manage attack and resist bonuses of different units.
 * Manages also the terrain allowed.
 */
public interface Bonuses {
    enum terrain {HILL, PLAINS, FOREST}
    int getAttackBonus();
    int getResistBonus();
}