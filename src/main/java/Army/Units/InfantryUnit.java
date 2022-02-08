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
        return 1;
    }

    /**
     * Override of abstract method.
     * Using hitsTaken, distance is predicted.
     * @return DamageBonus, meaning extra damage dealt.
     */
    @Override
    public int getAttackBonus() {
        if (getHitsTaken() > 2) //To give the unit a close range bonus.
            return 3;
        else
            return 1;
    }

}
