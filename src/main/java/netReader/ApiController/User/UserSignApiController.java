package netReader.ApiController.User;

import netReader.Constant.MD5;
import netReader.Model.User;
import netReader.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Nettle on 2017/1/5.
 */
@Controller
@SessionAttributes("currentUser")
public class UserSignApiController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public @ResponseBody String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse response,
            ModelMap model
    )   {
        if (model.containsAttribute("currentUser")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Error: Already login";
        }
        User user = userRepository.findByUsername(username);
        if (user == null || user.getDeleted()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Error: Username does not exist";
        }
        if (user.getNuked()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Error: User has been nuked";
        }
        try {
            if (!user.getPassword().equals(MD5.md5(password))) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return "Error: Password is not correct";
            }
        }   catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Error: System error";
        }
        model.addAttribute("currentUser", user);
        return "OK: Login succeeded";
    }

    @RequestMapping(value = "api/logout", method = RequestMethod.GET)
    public @ResponseBody void logout(
            SessionStatus status
    )   {
        status.setComplete();
    }
}
