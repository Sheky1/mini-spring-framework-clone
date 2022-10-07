package testing.testing3;

import framework.annotations.*;
import framework.request.Request;
import framework.response.JsonResponse;
import framework.response.Response;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MoviesController {

    @Autowired(verbose = false)
    MoviesService moviesService;
    @Autowired(verbose = false)
    @Qualifier(value = "moviesBeanPrototype")
    MoviesBeanInterface bean;

    public MoviesController() {
    }

    @GET
    @Path(route = "/movies")
    public Response firstGetMethod(Request request) {
        System.out.println("Calling firstGetMethod..");

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("serviceResponse", moviesService.hello());
        responseMap.put("singletonBean", moviesService.helloInside2());
        responseMap.put("prototypeBean", moviesService.helloInside());
        responseMap.put("beanHello", bean.sayHello());
        Response response = new JsonResponse(responseMap);

        return response;
    }

    @GET
    @Path(route = "/movies/inner-get")
    public Response secondGetMethod(Request request) {
        System.out.println("Calling firstGetMethod..");

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("serviceResponse", moviesService.hello());
        responseMap.put("beanHello", bean.sayHello());
        Response response = new JsonResponse(responseMap);

        return response;
    }

    @POST
    @Path(route = "/movies")
    public Response firstPostMethod(Request request) {
        System.out.println("Calling firstGetMethod..");

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("serviceResponse", moviesService.hello());
        responseMap.put("beanHello", bean.sayHello());
        Response response = new JsonResponse(responseMap);

        return response;
    }
}
