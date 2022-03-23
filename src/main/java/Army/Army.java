package Army;
import Army.Units.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Constructor for army class.
 * Keeps a list of units, forming an army.
 */
public class Army {
    private String name;
    private List<Unit> units;
    private Random randomUnit;

    /**
     * Constructor of the army class.
     *
     * @param name Name of army.
     */
    public Army(String name) {
        this.name = name;
        units = new ArrayList<>();
        this.randomUnit = new Random();
    }

    /**
     * Method to add single units to army list.
     *
     * @param unit Single unit object.
     */
    public void add(Unit unit) {
        units.add(unit);
    }

    /**
     * Method to add a list of units to army list.
     *
     * @param listOfUnits Input list of type Unit.
     */
    public void addAll(List<Unit> listOfUnits) {
        units.addAll(listOfUnits);
    }

    /**
     * Method to remove Unit object from army list.
     *
     * @param unit Inputted unit object.
     */
    public void remove(Unit unit) throws NullPointerException {
        if (units.contains(unit)) {
            units.remove(unit);
        } else throw new NullPointerException("Unit does not exist.");
    }

    /**
     * Method to return boolean value if army list is empty or has units.
     *
     * @return hasUnits, returns false if army list is empty.
     */
    public boolean hasUnits() {
        return !units.isEmpty();
    }

    /**
     * Method to get random unit from list.
     * Using random number generator, between 0 and size of arrayList.
     *
     * @return Random unit.
     */
    public Unit getRandom() {
        int bound = units.size();
        if (!hasUnits())
            return null;
        else
            return units.get(randomUnit.nextInt(0, bound));
    }

    /**
     * Get name.
     * @return String name of army.
     */
    public String getName() {
        return name;
    }

    /**
     * Method which adds infantry units in its own list of units.
     * @return list of unit type infantry.
     */
    public List<Unit> getInfantryUnits() {
        List<Unit> filteredList = units.stream()
            .filter(Unit -> Unit instanceof InfantryUnit).collect(Collectors.toList());
        return filteredList;
    }

    /**
     * Method which adds Ranged units in its own list of units.
     * @return list of unit type ranged.
     */
    public List<Unit> getRangedUnits() {
        List<Unit> filteredList = units.stream()
            .filter(Unit -> Unit instanceof RangedUnit).collect(Collectors.toList());
        return filteredList;
    }

    /**
     * Method which adds Cavalry in its own list of units.
     * @return list of unit type Cavalry.
     */
    public List<Unit> getCavalryUnits() {
        List<Unit> filteredList = units.stream()
            .filter(Unit -> Unit instanceof CavalryUnit).collect(Collectors.toList());
        return filteredList;
    }

    /**
     * Method which adds Commander units in its own list of units.
     * @return list of unit type Commander.
     */
    public List<Unit> getCommanderUnits() {
        List<Unit> filteredList = units.stream()
            .filter(Unit -> Unit instanceof CommanderUnit).collect(Collectors.toList());
        return filteredList;
    }

    /**
     * Override methods made by IntelliJ.
     *
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
}
