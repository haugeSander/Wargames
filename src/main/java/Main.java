import Army.Army;
import Army.Units.*;
import Simulation.Battle;

public class Main {

    public static void main(String[] args) {
        init();
    }

    /**
     * Initializer.
     * Creates units, armies and a battle instance.
     * All is added and simulate() is run.
     */
    private static void init() {


        for (int i = 0; i < 10; i++) {
            Unit CavalryBlue = new CavalryUnit("Alfa Blue", 20);
            Unit CavalryRed = new CavalryUnit("Alfa Red", 20);
            Unit InfantryBlue = new InfantryUnit("Bravo Blue", 10);
            Unit InfantryRed = new InfantryUnit("Bravo Red", 10);
            Unit RangerBlue = new RangedUnit("Charlie Blue", 10);
            Unit RangerRed = new RangedUnit("Charlie Red", 10);

            Army blueArmy = new Army("Ukraina");
            Army redArmy = new Army("Russland");

            blueArmy.add(CavalryBlue);
            blueArmy.add(InfantryBlue);
            blueArmy.add(RangerBlue);

            redArmy.add(CavalryRed);
            redArmy.add(InfantryRed);
            redArmy.add(RangerRed);

            Battle WW1 = new Battle(blueArmy, redArmy);
            System.out.println(WW1.simulate());
        }
    }
}
