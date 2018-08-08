package help;

import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

import annotation.Inject;
import util.CollectionUtil;
import util.ReflectionUtil;

/**
 * 依赖注入助手类
 *
 * @author 方宗庆
 * @create 2018-02-06 13:53
 */
public class IocHelper {
    static {
        //获取所有Bean类与Bean实例之间的映射关系（简称Bean Map）
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            //循环遍历beanMap
            for (Map.Entry<Class<?>, Object> benEntry : beanMap.entrySet()) {
                //从BeanMap中获取Bean类与Bean实例
                Class<?> beanClass = benEntry.getKey();
                Object beanInstance = benEntry.getValue();
                //获取Bean类定义的所有成员变量（简称 Bean Field）
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    //遍历bean field
                    for (Field beanFiled : beanFields) {
                        //判断当前BeanField是否带有Inject注解
                        if (beanFiled.isAnnotationPresent(Inject.class)) {
                            //在Bean Map中获取Bean Field对应的实例
                            Class<?> beanFiledClass = beanFiled.getType();
                            Object beanFieldInstance = beanMap.get(beanFiledClass);
                            if (beanFieldInstance != null) {
                                //通过反射初始化BeanField的值
                                ReflectionUtil.setField(beanInstance, beanFiled, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}