package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 编码与解码操作工具类
 *
 * @author 方宗庆
 * @create 2018-02-06 16:36
 */
public final class CodecUtil {
    private static final Logger LOG = LoggerFactory.getLogger(CodecUtil.class);

    /**
     * 将url编码
     *
     * @return java.lang.String
     * @author 方宗庆
     * @date 2018/2/6 16:38
     * @since 1.0.0
     */
    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("编码失败", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将url编码
     *
     * @return java.lang.String
     * @author 方宗庆
     * @date 2018/2/6 16:38
     * @since 1.0.0
     */
    public static String decodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("解码失败", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}