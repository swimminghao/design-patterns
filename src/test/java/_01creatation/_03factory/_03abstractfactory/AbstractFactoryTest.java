//package _01creatation.factory.abstractfactory;
//
//
///**
// * 抽象出来。
// *      可以抽象成接口（只有方法），可以抽象成抽象类（有些属性也需要用）
// */
//public class AbstractFactoryTest {
//
//    public static void main(String[] args) {
//
//        //
//        WulinFactory wulinFactory = new WulinWuHanMaskFactory();
//        AbstractCar abstractCar = wulinFactory.newCar();
//
//        AbstractMask abstractMask = wulinFactory.newMask();
//        abstractMask.protectedMe();
//
//
//        wulinFactory = new WulinHangZhouMaskFactory();
//        AbstractMask abstractMask1 = wulinFactory.newMask();
//        abstractMask1.protectedMe();
//    }
//}
package _01creatation._03factory._03abstractfactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class AbstractFactoryTest {
    private Class<? extends WulinFactory> factoryClass;
    private String factoryName;
    private String expectedCarType;
    private String expectedMaskType;

    public AbstractFactoryTest(Class<? extends WulinFactory> factoryClass,
                               String factoryName,
                               String expectedCarType,
                               String expectedMaskType) {
        this.factoryClass = factoryClass;
        this.factoryName = factoryName;
        this.expectedCarType = expectedCarType;
        this.expectedMaskType = expectedMaskType;
    }

    @Parameterized.Parameters(name = "{1}")
    public static Object[][] parameters() {
        return new Object[][] {
                { WulinWuHanMaskFactory.class, "武汉口罩工厂", "武汉汽车", "武汉口罩" },
                { WulinHangZhouMaskFactory.class, "杭州口罩工厂", "杭州汽车", "杭州口罩" },
                { WulinCarFactory.class, "汽车工厂", "汽车", null },
                { WulinMaskFactory.class, "口罩工厂", null, "口罩" }
        };
    }

    @Test
    public void testCreateProduct() throws ReflectiveOperationException {
        System.out.println("\n========== 测试工厂: " + factoryName + " ==========");

        WulinFactory factory = factoryClass.newInstance();

        // 测试创建汽车
        if (expectedCarType != null) {
            AbstractCar car = factory.newCar();
            System.out.println("创建汽车: " + car.getClass().getSimpleName());
            car.run(); // 假设有run方法
        }

        // 测试创建口罩
        if (expectedMaskType != null) {
            AbstractMask mask = factory.newMask();
            System.out.println("创建口罩: " + mask.getClass().getSimpleName());
            mask.protectedMe();
        }

        // 测试汽车和口罩的交互
        if (expectedCarType != null && expectedMaskType != null) {
            AbstractCar car = factory.newCar();
            AbstractMask mask = factory.newMask();

            // 这里可以添加更多的交互测试
            System.out.println("工厂同时生产汽车和口罩");
        }

        printSeparator(50);
    }

    @Test
    public void testMultipleFactories() throws ReflectiveOperationException {
        // 测试多个工厂的组合使用
        List<Class<? extends WulinFactory>> factories = Arrays.asList(
                WulinWuHanMaskFactory.class,
                WulinHangZhouMaskFactory.class,
                WulinCarFactory.class,
                WulinMaskFactory.class
        );

        System.out.println("\n========== 多工厂批量测试 ==========");
        for (Class<? extends WulinFactory> factoryClass : factories) {
            WulinFactory factory = factoryClass.newInstance();
            System.out.println("工厂类型: " + factoryClass.getSimpleName());

            // 尝试创建产品
            tryCreateProduct(factory, "Car");
            tryCreateProduct(factory, "Mask");

            printSeparator(30);
        }
        printSeparator(100);
    }

    private void tryCreateProduct(WulinFactory factory, String productType) {
        try {
            if ("Car".equals(productType)) {
                AbstractCar car = factory.newCar();
                System.out.println("  - 成功创建汽车: " + car.getClass().getSimpleName());
            } else if ("Mask".equals(productType)) {
                AbstractMask mask = factory.newMask();
                System.out.println("  - 成功创建口罩: " + mask.getClass().getSimpleName());
            }
        } catch (Exception e) {
            System.out.println("  - 创建" + productType + "失败: " + e.getMessage());
        }
    }

    private void printSeparator(int length) {
        System.out.println(new String(new char[length]).replace('\0', '-'));
    }
}