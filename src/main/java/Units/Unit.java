package Units;

/**
 * Abstract class of Units.
 * Basis of what all units are derived from.
 */
public abstract class Unit extends Bonuses {
    private String name;
    private int health; //Total health, means the unit can take more damage before dying.
    private int attack; //Attack value to decide how much health each attack does.
    private int armor; //Defence value to decide resistance to attacks.
    private boolean isAlive; //Boolean which tells if a unit is dead or alive.

    public Unit(String name, int health, int attack, int armor) {
        this.name = name;

        if (health < 0){
            health = 0;
            this.isAlive = false;
         }else {
            this.health = health;
            this.isAlive = true;
        }
        this.attack = attack;
        this.armor = armor;
    }

    /**
     * Method used to attack other units.
     * @param opponent Opposing unit object to attack.
     */
    public void attack(Unit opponent) {
        opponent.health = opponent.health - (this.attack + getAttackBonus()) +
                (opponent.armor + opponent.getResistBonus());
    }

    @Override
    public String toString() {
        return name + ", " + this.health+ " hp";
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

    public void setHealth(int health) {
        this.health = health;
    }
}
