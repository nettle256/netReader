package netReader.ApiController.User;

import netReader.Constant.MD5;
import netReader.JsonModel.JMessage;
import netReader.JsonModel.JUser;
import netReader.Model.User;
import netReader.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<JMessage> login(
            @RequestBody JUser loginUser,
            ModelMap model
    )   {
        if (model.containsAttribute("currentUser"))
            return new ResponseEntity<JMessage>(new JMessage("Error: Already login"), HttpStatus.FORBIDDEN);

        User user = userRepository.findByUsername(loginUser.getUsername());
        if (user == null || user.getDeleted())
            return new ResponseEntity<JMessage>(new JMessage("Error: Username does not exist"), HttpStatus.FORBIDDEN);
        if (user.getNuked())
            return new ResponseEntity<JMessage>(new JMessage("Error: User has been nuked"), HttpStatus.FORBIDDEN);

        try {
            if (!user.getPassword().equals(MD5.md5(loginUser.getPassword())))
                return new ResponseEntity<JMessage>(new JMessage("Error: Password is not correct"), HttpStatus.FORBIDDEN);
        }   catch (Exception e) {
            return new ResponseEntity<JMessage>(new JMessage("Error: System error"), HttpStatus.FORBIDDEN);
        }

        model.addAttribute("currentUser", user);
        return new ResponseEntity<JMessage>(new JMessage("OK: Login succeeded"), HttpStatus.OK);
    }

    @RequestMapping(value = "api/logout", method = RequestMethod.GET)
    public @ResponseBody void logout(
            SessionStatus status
    )   {
        status.setComplete();
    }
}
