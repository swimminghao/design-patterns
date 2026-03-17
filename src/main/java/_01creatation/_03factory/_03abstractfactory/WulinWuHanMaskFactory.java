package _01creatation._03factory._03abstractfactory;

/**
 * 分厂：否则口罩
 */
public class WulinWuHanMaskFactory  extends WulinMaskFactory{

    @Override
    AbstractCar newCar() {
        return null;
    }

    @Override
    AbstractMask newMask() {
        return new N95Mask();
    }
}
