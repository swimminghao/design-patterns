package _01creatation.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

//饿汉式静态常量式
public class EagerInitializedSingleton implements Serializable {
    // 添加serialVersionUID,确保序列化版本一致
    private static final long serialVersionUID = 1L;
    private static final EagerInitializedSingleton instance = new EagerInitializedSingleton();
    // 添加一个标志位
    private  boolean flag = false;

    private EagerInitializedSingleton() {
        // ✅ 直接检查instance，而不是用flag
        if (instance != null) {
            throw new RuntimeException("禁止反射创建实例");
        }
    }

    public static EagerInitializedSingleton getInstance() {
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
