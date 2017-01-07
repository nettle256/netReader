package netReader.ApiController.Kernel;

import netReader.Controller.User.UserAuthority;
import netReader.Model.User;
import netReader.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.xml.ws.Action;

/**
 * Created by Nettle on 2017/1/4.
 */
@Controller
@SessionAttributes("currentUser")
public class Router {

//    @Autowired
//    private UserDAO userDAO;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String index(
            @SessionAttribute(value="currentUser", required=false) User currentUser
//            ModelMap model
    )   {
//        if (currentUser == null) {
//            User redisUser = userDAO.getCurrentUser();
//            if (redisUser != null) {
//                redisUser = userRepository.findById(redisUser.getId());
//                model.addAttribute("currentUser", redisUser);
//                return "index";
//            }
//        }   else {
            if (UserAuthority.checkCurrentUserAuthority(UserAuthority.USER, currentUser))
                return "index";
//        }
        return "sign";
    }
}
