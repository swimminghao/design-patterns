package _01creatation._03factory._03abstractfactory;

/**
 * 分厂：VanCar
 */
public class WulinVanCarFactory extends WulinCarFactory{
    @Override
    AbstractCar newCar() {
        return new VanCar();
    }

    @Override
    AbstractMask newMask() {
        return null;
    }

}
