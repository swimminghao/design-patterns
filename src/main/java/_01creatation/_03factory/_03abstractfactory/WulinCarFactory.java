package _01creatation._03factory._03abstractfactory;

/**
 * wulin 汽车集团
 */
public  abstract  class WulinCarFactory extends WulinFactory{
    @Override
    abstract  AbstractCar newCar();
}
