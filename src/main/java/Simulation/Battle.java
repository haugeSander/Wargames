package Simulation;

import Army.Army;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Battle {
    private Army army1;
    private Army army2;
    //private List<Army> armyList;

    public Battle(Army army1, Army army2) {
        this.army1 = army1;
        this.army2 = army2;
        //armyList = new ArrayList<>();
    }

    /**
     * Simulator.
     * @return String of who won.
     */
    public String simulate() {
        //for (Army army : armyList)
        //  armyList.add(army);
        Random randint = new Random();

        while (army1.hasUnits() || army2.hasUnits()) {
            if (randint.nextInt(0, 1) == 0) {
                army1.getRandom().attack(army2.getRandom());

                if (!army2.getRandom().getIsAlive()) {
                    army2.remove(army2.getRandom());
                }
            }

            if (randint.nextInt(0,1) == 1) {

            }

        }

        if (!army2.hasUnits()) {
            return "Army 1: " + army1.getName() + " has won!";
        } else if (!army1.hasUnits()) {
            return "Army 2: " + army2.getName() + " has won!";
        } else
            return "Draw!";
    }


}
