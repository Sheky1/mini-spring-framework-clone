package testing;

import framework.annotations.*;
import framework.request.Request;
import framework.response.JsonResponse;
import framework.response.Response;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    int test = 0;
    @Autowired(verbose = false)
    private UserService service;
    @Autowired(verbose = false)
    private PrototypeBean prototypeBean;

    public UserController() {
    }

//    @GET
//    @Path(route = "/testing")
//    public void prviGetCopy(Request request) {
//        System.out.println(request);
//        System.out.println(service.izracunaj());
//    }

    @GET
    @Path(route = "/testing")
    public Response firstGetMethod(Request request) {
        System.out.println("Calling firstGetMethod..");

        String serviceResponse = service.testBeanText();
        String prototypeBeanResponse = prototypeBean.prototypeBeanText();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("serviceResponse", serviceResponse);
        responseMap.put("prototypeBeanResponse", prototypeBeanResponse);
        responseMap.put("beanCalculated", service.calculate());
        Response response = new JsonResponse(responseMap);

        return response;
    }

    @GET
    @Path(route = "/testing/inner-get")
    public Response secondGetMethod(Request request) {
        System.out.println("Calling secondGetMethod..");

        String prototypeBeanResponse = prototypeBean.prototypeBeanText();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("quickMaths", 2+2);
        responseMap.put("prototypeBeanResponse", prototypeBeanResponse);
        Response response = new JsonResponse(responseMap);

        return response;
    }

    @POST
    @Path(route = "/testing")
    public Response firstPostMethod(Request request) {
        System.out.println("Calling firstPostMethod..");

        String prototypeBeanResponse = prototypeBean.prototypeBeanText();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("postText", "Hello from post!");
        responseMap.put("prototypeBeanResponse", prototypeBeanResponse);
        Response response = new JsonResponse(responseMap);

        return response;
    }
}
