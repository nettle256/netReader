package netReader.ApiController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Nettle on 2017/1/4.
 */
@Controller
@RequestMapping(path="/template")
public class HtmlTemplate {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getHtmlTemplate(@RequestParam(required = true, value = "uri") String uri) {
        return "ng-template/"+uri;
    }
}
