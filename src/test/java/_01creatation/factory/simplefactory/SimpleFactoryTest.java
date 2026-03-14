package _01creatation.factory.simplefactory;

public class SimpleFactoryTest {

    public static void main(String[] args) {

        WuLinSimpleFactory factory = new WuLinSimpleFactory();

        AbstractCar van = factory.newCar("van");
        AbstractCar mini = factory.newCar("mini");
        AbstractCar zz = factory.newCar("zz");
        van.run();
        mini.run();

    }
}
