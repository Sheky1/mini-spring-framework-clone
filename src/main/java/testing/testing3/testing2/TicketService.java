package testing.testing3.testing2;

import framework.annotations.Autowired;
import framework.annotations.Service;

@Service
public class TicketService {

    @Autowired(verbose = true)
    TicketBeanPrototype prototype;
    @Autowired(verbose = true)
    TicketBeanSingleton singleton;

    public TicketService() {}

    public String hello() {
        return "Hello from MoviesService!";
    }

    public String helloInside() {
        return "Hello from Movies service and " + prototype.sayHello();
    }
    public String helloInside2() {
        return "Hello from Movies service and " + singleton.sayHello();
    }
}
