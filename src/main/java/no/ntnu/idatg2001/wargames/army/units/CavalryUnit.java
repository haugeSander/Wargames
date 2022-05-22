package no.ntnu.idatg2001.wargames.army.units;

/**
 * Subclass of unit.
 * Unit which should function as a soldier on a horse.
 */
public class CavalryUnit extends Unit {

    /**
     * Main method used by CavalryUnit. Sets attack and armor stat from the start.
     * @param name Name of unit.
     * @param health Amount of health, meaning amount of damage they may take before dying.
     */
    public CavalryUnit(String name, int health) {
        super(name, health, 20, 12);
    }

    /**
     * Overloading method used by CommanderUnit.
     * @param name Name of unit.
     * @param health Amount of health, meaning amount of damage they may take before dying.
     * @param attack Amount of damage dealt per hit.
     * @param armor Amount of resistance to damage.
     */
    public CavalryUnit(String name, int health, int attack, int armor) {
        super(name, health, attack, armor);
    }

    /**
     * Override of abstract method.
     * When close range it resists more damage.
     * @return ResistBonus, meaning resistant to damage.
     */
    @Override
    public int getResistBonus() {
        if (getTerrain() == Bonuses.terrain.FOREST) //No bonus if the fight is in a forest.
            return 0;
        else if (getHitsTaken() > 2) //Gets a 2 resist bonus if enemies attack close range.
            return 2;
        else
            return 0;
    }

    /**
     * Override of abstract method.
     * Using hitsTaken, the first hit gives major damage boost.
     * @return DamageBonus, meaning extra damage dealt.
     */
    @Override
    public int getAttackBonus() {
        if (getHitsDealt() < 1 && getTerrain() == Bonuses.terrain.PLAINS) //Plains and first hit makes these strongest.
            return 8;
        else if (getHitsDealt() < 1) //First hit deals a lot of damage.
            return 6;
        else if (getTerrain() == Bonuses.terrain.PLAINS) //Plains are the preferred battlefield.
            return 4;
        else
            return 1;
    }
}
