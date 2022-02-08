package Army;
import Army.Units.*;

import java.util.ArrayList;
import java.util.List;

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
     * @param units Input list of type Unit.
     */
    public void addAll(List<Unit> units) {
        units.addAll(this.units);
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
        return super.toString();
    }
}
