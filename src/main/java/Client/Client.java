package Client;

import Army.Army;
import Army.Units.*;
import Simulation.Battle;

import java.util.Locale;
import java.util.Scanner;

public class Client {
    Scanner sc;
    Battle battleRoyale;

    public Client() {
        sc = new Scanner(System.in);
    }

    public void menu() {
        String message = "--* Army battle simulator *--" + '\n' +
                "1. Make army and fill." + '\n' +
                "2. Remove army or units." + '\n' +
                "3. OUR BATTLE WILL BE LEGENDARY!" + '\n' +
                "4. Im out." + '\n';
        System.out.println(message);

        int menuChoice = validator(sc.nextLine());
        boolean finished = false;
        boolean satisfied = false;

        do {
            switch (menuChoice) {
                case 1:
                    System.out.println("Name of army: ");
                    String nameOfArmy=sc.nextLine();
                    Army army = new Army(nameOfArmy);

                    do {
                        System.out.println("Type of unit to add: ");
                        String unitToAdd = sc.nextLine();
                        System.out.println("Amount: ");
                        int amountToAdd = validator(sc.nextLine());
                        fillArmy(army, unitToAdd, amountToAdd);

                        System.out.println("Add more?(y/n): ");
                        if(sc.nextLine().equalsIgnoreCase("y")) {satisfied = true;}
                    }while (!satisfied);
                    break;
                case 2:
                    System.out.println("Not implemented");
                    break;
                case 3:
                    break;
                case 4:
                    finished = true;
                    break;
            }
        } while (!finished);

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
        }

        while (i < amount) {
            army.add(unit);
            i++;
        }
    }
}
