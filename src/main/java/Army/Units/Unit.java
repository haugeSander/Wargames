package Army.Units;

/**
 * Abstract class of Army.Army.Units.
 * Basis of what all units are derived from.
 */
public abstract class Unit implements Bonuses {
    private String name;

    private int health; //Total health, means the unit can take more damage before dying.
    private int attack; //Attack value to decide how much health each attack does.
    private int armor; //Defence value to decide resistance to attacks.

    private boolean isAlive; //Boolean which tells if a unit is dead or alive.
    private int hitsDealt; //Count hits dealt.
    private int hitsTaken; //Count hits received.
    private String className; //The type of unit, infantry/ranged etc.
    private terrain terrain; //Enum terrain from bonus interface.

    public Unit(String name, int health, int attack, int armor) {
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
        return name + ", " + health + " hp" + ". Hits taken: "
                + hitsTaken + ". Hits dealt: " + hitsDealt + ".";
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
     * @throws IllegalArgumentException
     */
    public void setTerrain(String terrain) throws IllegalArgumentException {
        for (Bonuses.terrain b : Bonuses.terrain.values()) {
            if (b.name().equalsIgnoreCase(terrain)) {
                this.terrain = b;
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getArmor() {
        return armor;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public int getHitsDealt() {
        return hitsDealt;
    }

    public int getHitsTaken() {
        return hitsTaken;
    }

    public terrain getTerrain() {
        return terrain;
    }

    public String getClassName() {
        return className;
    }
}
