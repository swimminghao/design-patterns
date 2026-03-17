package _01creatation._03factory._03abstractfactory;


/**
 * 具体工厂。只造车
 */
public class WulinRacingCarFactory extends WulinCarFactory {
    @Override
    AbstractCar newCar() {
        return new RacingCar();
    }

    @Override
    AbstractMask newMask() {
        return null;
    }
}
