//package _01creatation.singleton;
//
//
//import com.alibaba.fastjson.JSON;
//
//import java.util.Map;
//
//public class SingletonTest {
//
//    public static void main(String[] args) {
/// /        Person person1 = Person.guiguBoss();
/// /
/// /        Person person2 = Person.guiguBoss();
/// /
/// /        System.out.println(person1 == person2);
//
/// /        Properties properties = System.getProperties();
/// /        System.out.println(properties);
//
//        //获取当前系统的环境变量
//        Map<String, String> getenv = System.getenv();
//        System.out.println(JSON.toJSONString(getenv));
//
//    }
//}
////
package _01creatation.singleton;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(Parameterized.class)
public class SingletonTest {

    private Class<?> singletonClass;
    private String methodName;

    public SingletonTest(Class<?> singletonClass, String methodName) {
        this.singletonClass = singletonClass;
        this.methodName = methodName;
    }

    @Parameterized.Parameters(name = "测试{0}方法")
    public static Object[][] parameters() {
        return new Object[][]{
                // 可以测试不同类型的单例
                {EagerInitializedSingleton.class, "getInstance"},
                {EnumSingleton.class, "getInstance"},
                {StaticInnerClassSingleton.class, "getInstance"},
                {ThreadSafeLazySingleton.class, "getInstance"}
        };
    }

    @Test
    public void testSingletonPattern() throws Exception {
        System.out.println("==========================================");
        System.out.println("测试单例类: " + singletonClass.getSimpleName());
        System.out.println("获取实例方法: " + methodName);
        System.out.println("==========================================");

        // 测试1: 验证多次调用返回同一个实例
        testSingleThreadSingleton();

        // 测试2: 多线程环境下的单例测试
        testMultiThreadSingleton();
    }

    /**
     * 测试单线程环境下是否返回同一个实例
     */
    private void testSingleThreadSingleton() throws Exception {
        System.out.println("\n--- 单线程测试 ---");

        Method getInstanceMethod = singletonClass.getMethod(methodName);

        // 多次获取实例，验证是否相同
        Object instance1 = getInstanceMethod.invoke(null);
        Object instance2 = getInstanceMethod.invoke(null);
        Object instance3 = getInstanceMethod.invoke(null);

        System.out.println("第一次获取实例: " + instance1);
        System.out.println("第二次获取实例: " + instance2);
        System.out.println("第三次获取实例: " + instance3);

        // 验证所有实例都是同一个对象
        assert instance1 == instance2 : "单例失败: instance1和instance2不是同一个对象";
        assert instance2 == instance3 : "单例失败: instance2和instance3不是同一个对象";

        System.out.println("✓ 单线程测试通过: 所有实例都是同一个对象");
    }

    /**
     * 测试多线程环境下的单例
     */
    private void testMultiThreadSingleton() throws Exception {
        System.out.println("\n--- 多线程并发测试 ---");

        Method getInstanceMethod = singletonClass.getMethod(methodName);
        int threadCount = 10;
        int attemptsPerThread = 100;

        // 使用线程池
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        // 使用ConcurrentHashMap来存储所有获取到的实例，确保线程安全
        ConcurrentHashMap<Object, Boolean> instances = new ConcurrentHashMap<>();
        // 使用CountDownLatch确保所有线程几乎同时启动
        CountDownLatch startLatch = new CountDownLatch(1);
        // 使用CountDownLatch等待所有线程完成
        CountDownLatch endLatch = new CountDownLatch(threadCount);

        System.out.printf("启动 %d 个线程，每个线程获取 %d 次实例...\n", threadCount, attemptsPerThread);

        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            executorService.submit(() -> {
                try {
                    startLatch.await(); // 等待开始信号

                    for (int j = 0; j < attemptsPerThread; j++) {
                        Object instance = getInstanceMethod.invoke(null);
                        instances.put(instance, Boolean.TRUE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    endLatch.countDown();
                }
            });
        }

        long startTime = System.currentTimeMillis();
        startLatch.countDown(); // 发送开始信号

        endLatch.await(); // 等待所有线程完成
        long endTime = System.currentTimeMillis();

        executorService.shutdown();

        System.out.println("所有线程执行完成，耗时: " + (endTime - startTime) + "ms");
        System.out.println("总共获取到的不同实例数量: " + instances.size());
        System.out.println("实例详情: " + instances.keySet());

        // 验证是否只有一个实例
        assert instances.size() == 1 : "多线程单例失败: 产生了 " + instances.size() + " 个不同的实例";
        System.out.println("✓ 多线程测试通过: 所有线程获取的都是同一个实例");
    }

    @Test
    public void testReflectionAttack() {
        System.out.println("\n--- 反射攻击测试 ---");

        try {
            // 尝试通过反射创建新的实例
            Constructor<?> constructor = singletonClass.getDeclaredConstructor();
            constructor.setAccessible(true);

            Object instance1 = singletonClass.getMethod(methodName).invoke(null);
            Object instance2 = constructor.newInstance();

            System.out.println("正常获取的实例: " + instance1);
            System.out.println("反射创建的实例: " + instance2);

            if (instance1 == instance2) {
                System.out.println("✓ 反射攻击防御成功: 反射无法创建新实例");
            } else {
                System.out.println("✗ 反射攻击防御失败: 反射创建了新的实例");
            }

        } catch (NoSuchMethodException e) {
            System.out.println("该类没有无参构造器，可能使用了其他防御方式");
        } catch (Exception e) {
            System.out.println("反射攻击被成功防御: " + e.getMessage());
        }
    }

    @Test
    public void testSerialization() throws Exception {
        System.out.println("\n--- 序列化测试 ---");

        // 注意：这个测试需要类实现Serializable接口
        // 如果您的单例类没有实现Serializable，可以跳过这个测试

        try {
            Method getInstanceMethod = singletonClass.getMethod(methodName);
            Object instance1 = getInstanceMethod.invoke(null);

            // 这里应该添加序列化和反序列化的代码
            // 由于Person类没有实现Serializable，我们暂时跳过
            System.out.println("当前类未实现Serializable接口，跳过序列化测试");

        } catch (Exception e) {
            System.out.println("序列化测试跳过: " + e.getMessage());
        }
    }
}