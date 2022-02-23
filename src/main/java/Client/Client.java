package Client;

import Army.Army;
import Army.Units.*;
import Simulation.Battle;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Client {
    Scanner sc;
    Battle battleRoyale;
    ArrayList<Army> armies;

    public Client() {
        sc = new Scanner(System.in);
        armies = new ArrayList<>();
    }

    public void menu() {
        fillWithPresets();
        String message = "--* Army battle simulator *--" + '\n' +
                "1. Make army and fill." + '\n' +
                "2. Remove army or units." + '\n' +
                "3. OUR BATTLE WILL BE LEGENDARY!" + '\n' +
                "4. Im out." + '\n';

        boolean finished = false;
        int menuChoice;

        do {
            System.out.println(message);
            menuChoice = validator(sc.nextLine());

            switch (menuChoice) {
                case 1:
                    makeArmyFromConsole();
                    break;
                case 2:
                    System.out.println("Not implemented");
                    break;
                case 3:
                    startBattleSimulation();
                    break;
                case 4:
                    finished = true;
                    break;
            }
        } while (!finished);
    }

    private void fillWithPresets() {
        Army preset1 = new Army("Preset1");
        Army preset2 = new Army("Preset2");
        fillArmy(preset1, "infantry", 100);
        fillArmy(preset2, "infantry", 100);

        armies.add(preset1);
        armies.add(preset2);
    }

    private void makeArmyFromConsole() {
        boolean satisfied = false;

        System.out.print("Name of army: ");
        String nameOfArmy = sc.nextLine();
        Army army = new Army(nameOfArmy);
        armies.add(army);

        do {
            System.out.print("Type of unit to add: ");
            String unitToAdd = sc.nextLine();
            if (unitToAdd.equalsIgnoreCase("infantry") || unitToAdd.equalsIgnoreCase("commander")
            || unitToAdd.equalsIgnoreCase("cavalry") || unitToAdd.equalsIgnoreCase("ranged")) {
                System.out.print("Amount: ");
                int amountToAdd = validator(sc.nextLine());
                fillArmy(army, unitToAdd, amountToAdd);
            } else {
                System.err.println("No such unit exist, try again.");
                continue;
            }

            System.out.print("Add more?(y/n): ");
            String choice = sc.nextLine();

            if(choice.equalsIgnoreCase("n")) {
                satisfied = true;
            }
        }while (!satisfied);
        System.out.println("Successfully added.\n");
    }

    private void startBattleSimulation() {
        if (armies.isEmpty() || armies.size() == 1)
            System.out.println("No armies to battle.");
        else {
            battleRoyale = new Battle(armies.get(0), armies.get(1));
            System.out.println(battleRoyale.simulate());
        }
    }

    private int validator(String stringToConvert) {
        boolean isValid = false;
        int valid = 0;

        while (!isValid) {
            try {
                valid = Integer.parseInt(stringToConvert);
                isValid = true;
            } catch (IllegalArgumentException e) {
                System.err.println("Illegal argument");
            }
        } return valid;
    }

    /**
     * Method to make an army of specified type.
     * @param army Army object to fill with units.
     * @param typeOfUnit String type of what unit you want.
     * @param amount Int amount of units to add in army.
     */
    private void fillArmy(Army army, String typeOfUnit, int amount) {
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
            default:
                System.err.println("No possible unit selected, choose from: Infantry, Commander, " +
                        "Ranged and Cavalry!");
        }

        while (i < amount) {
            army.add(unit);
            i++;
        }
    }
}
