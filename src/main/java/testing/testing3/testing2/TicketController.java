package testing.testing3.testing2;

import framework.annotations.*;
import framework.request.Request;
import framework.response.JsonResponse;
import framework.response.Response;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TicketController {

    @Autowired(verbose = true)
    TicketService ticketService;

    @Autowired(verbose = true)
    TicketBeanSingleton singleton;

    @Autowired(verbose = true)
    TicketBeanPrototype prototype;

    public TicketController() {}

    @GET
    @Path(route = "/tickets")
    public Response firstGetMethod(Request request) {
        System.out.println("Calling firstGetMethod..");

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("serviceResponse", ticketService.hello());
        responseMap.put("singletonBean", ticketService.helloInside2());
        responseMap.put("prototypeBean", ticketService.helloInside());
        responseMap.put("beanHello", singleton.sayHello());
        Response response = new JsonResponse(responseMap);

        return response;
    }

    @GET
    @Path(route = "/tickets/inner-get")
    public Response secondGetMethod(Request request) {
        System.out.println("Calling firstGetMethod..");

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("serviceResponse", ticketService.hello());
        responseMap.put("beanHello", singleton.sayHello());
        Response response = new JsonResponse(responseMap);

        return response;
    }

    @POST
    @Path(route = "/tickets")
    public Response firstPostMethod(Request request) {
        System.out.println("Calling firstGetMethod..");

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("serviceResponse", ticketService.hello());
        responseMap.put("beanHello", singleton.sayHello());
        Response response = new JsonResponse(responseMap);

        return response;
    }

}
