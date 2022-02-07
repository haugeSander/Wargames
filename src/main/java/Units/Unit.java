package Units;

public class Unit {
    private String name;
    private int health; //Total health, means the unit can take more damage before dying.
    private int attack; //Attack value to decide how much health each attack does.
    private int defence; //Defence value to decide resistance to attacks.
    private boolean isAlive; //Boolean which tells if a unit is dead or alive.

    public Unit(String name, int health, int attack, int defence) {
        this.name = name;

        if (health < 0){
            health = 0;
            this.isAlive = false;
         }else {
            this.health = health;
            this.isAlive = true;
        }
        this.attack = attack;
        this.defence = defence;
    }

    /*
    public void attack(Unit opponent) {
        opponent.health = opponent.health - (this.attack + getAttackBonus()) +
                (opponent.defence + opponent.getResistBonus());

        attackBonus ++;
        opponent.resistBonus++;
    }
*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }
}
