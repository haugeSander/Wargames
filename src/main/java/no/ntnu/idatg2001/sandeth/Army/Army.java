package no.ntnu.idatg2001.sandeth.Army;
import no.ntnu.idatg2001.sandeth.Army.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import no.ntnu.idatg2001.sandeth.Army.Units.CavalryUnit;
import no.ntnu.idatg2001.sandeth.Army.Units.CommanderUnit;
import no.ntnu.idatg2001.sandeth.Army.Units.InfantryUnit;
import no.ntnu.idatg2001.sandeth.Army.Units.RangedUnit;
import no.ntnu.idatg2001.sandeth.Army.Units.Unit;

/**
 * Constructor for army class.
 * Keeps a list of units, forming an army.
 */
public class Army {
    private String name;
    private List<Unit> units;
    private final Random randomUnit;

    /**
     * Constructor of the army class.
     * @param name Name of army.
     */
    public Army(String name) {
        this.name = name;
        units = new ArrayList<>();
        this.randomUnit = new Random();
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
    public void remove(Unit unit) throws NullPointerException {
        if (units.contains(unit)) {
            units.remove(unit);
        } else
            throw new NullPointerException("Unit does not exist.");
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
        if (!hasUnits())
            return null;
        else
            return units.get(randomUnit.nextInt(bound));
    }

    /**
     * Sets name of Army.
     * @param name String of name.
     */
    public void setName(String name) throws IllegalArgumentException {
        if (name.isEmpty() || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or empty.");
        else
            this.name = name;
    }

    public void setUnits(List<Unit> units) throws IllegalArgumentException {
        if (units == null) {
            throw new IllegalArgumentException("List may not be null or empty!");
        } else
            this.units = units;
    }

    /**
     * Get name.
     * @return String name of army.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the units list from Army.
     * @return List of units.
     */
    public List<Unit> getUnits() {
        return units;
    }


    /**
     * Method which adds infantry units in its own list of units.
     * @return list of unit type infantry.
     */
    public List<Unit> getInfantryUnits() {
        return units.stream()
            .filter(InfantryUnit.class::isInstance).collect(Collectors.toList());
    }

    /**
     * Method which adds Ranged units in its own list of units.
     * @return list of unit type ranged.
     */
    public List<Unit> getRangedUnits() {
        return units.stream()
            .filter(RangedUnit.class::isInstance).collect(Collectors.toList());
    }

    /**
     * Method which adds Cavalry in its own list of units.
     * @return list of unit type Cavalry.
     */
    public List<Unit> getCavalryUnits() {
        return units.stream()
            .filter(u -> u.getClass().equals(CavalryUnit.class)).collect(Collectors.toList());
    }

    /**
     * Method which adds Commander units in its own list of units.
     * @return list of unit type Commander.
     */
    public List<Unit> getCommanderUnits() {
        return units.stream()
            .filter(CommanderUnit.class::isInstance).collect(Collectors.toList());
    }

    /**
     * Override equals methods.
     * @return Boolean if object is the same.
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * toString override for armies.
     * @return String information about army.
     */
    @Override
    public String toString() {
        return name + ", forces left: " + units.size();
    }
}
