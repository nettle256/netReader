package netReader;

import netReader.Controller.User.UserAuthority;
import netReader.JsonModel.JMessage;
import netReader.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "api/test", method = RequestMethod.GET)
    public ResponseEntity<JMessage> test(
    ) {
        return new ResponseEntity<JMessage>(new JMessage("flag is true"), HttpStatus.BAD_REQUEST);
    }
    public static void main(String[] args) {
    }
}
