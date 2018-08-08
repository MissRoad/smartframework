package bean;

import java.lang.reflect.Method;

/**
 * 封装Action信息
 *
 * @author 方宗庆
 * @create 2018-02-06 14:33
 */
public class Handler {
    private Class<?> controllerClass;

    /**
     * Action方法
     */
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}