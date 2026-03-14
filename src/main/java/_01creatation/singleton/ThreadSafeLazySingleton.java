package _01creatation.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * 佟老师只能有一个
 */
public class ThreadSafeLazySingleton implements Serializable {
    private String name;
    private String age;

    //恶汉
    //private volatile static Person instance = new Person();
    /*
    instance = new Person();  // 实际分为3步：

    1. mem = allocate();     // 1. 分配内存空间
    2. ctorSingleton(mem);    // 2. 初始化对象
    3. instance = mem;        // 3. 将引用指向内存

    // 可能被重排序为：
    1. mem = allocate();     // 1. 分配内存空间
    2. instance = mem;        // 2. 将引用指向内存（此时对象未初始化！）
    3. ctorSingleton(mem);    // 3. 初始化对象
     */
    /*
    // 假设没有volatile，线程A和B并发执行

        // 线程A：
        if (instance == null) {  // true
            synchronized(Person.class) {
                if (instance == null) {
                    instance = new Person();  // 可能重排序
                    // 如果重排序，这里instance已经不为null，但对象还未初始化
                }
            }
        }

        // 线程B：
        if (instance == null) {  // false（看到instance不为null）
            // ...
        }
        return instance;  // ❌ 返回了未初始化的对象！
     */
    //懒汉
    //问题的本质：指令重排序只影响已经初始化但未完全构造的对象
    private volatile static ThreadSafeLazySingleton instance;  //饿汉

    // 添加serialVersionUID,确保序列化版本一致
    private static final long serialVersionUID = 1L;

    // 使用一个无法通过反射轻易修改的方式
    private static final Object lock = new Object();
    private static volatile boolean initialized = false;
    //构造器私有，外部不能实例化
    private ThreadSafeLazySingleton() {
//        // 双重检查锁
//        if (!initialized) {
//            synchronized (lock) {
//                if (!initialized) {
//                    initialized = true;
//                } else {
//                    throw new RuntimeException("单例模式正在被攻击，禁止反射创建实例");
//                }
//            }
//        } else {
//            throw new RuntimeException("单例模式正在被攻击，禁止反射创建实例");
//        }
        if (instance != null) {
            throw new RuntimeException("单例模式正在被攻击，禁止反射创建实例");
        }
    }


    //提供给外部的方法
    //1、public static synchronized Person guiguBoss() 锁太大
    //2、双重检查锁+内存可见性（设计模式）
    public static ThreadSafeLazySingleton getInstance() {
        //第一个if (instance == null) - 性能优化
        //目的：
        //避免不必要的同步（性能优化）。当实例已经创建后，直接返回，不需要进入同步块
        if (instance == null) {
            synchronized (ThreadSafeLazySingleton.class) {
                //第二个if (instance == null) - 线程安全
                //目的：
                //防止多次创建实例（线程安全）
                //确保只有一个线程能创建实例
                if (instance == null) {
                    ThreadSafeLazySingleton person = new ThreadSafeLazySingleton();
                    //多线程问题
                    instance = person;
                }
            }
        }
        return instance;
    }

    // 关键方法：在反序列化时直接返回已有的实例
    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    // 可选：防止在反序列化时通过反射创建新对象
    private Object writeReplace() throws ObjectStreamException {
        return instance;
    }

}
