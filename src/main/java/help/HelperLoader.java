package help;

import util.ClassUtil;

/**
 * 加载相应的Helper类
 *
 * @author 方宗庆
 * @create 2018-02-06 15:11
 */
public final class HelperLoader {
    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName(), true);
        }
    }
}