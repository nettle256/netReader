package netReader.ApiController.Kernel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Nettle on 2017/1/4.
 */
@Controller
public class Router {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
