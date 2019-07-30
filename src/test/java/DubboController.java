import com.sjw.api4j.annotation.ApiTag;
import com.sjw.api4j.enums.ProtocolEnum;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

/**
 * @author shijiawei
 * @version DubboController.java -> v 1.0
 * @date 2019/7/28
 * 这是dubbo controller class的注解
 */
@Path("dubbo-controller")
@ApiTag(value = "kkkk" ,protocol = ProtocolEnum.DUBBO)
public interface DubboController {

    /**
     * this is get Test note.
     * @param id
     * @param name
     * @return
     */
    @GET
    @Path("/getTest")
    List<Movie> getTest(Integer id, String name);


    /**
     * this is get test2 note.
     * @param id
     * @return
     */
    @GET
    @Path("getTest2")
    List<String> getTest2(@RequestParam(value = "ID") Integer id);

    @POST
    @Path("postTest1")
    List<Movie> postTest1(ParamDemo paramDemo);
}
