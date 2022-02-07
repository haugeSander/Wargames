package Units;

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
    protected int getResistBonus() {
        return 1;
    }

    @Override
    protected int getAttackBonus() {
        //if (distance < 5) { To give the unit a close range bonus.
        return 2;
    }

}
