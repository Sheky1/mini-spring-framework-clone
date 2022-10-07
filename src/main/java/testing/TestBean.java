package testing;

import framework.annotations.Autowired;
import framework.annotations.Bean;

@Bean(scope = "singleton")
public class TestBean {
    @Autowired(verbose = true)
    private PrototypeBean bean;

    public String myResponse() {
        return "I'm a test bean in testing1 package!";
    }

    public String prototypeCalculator() {
        return "Prototype bean calculated 2+2 to be = " + bean.calculate(2,2);
    }

}
