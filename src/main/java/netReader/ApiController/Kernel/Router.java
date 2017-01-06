package netReader.ApiController.Kernel;

import netReader.Controller.User.UserAuthority;
import netReader.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * Created by Nettle on 2017/1/4.
 */
@Controller
public class Router {
    @RequestMapping("/")
    public String index(
            @SessionAttribute(value="currentUser", required=false) User currentUser
    )   {
        if (UserAuthority.checkCurrentUserAuthority(UserAuthority.USER, currentUser))
            return "index";
        else return "sign";
    }
}
