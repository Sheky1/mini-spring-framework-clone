package testing.testing3;

import framework.annotations.Autowired;
import framework.annotations.Qualifier;
import framework.annotations.Service;

@Service
public class MoviesService {

    @Autowired(verbose = true)
    @Qualifier(value = "moviesBeanPrototype")
    MoviesBeanInterface beanPrototype;
    @Autowired(verbose = true)
    MovieBeanSingleton beanSingleton;

    public MoviesService() {
    }

    public String hello() {
        return "Hello from MoviesService!";
    }

    public String helloInside() {
        return "Hello from Movies service and " + beanPrototype.sayHello();
    }
    public String helloInside2() {
        return "Hello from Movies service and " + beanSingleton.sayHello();
    }

}
