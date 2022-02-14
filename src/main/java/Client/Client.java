package Client;

import Army.Army;
import Army.Units.*;

import java.util.Locale;

public class Client {
    public Client() {

    }

    /**
     * Method to make an army of specified type.
     * @param typeOfUnit String type of what unit you want.
     * @param amount Int amount of units to add in army.
     * @param name String name of army.
     */
    private void makeArmy(String typeOfUnit, int amount, String name) {
        Army army = new Army(name);
        int i = 0;
        Unit unit = null;

        switch (typeOfUnit.toLowerCase(Locale.ROOT)) {
            case "infantry":
                unit = new InfantryUnit("Footman", 100);
                break;
            case "commander":
                unit = new CommanderUnit("Mountain King", 180);
                break;
            case "ranged":
                unit = new RangedUnit("Archer", 100);
                break;
            case "cavalry":
                unit = new CavalryUnit("Knight", 100);
                break;
        }
        while (i < amount) {
            army.add(unit);
            i++;
        }
    }
}
