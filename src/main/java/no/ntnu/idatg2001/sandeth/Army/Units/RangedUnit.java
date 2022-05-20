package no.ntnu.idatg2001.sandeth.Army.Units;

/**
 * Subclass of unit.
 * Unit specialises in long range attacks.
 */
public class RangedUnit extends Unit {

    /**
     * Constructor for RangedUnit.
     * @param name Name of unit.
     * @param health Amount of health, meaning amount of damage they may take before dying.
     */
    public RangedUnit(String name, int health) {
        super(name, health, 20, 12);
    }

    /**
     * Override of abstract method.
     * Resists more damage the first 2 hits.
     * @return ResistBonus, meaning resistant to damage.
     */
    @Override
    public int getResistBonus() {
        if (getHitsTaken() <= 1) //First hit gives 6 resist.
            return 6;
        else if (getHitsTaken() == 2) //Second hit gives 4 resist.
            return 4;
        else
            return 0; //Any more hits give no resist.
    }

    /**
     * Override of abstract method.
     * Using hitsTaken distance is predicted.
     * @return DamageBonus, meaning extra damage dealt.
     */
    @Override
    public int getAttackBonus() {
        if (getTerrain() == Bonuses.terrain.FOREST) //Forest makes it hard for ranged units.
            return 1;
        else if (getTerrain() == Bonuses.terrain.HILL && getHitsTaken() < 2) //Hills and distance is when they are strongest.
            return 6;
        else if (getTerrain() == Bonuses.terrain.HILL) //Hills make these units stronger.
            return 4;
        else if (getHitsTaken() >= 1) //If another unit has hit ranged 2 times they are considered close and ranged will do less dmg.
            return 0;
        else
            return 1;
    }
}
