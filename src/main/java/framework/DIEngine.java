package framework;

import framework.annotations.*;
import framework.excpetions.*;
import framework.request.Request;
import framework.response.Response;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class DIEngine {

    Map<Class, Object> controllerMap;
    Map<Class, Object> beanMap;
    List<MethodMap> methodMapper;
    Object controller = null;
    DependencyContainer dependencyContainer;

    public DIEngine() {
        controllerMap = new HashMap<>();
        beanMap = new HashMap<>();
        methodMapper = new ArrayList<>();
        dependencyContainer = new DependencyContainer();
        try {
            setupQualifiers("D:\\Dimitrije\\RAF\\7. semestar\\Napredno veb programiranje\\Projekti\\Domaci 2\\http\\src\\main\\java\\testing", "");
            setupFile("D:\\Dimitrije\\RAF\\7. semestar\\Napredno veb programiranje\\Projekti\\Domaci 2\\http\\src\\main\\java\\testing", "");
            setRoutes();
            System.out.println(controllerMap);
            System.out.println(beanMap);
            System.out.println(methodMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupQualifiers(String pathname, String pathBeginning) {
        File currentFile = new File(pathname);
        if(currentFile.isDirectory()) {
            File[] files = currentFile.listFiles();
            for (File file : files) setupQualifiers(file.getAbsolutePath(), pathBeginning + currentFile.getName() + ".");
        } else {
            setQualifiers(pathname, pathBeginning);
        }
    }

    private void setQualifiers(String pathname, String pathBeginning) {
        try {
            String[] parts = pathname.split("\\\\");
            String fileString = pathBeginning + parts[parts.length-1].replaceAll(".java", "");
            Class newClass = Class.forName(fileString);

            if(newClass.isAnnotationPresent(Qualifier.class)) {
                Qualifier qual = (Qualifier) newClass.getAnnotation(Qualifier.class);
                if(dependencyContainer.getQuilifierMap().containsKey(qual.value())) throw new MultipleQualifierException("Multiple qualifiers!");
                dependencyContainer.add(qual.value(), newClass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Response invokeControllerMethod(Request request) {
        for(MethodMap methodMap: methodMapper) {
            if(request.getMethod().toString().equals(methodMap.getMethodType()) && request.getLocation().equals(methodMap.getRoute())) {
                try {
                    Class controllerClass = methodMap.getControllerClass();
                    Response response = (Response) methodMap.getMethod().invoke(controllerMap.get(controllerClass), request);
                    return response;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void setupFile(String pathname, String pathBeginning) {
        File currentFile = new File(pathname);
        if(currentFile.isDirectory()) {
            File[] files = currentFile.listFiles();
            for (File file : files) setupFile(file.getAbsolutePath(), pathBeginning + currentFile.getName() + ".");
        } else {
            getController(pathname, pathBeginning);
        }
    }

    private void setRoutes() throws MethodSeenException, MultipleMethodException {
        for(Map.Entry<Class, Object> pair: controllerMap.entrySet()) {
            for (Method method: pair.getKey().getDeclaredMethods()) {
                if(method.isAnnotationPresent(GET.class) && method.isAnnotationPresent(POST.class)) throw new MultipleMethodException("CANNOT USE BOTH GET AND POST");
                if(methodSeen(method, pair.getKey())) throw new MethodSeenException("I've seen this method before. " + method.getName());
                if(method.isAnnotationPresent(GET.class)) methodMapper.add(new MethodMap("GET", method.getAnnotation(Path.class).route(), pair.getKey(), method));
                if(method.isAnnotationPresent(POST.class)) methodMapper.add(new MethodMap("POST", method.getAnnotation(Path.class).route(), pair.getKey(), method));
            }
        }
    }

    private boolean methodSeen(Method method, Class key) {
        if(method.isAnnotationPresent(GET.class)) return methodMapper.contains(new MethodMap("GET", method.getAnnotation(Path.class).route(), key, method));
        if(method.isAnnotationPresent(POST.class)) return methodMapper.contains(new MethodMap("POST", method.getAnnotation(Path.class).route(), key, method));
        return false;
    }

    private void getController(String pathname, String pathBeginning) {
        try {
            String[] parts = pathname.split("\\\\");
            String fileString = pathBeginning + parts[parts.length-1].replaceAll(".java", "");
            Class newClass = Class.forName(fileString);

            if(!newClass.isAnnotationPresent(Controller.class)) return;
            if(!controllerMap.containsKey(newClass)) {
                Constructor constructor = newClass.getDeclaredConstructor();
                controller = constructor.newInstance();
                controllerMap.put(newClass, controller);
            }

            setFieldInstances(newClass, controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getInstance(Class neededClass) {
        Object newInstance = null;

        boolean isSingleton = isBeanSingleton(neededClass);
        if(isSingleton && beanMap.containsKey(neededClass)) return beanMap.get(neededClass);
        try {
            Constructor newConstructor = neededClass.getDeclaredConstructor();
            newInstance = newConstructor.newInstance();

            setFieldInstances(neededClass, newInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(isSingleton) beanMap.put(neededClass, newInstance);
        return newInstance;
    }

    public void setFieldInstances(Class neededClass, Object newInstance) throws IllegalAccessException, NoQualifierException, WrongAutowiredException {
        for(Field field: neededClass.getDeclaredFields()) {
            if(field.isAnnotationPresent(Autowired.class)) {
                Class fieldType = field.getType();
                if(fieldType.isInterface() && !field.isAnnotationPresent(Qualifier.class)) throw new NoQualifierException("NO QUALL");
                if(!fieldType.isAnnotationPresent(Bean.class) && !fieldType.isAnnotationPresent(Service.class) &&
                        !fieldType.isAnnotationPresent(Component.class) && !fieldType.isInterface()) throw new WrongAutowiredException("CANNOT AUTOWITRE THIS " + fieldType.getName());
                if(field.isAnnotationPresent(Qualifier.class)) {
                    if(dependencyContainer.getQuilifierMap().get(field.getAnnotation(Qualifier.class).value()) == null) throw new NoQualifierException("NO QUAL");
                    fieldType = dependencyContainer.getQuilifierMap().get(field.getAnnotation(Qualifier.class).value());
                }
                Object fieldInstance = getInstance(fieldType);
                if(field.getAnnotation(Autowired.class).verbose()) System.out.println("Initialized " + fieldType + " " + field.getName() + " in "
                        + neededClass.getName() + " on " + new Date().toString() + " with " + fieldInstance.hashCode());
                field.setAccessible(true);
                field.set(newInstance, fieldInstance);
            }
        }
    }

    private boolean isBeanSingleton(Class neededClass) {
        if(neededClass.isAnnotationPresent(Service.class)) return true;
        if(neededClass.isAnnotationPresent(Bean.class)) {
            Bean beanAnnotation = (Bean) neededClass.getAnnotation(Bean.class);
            if(beanAnnotation.scope().equals("singleton")) return true;
        }
        return false;
    }
}
