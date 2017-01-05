package netReader;

import netReader.Controller.User.UserAuthority;
import netReader.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Nettle on 2017/1/5.
 */

@Controller
//@SessionAttributes("currentUser")
public class Test {

    @Autowired
    private UserAuthority userAuthority;

    @RequestMapping(value = "api/test", method = RequestMethod.POST)
    public @ResponseBody String test(
            @RequestParam(value="requestAuthority") Long requestAuthority,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    ) {
        if (userAuthority.checkCurrentUserAuthority(requestAuthority, currentUser)) return "Passed";
        return "Failed";
}
    public static void main(String[] args) {
    }
}
