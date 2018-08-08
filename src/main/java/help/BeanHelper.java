package help;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import util.ReflectionUtil;

/**
 * Bean助手类
 *
 * @author 方宗庆
 * @create 2018-02-06 13:40
 */
public final class BeanHelper {

    /**
     * 定义bean映射（用于存放bean类与bean实例的映射关系）
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    /**
     * 获取bean映射
     *
     * @param
     * @return java.util.Map<java.lang.Class<?>,java.lang.Object>
     * @author 方宗庆
     * @date 2018/2/6 13:47
     * @since 1.0.0
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取 bean实例
     *
     * @param cls
     * @return T
     * @author 方宗庆
     * @date 2018/2/6 13:48
     * @since 1.0.0
     */
    public static <T> T getBean(Class<T> cls){
        if (!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("不能获取到bean实例"+cls);
        }
        return (T) BEAN_MAP.get(cls);
    }
}