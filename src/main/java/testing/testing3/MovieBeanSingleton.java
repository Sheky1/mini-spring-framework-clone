package testing.testing3;

import framework.annotations.Bean;

@Bean
public class MovieBeanSingleton {

    public MovieBeanSingleton() {
    }

    public String sayHello() {
        return "Hello from the singleton bean!";
    }

}
