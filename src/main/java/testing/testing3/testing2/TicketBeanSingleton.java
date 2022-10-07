package testing.testing3.testing2;

import framework.annotations.Bean;

@Bean
public class TicketBeanSingleton {

    public TicketBeanSingleton() {
    }
    public String sayHello() {
        return "Hello from the singleton bean!";
    }
}
