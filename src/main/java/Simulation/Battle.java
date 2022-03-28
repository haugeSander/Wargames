package Simulation;

import Army.Army;
import Army.Units.Unit;

import java.util.Random;

public class Battle {
    private Army army1;
    private Army army2;

    public Battle(Army army1, Army army2) {
        this.army1 = army1;
        this.army2 = army2;
    }

    /**
     * Simulator of a battle.
     * @return String of who won.
     * Returns specific Strings depending on who won.
     * If both forces are wiped out, a draw String is returned. (Should not happen)
     */
    public Army simulate() {
        Unit tempUnit1 = null;
        Unit tempUnit2 = null;
        boolean battleFinished = false;
        Random random = new Random();

        while (!battleFinished) {

            if (!army1.hasUnits() || !army2.hasUnits())
                battleFinished = true;
            else {
                tempUnit1 = army1.getRandom();
                tempUnit2 = army2.getRandom();
            }

            int randint = random.nextInt(0,2); //0<= Random int < 2

            if ((randint == 0 && !battleFinished) && tempUnit1.getIsAlive()) { //Army 1 gets to attack.
                tempUnit1.attack(tempUnit2);

                if (!tempUnit2.getIsAlive()) {
                    army2.remove(tempUnit2);
                }
            }

            if ((randint == 1 && !battleFinished) && tempUnit2.getIsAlive()) { //Army 2 gets to attack.
                tempUnit2.attack(tempUnit1);

                if (!tempUnit1.getIsAlive()) {
                    army1.remove(tempUnit1);
                }
            }
        }

        if (!army2.hasUnits() && army1.hasUnits()) {
            return army1;
        } else if (!army1.hasUnits() && army2.hasUnits()) {
            return army2;
        } else
            return null;
    }

    /**
     * Updates the armies in the simulation.
     * If null is input, nothing is done.
     * @param newArmyOne The updated army1.
     * @param newArmyTwo The updated army2.
     */
    public void updateArmies(Army newArmyOne, Army newArmyTwo) {
        if (newArmyOne != null)
            this.army1 = newArmyOne;
        if (newArmyTwo != null)
            this.army2 = newArmyTwo;
    }

    /**
     * Getter for army 1.
     * @return Army for army1.
     */
    public Army getArmy1() {
        return army1;
    }

    /**
     * Getter for army 2.
     * @return Army for army2.
     */
    public Army getArmy2() {
        return army2;
    }
}
