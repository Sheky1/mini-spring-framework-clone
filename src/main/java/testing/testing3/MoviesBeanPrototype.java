package testing.testing3;

import framework.annotations.Bean;
import framework.annotations.Qualifier;

@Bean(scope = "prototype")
@Qualifier(value = "moviesBeanPrototype")
public class MoviesBeanPrototype implements MoviesBeanInterface {

    public MoviesBeanPrototype() {
    }

    @Override
    public String sayHello() {
        return "Hello from the bean that implements the interface";
    }
}
