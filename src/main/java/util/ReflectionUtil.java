package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author 方宗庆
 * @create 2018-02-06 13:23
 */
public final class ReflectionUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     *
     * @return java.lang.Object
     * @author 方宗庆
     * @date 2018/2/6 13:29
     * @since 1.0.0
     */
    public static Object newInstance(Class<?> cls) {
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            LOG.error("创建实例失败", e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     *
     * @return java.lang.Object
     * @author 方宗庆
     * @date 2018/2/6 13:33
     * @since 1.0.0
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            LOG.error("调用方法失败", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量的值
     *
     * @return void
     * @author 方宗庆
     * @date 2018/2/6 13:36
     * @since 1.0.0
     */
    public static void setField(Object obj, Field field, Object value) {
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } catch (Exception e) {
            LOG.error("设置成员变量失败", e);
            throw new RuntimeException(e);
        }
    }
}