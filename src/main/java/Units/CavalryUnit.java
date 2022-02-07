package Units;

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

    @Override
    protected int getResistBonus() {
        //if (distance < 5) get a resistBonus if enemies attack close range.
        return 1;
    }
    @Override
    protected int getAttackBonus() {
        //if (firstAttack)
        //  return 6;
        //else
        return 2;
    }
}
