package no.ntnu.idatg2001.sandeth.Simulation;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatg2001.sandeth.Army.Army;
import no.ntnu.idatg2001.sandeth.Army.Units.Bonuses;
import no.ntnu.idatg2001.sandeth.Army.Units.Unit;
import java.util.Arrays;
import java.util.Random;

/**
 * Class which represent the simulation part of the application.
 */
public class Battle extends BattleUpdater {
    private Army army1;
    private Army army2;

    /**
     * Main constructor to take two armies.
     * @param army1 Army 1.
     * @param army2 Army 2.
     */
    public Battle(Army army1, Army army2) {
        this.army1 = army1;
        this.army2 = army2;
    }

    /**
     * Overloading constructor with no parameters.
     */
    public Battle() {
    }

    /**
     * Simulator of a battle, running simulate
     * step until there is no more units in one
     * of the armies.
     * @return The winner army.
     */
    public String simulate() {
        boolean battleFinished = false;

        while (!battleFinished) {
            if (!army1.hasUnits() || !army2.hasUnits())
                battleFinished = true;
            else {
                simulateStep(army1, army2);
            }
        }

        if (!army2.hasUnits() && army1.hasUnits())
            return army1.getName();
         else
             return army2.getName();
    }

    /**
     * Simulates one step at a time.
     * @param army1 Army1 to battle.
     * @param army2 Army2 to battle.
     */
    public void simulateStep(Army army1, Army army2) {
        Unit randomArmy1Unit = army1.getRandom();
        Unit randomArmy2Unit = army2.getRandom();
        String stepEvent; //Main event this step.
        int randomInt = new Random().nextInt(2);

        if (randomInt == 0 && randomArmy1Unit.getIsAlive()) //Army 1 gets to attack.
            randomArmy1Unit.attack(randomArmy2Unit);
        if (randomInt == 1 && randomArmy2Unit.getIsAlive()) //Army 2 gets to attack.
            randomArmy2Unit.attack(randomArmy1Unit);

        if (!randomArmy1Unit.getIsAlive()) {
            army1.remove(randomArmy1Unit);
            stepEvent = army2.getName() + ": " + randomArmy2Unit.getName() +
                " died whilst fighting " + randomArmy1Unit.getName();
        } else if (!randomArmy2Unit.getIsAlive()) {
            army2.remove(randomArmy2Unit);
            stepEvent = army1.getName() + ": " + randomArmy1Unit.getName() +
                " died whilst fighting " + randomArmy2Unit.getName();
        } else if (!army1.hasUnits())
            stepEvent = army2.getName() + " has won!";
        else if (!army2.hasUnits())
            stepEvent = army1.getName() + " has won!";
        else
            stepEvent = "";

        battleActions(stepEvent); //Observer which takes stepEvent and updates subscribers.
        battleStatus(army1.getUnits().size(), army2.getUnits().size()); //Same for size of armies.
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
        try {
            for (Unit unit : army1.getUnits())
                unit.setTerrain(terrain);

            for (Unit unit : army2.getUnits())
                unit.setTerrain(terrain);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
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
