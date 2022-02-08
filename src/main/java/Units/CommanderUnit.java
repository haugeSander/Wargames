package Units;

/**
 * Subclass of CavalryUnit.
 * A stronger cavalry, should function as leader.
 */
public class CommanderUnit extends CavalryUnit {

    /**
     * Constructor for the CommanderUnit.
     * @param name Name of unit.
     * @param health Amount of health, meaning amount of damage they may take before dying.
     */
    public CommanderUnit(String name, int health) {
        super(name, health, 20, 12);
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
