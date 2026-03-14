package _01creatation.singleton;

import java.io.Serializable;

public enum EnumSingleton implements Serializable {
    INSTANCE;
    // 添加serialVersionUID,确保序列化版本一致
    private static final long serialVersionUID = 1L;

    // 标准getInstance方法 - 无参
    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}
