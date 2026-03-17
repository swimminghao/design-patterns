package _01creatation._03factory._02factorymethod;

/**
 * RacingCar分厂
 */
public class WulinRacingCarFactory extends AbstractCarFactory{
    @Override
    public AbstractCar newCar() {
        return new RacingCar();
    }
}
