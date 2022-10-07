package testing;

import framework.annotations.Autowired;
import framework.annotations.Service;

@Service
public class UserService {

    @Autowired(verbose = true)
    private TestBean bean;

    public String testBeanText() {
        return "This is UserService and my TestBean said: " + bean.myResponse();
    }

    public String calculate() {
        return bean.prototypeCalculator();
    }

}
