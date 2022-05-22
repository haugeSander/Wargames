package no.ntnu.idatg2001.wargames.army.units;

/**
 * Abstract class of Army.Army.Units.
 * Basis of what all units are derived from.
 */
public abstract class Unit implements Bonuses {
    private String name;
    private int health; //Total health, means the unit can take more damage before dying.

    private final String className; //The type of unit, infantry/ranged etc.
    private final int attack; //Attack value to decide how much health each attack does.
    private final int armor; //Defence value to decide resistance to attacks.

    private boolean isAlive; //Boolean which tells if a unit is dead or alive.
    private int hitsDealt; //Count hits dealt.
    private int hitsTaken; //Count hits received.
    private terrain terrain; //Enum terrain from bonus interface.

    /**
     * Class must only be accessed by its children and package.
     * @param name Name of unit.
     * @param health Health of unit.
     * @param attack Attack stat of unit.
     * @param armor Armor stat of unit, resistance against damage.
     */
    protected Unit(String name, int health, int attack, int armor) {
        setName(name);
        setHealth(health);
        this.attack = attack;
        this.armor = armor;
        className = getClass().getSimpleName();
        hitsDealt = 0;
        hitsTaken = 0;
    }

    /**
     * Method used to attack other units.
     * If opponents health is 0 or less after attack, they die.
     * @param opponent Opposing unit object to attack.
     */
    public void attack(Unit opponent) {
        opponent.setHealth(opponent.health - (this.attack + getAttackBonus()) +
                (opponent.armor + opponent.getResistBonus()));
        this.hitsDealt++;
        opponent.hitsTaken++;
    }

    /**
     * Override method of toString.
     * @return Unit described as a string format.
     */
    @Override
    public String toString() {
        return className + ": " + name + ", HP: " + health;
    }

    /**
     * Sets health and changes states of being dead or alive based on hp.
     * Throws exception if unit goes below 0 hp.
     * @param health Integer of the health points the unit should have.
     */
    public void setHealth(int health) {
        if (health <= 0) {
            this.health = 0;
            isAlive = false;
        }else {
            this.health = health;
            isAlive = true;
        }
    }

    /**
     * Sets name if it is valid.
     * @param name String representation of name.
     * @throws NullPointerException If name is blank or null.
     */
    public void setName(String name) throws NullPointerException {
        if (name.isEmpty()) {
            throw new NullPointerException("Name cannot be null.");
        } else {
            this.name = name;
        }
    }

    /**
     * Set terrain type.
     * @param terrain String representation of a terrain type.
     * @throws IllegalArgumentException If invalid terrain is entered.
     */
    public void setTerrain(String terrain) throws IllegalArgumentException {
        boolean validTerrain = false;

        for (Bonuses.terrain b : Bonuses.terrain.values()) {
            if (b.name().equalsIgnoreCase(terrain)) {
                this.terrain = b;
                validTerrain = true;
            }
        }
        if (!validTerrain)
            throw new IllegalArgumentException(terrain + " is an invalid terrain!");
    }

    /**
     * Getter for the name of unit.
     * @return String name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for health of unit.
     * @return Integer value of health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Getter for attack stat of unit.
     * @return Integer of attack value stat.
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Getter for armor stat of unit.
     * @return Integer of resist value stat.
     */
    public int getArmor() {
        return armor;
    }

    /**
     * Getter for isAlive, true if it is alive, false if not.
     * @return Boolean of isAlive.
     */
    public boolean getIsAlive() {
        return isAlive;
    }

    /**
     * Getter for amount of hits unit has dealt.
     * @return Integer of hits dealt.
     */
    public int getHitsDealt() {
        return hitsDealt;
    }

    /**
     * Getter for amount of hits unit has taken.
     * @return Integer of hits taken.
     */
    public int getHitsTaken() {
        return hitsTaken;
    }

    /**
     * Getter for terrain set on this unit.
     * @return Enum of terrain.
     */
    public terrain getTerrain() {
        return terrain;
    }

    /**
     * Getter for className.
     * @return String for classname of subclasses.
     */
    public String getClassName() {
        return className;
    }
}
