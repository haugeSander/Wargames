package Army;
import Army.Units.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Constructor for army class.
 * Keeps a list of units, forming an army.
 */
public class Army {
    private String name;
    List<Unit> units;

    /**
     * Constructor of the army class.
     * @param name Name of army.
     */
    public Army(String name) {
        this.name = name;
        units = new ArrayList<>();
    }

    /**
     * Method to add single units to army list.
     * @param unit Single unit object.
     */
    public void add(Unit unit) {
        units.add(unit);
    }

    /**
     * Method to add a list of units to army list.
     * @param listOfUnits Input list of type Unit.
     */
    public void addAll(List<Unit> listOfUnits) {
        units.addAll(listOfUnits);
    }

    /**
     * Method to remove Unit object from army list.
     * @param unit Inputted unit object.
     */
    public void remove(Unit unit) {
        boolean isFound = false;
        int i = 0;

        while (units.size() > i && !isFound) {
            Unit tempUnit = units.get(i);

            if (tempUnit.equals(unit)) {
                units.remove(tempUnit);
                isFound = true;
            }
            i++;
        }
    }

    /**
     * Method to return boolean value if army list is empty or has units.
     * @return hasUnits, returns false if army list is empty.
     */
    public boolean hasUnits() {
        return !units.isEmpty();
    }

    /**
     * Method to get random unit from list.
     * Using random number generator, between 0 and size of arrayList.
     * @return Random unit.
     */
    public Unit getRandom() {
        int bound = units.size();
        int randomUnit = new Random().nextInt(0, bound);
        if (!hasUnits())
            return null;
        else
            return units.get(randomUnit);
    }

    /**
     * Override methods made by IntelliJ.
     * @return Hashcode of object.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return name + ", forces left: " + units.size();
    }

    public String getName() {
        return name;
    }
}
