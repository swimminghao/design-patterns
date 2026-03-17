package _01creatation._03factory._02factorymethod;

public class  WulinVanCarFactory extends AbstractCarFactory {
    @Override
    public AbstractCar newCar() {
        return new VanCar();
    }
}
