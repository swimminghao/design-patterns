package _01creatation._03factory._03abstractfactory;


/**
 * 只造口罩
 */
public class WulinHangZhouMaskFactory extends WulinMaskFactory {

    @Override
    AbstractCar newCar() {
        return null;
    }

    @Override
    AbstractMask newMask() {
        return new CommonMask();
    }
}
