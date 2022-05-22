package no.ntnu.idatg2001.wargames.Army.Units;

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
        super(name, health, 25, 15);
    }
}
