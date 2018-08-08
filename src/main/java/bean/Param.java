package bean;

import java.util.Map;

import util.CastUtil;

/**
 * 请求参数对象
 *
 * @author 方宗庆
 * @create 2018-02-06 15:28
 */
public class Param {
    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 根据参数名获取long型参数值
     */
    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    /**
     * 获取所有字段信息
     */
    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}