package util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 *
 * @author 方宗庆
 * @create 2018-02-01 18:54
 */
public final class CollectionUtil {
    /**
     * 判断collection是否为空
     */
    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断collection是否非空
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /***
     * 判断Map是否为空
     *
     * @param map
     * @return boolean
     * @author 方宗庆
     * @date 2018/2/1 19:00
     * @since 1.0.0
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return MapUtils.isEmpty(map);
    }

    /***
     *
     *判断Map是否非空
     * @param map
     * @return boolean
     * @author 方宗庆
     * @date 2018/2/1 19:03
     * @since 1.0.0
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
}