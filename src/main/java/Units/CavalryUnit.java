package Units;

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
        super(name, health, 15, 8);
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
    protected int getResistBonus() {
        if (getHitsTaken() > 2) //Gets a 2 resist bonus if enemies attack close range.
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
    protected int getAttackBonus() {
        if (getHitsDealt() < 1)
            return 6;
        else
            return 1;
    }
}
