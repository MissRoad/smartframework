package util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 数组工具类
 *
 * @author 方宗庆
 * @create 2018-02-06 14:20
 */
public final class ArrayUtil {

    /**
     * 判断数组是否为空
     *
     * @return boolean
     * @author 方宗庆
     * @date 2018/2/6 14:23
     * @since 1.0.0
     */
    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }

    /**
     * 判断数组是否非空
     *
     * @return boolean
     * @author 方宗庆
     * @date 2018/2/6 14:23
     * @since 1.0.0
     */
    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }
}