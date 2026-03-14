package _01creatation.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

public class StaticInnerClassSingleton implements Serializable {
    // 添加serialVersionUID,确保序列化版本一致
    private static final long serialVersionUID = 1L;

    private static class Holder {
        private static final StaticInnerClassSingleton instance = new StaticInnerClassSingleton();
    }

    private StaticInnerClassSingleton() {
        // 防御反射攻击
        if (Holder.instance != null) {
            throw new RuntimeException("禁止反射创建实例");
        }
    }


    public static StaticInnerClassSingleton getInstance() {
        return Holder.instance;
    }

    // 关键方法：在反序列化时直接返回已有的实例
    private Object readResolve() throws ObjectStreamException {
        return Holder.instance;
    }

    // 可选：防止在反序列化时通过反射创建新对象
    private Object writeReplace() throws ObjectStreamException {
        return Holder.instance;
    }
}
