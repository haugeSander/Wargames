package no.ntnu.idatg2001.wargames.army.units;

/**
 * Subclass of CavalryUnit.
 * A stronger cavalry, should function as leader.
 * @author Sander Hauge
 * @version 1.0-SNAPSHOT
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
