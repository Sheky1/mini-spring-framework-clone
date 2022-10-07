package testing;

import framework.annotations.Bean;

@Bean(scope = "prototype")
public class PrototypeBean {

    public String prototypeBeanText() {
        return "I am the prototype bean in testing!";
    }

    public int calculate(int first, int second) {
        return first + second;
    }

}
