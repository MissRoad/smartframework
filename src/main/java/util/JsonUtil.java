package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSON工具类
 *
 * @author 方宗庆
 * @create 2018-02-06 16:42
 */
public final class JsonUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将pojo转为json
     *
     * @return java.lang.String
     * @author 方宗庆
     * @date 2018/2/6 16:44
     * @since 1.0.0
     */
    public static <T> String toJson(T obj) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            LOG.error("pojo转为json失败", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将pojo转为json
     *
     * @return java.lang.String
     * @author 方宗庆
     * @date 2018/2/6 16:44
     * @since 1.0.0
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch (Exception e) {
            LOG.error("json转为pojo失败", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }
}