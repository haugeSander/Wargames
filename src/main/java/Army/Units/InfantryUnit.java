package Army.Units;

/**
 * Subclass of unit.
 * Unit which specialises in close combat.
 */
public class InfantryUnit extends Unit {

    /**
     * Constructor for InfantryUnits.
     * @param name Name of unit.
     * @param health Amount of health, meaning amount of damage they may take before dying.
     */
    public InfantryUnit(String name, int health) {
        super(name, health, 15, 10);
    }

    @Override
    public int getResistBonus() {
        if (getTerrain() == Bonuses.terrain.FOREST) //Best units in forests.
            return 4;
        else
            return 1;
    }

    /**
     * Override of abstract method.
     * Using hitsTaken, distance is predicted.
     * @return DamageBonus, meaning extra damage dealt.
     */
    @Override
    public int getAttackBonus() {
        if (getHitsTaken() > 2 && getTerrain() == Bonuses.terrain.FOREST) //To give the unit a close range bonus and when in forests.
            return 4;
        if (getTerrain() == Bonuses.terrain.FOREST) //Infantry is strongest in forests.
            return 3;
        else if (getHitsTaken() > 2) //When close range they do most damage.
            return 2;
        else
            return 0;
    }
}
