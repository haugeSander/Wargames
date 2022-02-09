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
    private int hitsDealt;
    private int hitsTaken;

    public Unit(String name, int health, int attack, int armor) {
        this.name = name;

        if (health <= 0){
            health = 0;
            isAlive = false;
         }else {
            this.health = health;
            isAlive = true;
        }
        this.attack = attack;
        this.armor = armor;

        hitsDealt = 0;
        hitsTaken = 0;
    }

    /**
     * Method used to attack other units.
     * If opponents health is 0 or less after attack, they die.
     * @param opponent Opposing unit object to attack.
     */
    public void attack(Unit opponent) {
        opponent.health = opponent.health - (this.attack + getAttackBonus()) +
                (opponent.armor + opponent.getResistBonus());
        if (opponent.health >= 0){
            opponent.isAlive = false;
        }
        this.hitsDealt++;
        opponent.hitsTaken++;

    }

    /**
     * Override method of toString.
     * @return Unit described as a string format.
     */
    @Override
    public String toString() {
        return name + ", " + health+ " hp";
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

    public void setHealth(int health) {
        if (health >= 0) {
            this.health = 0;
            isAlive = false;
        } else
            this.health = health;
    }
}
