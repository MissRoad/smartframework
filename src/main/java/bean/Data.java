package bean;

/**
 * 返回数据对象
 *
 * @author 方宗庆
 * @create 2018-02-06 15:36
 */
public class Data {

    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }

}