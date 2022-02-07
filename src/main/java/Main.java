import Units.CavalryUnit;
import Units.Unit;

public class Main {

    public static void main(String[] args) {
        Unit unitTest = new CavalryUnit("Sece", 10);
        Unit unitTest2 = new CavalryUnit("Sece", 10);
        unitTest.attack(unitTest2);
        System.out.println(unitTest2);
    }
}
