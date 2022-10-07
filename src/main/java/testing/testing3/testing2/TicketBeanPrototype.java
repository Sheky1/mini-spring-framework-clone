package testing.testing3.testing2;

import framework.annotations.Bean;

@Bean(scope = "prototype")
public class TicketBeanPrototype {

    public TicketBeanPrototype() {
    }
    public String sayHello() {
        return "Hello from the bean that implements the interface";
    }
}
