package Simulation;

import Army.Army;
import Army.Units.Bonuses;
import Army.Units.Unit;

import java.util.Arrays;
import java.util.Random;

public class Battle {
    private Army army1;
    private Army army2;

    public Battle(Army army1, Army army2) {
        this.army1 = army1;
        this.army2 = army2;
    }

    public Battle() {
    }

    /**
     * Simulator of a battle, running simulate
     * step until there is no more units in one
     * of the armies.
     * @return The winner army.
     */
    public Army simulate() {
        boolean battleFinished = false;
        Unit tempUnit1;
        Unit tempUnit2;

        while (!battleFinished) {
            if (!army1.hasUnits() || !army2.hasUnits())
                battleFinished = true;
            else {
                tempUnit1 = army1.getRandom();
                tempUnit2 = army2.getRandom();

                simulateStep(tempUnit1, tempUnit2);
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
     * Simulates one step at a time.
     * @param tempUnit1 Random unit from army1.
     * @param tempUnit2 Random unit from army2.
     * @return String of events which happened.
     */
    public String simulateStep(Unit tempUnit1, Unit tempUnit2) {
        int randint = new Random().nextInt(2); //0<= Random int < 2

        if (randint == 0 && tempUnit1.getIsAlive()) { //Army 1 gets to attack.
            tempUnit1.attack(tempUnit2);

            if (!tempUnit2.getIsAlive()) {
                army2.remove(tempUnit2);
                return tempUnit1.getName() + " died whilst fighting " + tempUnit2.getName();
            }
        }
            if (randint == 1 && tempUnit2.getIsAlive()) { //Army 2 gets to attack.
                tempUnit2.attack(tempUnit1);
            }

        if (!tempUnit1.getIsAlive()) {
            army1.remove(tempUnit1);
            return tempUnit2.getName() + " died whilst fighting " + tempUnit1.getName();
        } else if (!army1.hasUnits())
            return army2.getName() + " has won!";
        else if (!army2.hasUnits())
            return army1.getName() + " has won!";
        return "";
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
     * Sets terrain on for all units, units may still be different terrain.
     * @param terrain String representation of terrain enum.
     * @throws IllegalArgumentException When terrain param is not valid.
     */
    public void setTerrain(String terrain) throws IllegalArgumentException {
        if (terrain.contains(Arrays.toString(Bonuses.terrain.values())))
            throw new IllegalArgumentException("No such terrain exist");

        for (Unit unit : army1.getUnits())
            unit.setTerrain(terrain);
        for (Unit unit : army2.getUnits())
            unit.setTerrain(terrain);
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
