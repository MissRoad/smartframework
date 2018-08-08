package help;

import java.util.HashSet;
import java.util.Set;

import annotation.Controller;
import annotation.Service;
import util.ClassUtil;

/**
 * 类操作助手
 *
 * @author 方宗庆
 * @create 2018-02-06 13:06
 */
public final class ClassHelper {

    /**
     * 定义类集合（用于存放所加载的类）
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包下面的所有类
     *
     * @return java.util.Set<java.lang.Class<?>>
     * @author 方宗庆
     * @date 2018/2/6 13:11
     * @since 1.0.0
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包下面的所有service类
     *
     * @return java.util.Set<java.lang.Class<?>>
     * @author 方宗庆
     * @date 2018/2/6 13:12
     * @since 1.0.0
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包下面的所有Controller类
     *
     * @return java.util.Set<java.lang.Class<?>>
     * @author 方宗庆
     * @date 2018/2/6 13:12
     * @since 1.0.0
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下所有bean类（包括：service、controller等）
     *
     * @return java.util.Set<java.lang.Class<?>>
     * @author 方宗庆
     * @date 2018/2/6 13:17
     * @since 1.0.0
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }
}