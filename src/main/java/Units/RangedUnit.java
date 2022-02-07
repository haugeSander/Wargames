package Units;

public class RangedUnit extends Unit {

    /**
     * Constructor for RangedUnit.
     * @param name Name of unit.
     * @param health Amount of health, meaning amount of damage they may take before dying.
     */
    public RangedUnit(String name, int health) {
        super(name, health, 20, 12);
    }

    @Override
    protected int getResistBonus() {
        //if (timesHit <= 1)
        //  return 6;
        //else if (timesHit == 2)
        //  return 4;
        //else
        return 1;
    }
    @Override
    protected int getAttackBonus() {
        //if (distance > 10) If the unit is far away from target gain bonus.
        return 2;
    }
}
