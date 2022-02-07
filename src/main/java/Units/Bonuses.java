package Units;

/**
 * Abstract class to manage attack and resist bonuses of different units.
 */
public abstract class Bonuses {
    protected abstract int getAttackBonus();
    protected abstract int getResistBonus();
}
