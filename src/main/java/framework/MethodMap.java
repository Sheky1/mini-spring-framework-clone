package framework;

import java.lang.reflect.Method;

public class MethodMap {

    private String methodType;
    private String route;
    private Class controllerClass;
    private Method method;

    public MethodMap(String methodType, String route, Class controllerClass, Method method) {
        this.methodType = methodType;
        this.route = route;
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Class getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class controllerClass) {
        this.controllerClass = controllerClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodMap that = (MethodMap) o;
        return methodType.equals(that.methodType) && route.equals(that.route);
    }
}
