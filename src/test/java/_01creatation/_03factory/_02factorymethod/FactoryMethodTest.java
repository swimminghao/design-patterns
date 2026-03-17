package _01creatation._03factory._02factorymethod;

public class FactoryMethodTest {

    public static void main(String[] args) {
        AbstractCarFactory carFactory = new WulinRacingCarFactory();
        AbstractCar abstractCar = carFactory.newCar();
        abstractCar.run();


        carFactory = new WulinVanCarFactory();
        AbstractCar abstractCar1 = carFactory.newCar();

        abstractCar1.run();
    }
}
