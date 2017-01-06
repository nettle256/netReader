package netReader.ApiController.User;

import netReader.Constant.MD5;
import netReader.Controller.User.UserAuthority;
import netReader.Model.Article;
import netReader.Model.User;
import netReader.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Nettle on 2017/1/5.
 */
@Controller
@RequestMapping("/api/user")
public class UserApiController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthority userAuthority;

    @RequestMapping(value = "", method = RequestMethod.GET)
    private @ResponseBody ResponseEntity<User> getCurrentUser(
            @SessionAttribute(value="currentUser", required=false) User currentUser
    )   {
        return new ResponseEntity<User>(currentUser, currentUser == null ? HttpStatus.FORBIDDEN : HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private @ResponseBody User getUserInfo(
        @PathVariable(value = "id") Long id
    )   {
        return userRepository.findById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    private ResponseEntity<User> createUser(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            @RequestParam(value = "description", required=false) String description,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    )   {
        if (currentUser != null)
            return new ResponseEntity<User>((User) null, HttpStatus.FORBIDDEN);

        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(MD5.md5(password));
            if (description == null) description = "";
            user.setDescription(description);
            user.setAuthority(UserAuthority.USER);
            userRepository.save(user);
            return new ResponseEntity<User>(user, HttpStatus.CREATED);
        }   catch (Exception e) {
            return new ResponseEntity<User>((User) null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    private ResponseEntity<User> updateUserInfo(
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "description", required = false) String description,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    )   {
        if (!userAuthority.checkCurrentUserAuthority(UserAuthority.USER, currentUser) || !currentUser.getId().equals(id))
            return new ResponseEntity<User>((User) null, HttpStatus.FORBIDDEN);

        try {
            User user = userRepository.findById(id);
            user.setDescription(description);
            userRepository.save(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }   catch (Exception e) {
            return new ResponseEntity<User>((User) null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value = "/{id}/password", method = RequestMethod.PUT)
    private ResponseEntity<String> updateUserPassword(
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "old_password") String old_password,
            @RequestParam(value = "new_password") String new_password,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    )   {
        if (!userAuthority.checkCurrentUserAuthority(UserAuthority.USER, currentUser) || !currentUser.getId().equals(id))
            return new ResponseEntity<String>("Error: Wrong user", HttpStatus.FORBIDDEN);

        try {
            User user = userRepository.findById(id);
            if (!MD5.md5(old_password).equals(user.getPassword())) {
                return new ResponseEntity<String>("Error: Old password is not correct", HttpStatus.FORBIDDEN);
            }
            user.setPassword(MD5.md5(new_password));
            userRepository.save(user);
            return new ResponseEntity<String>("OK: Password Updated", HttpStatus.OK);
        }   catch (Exception e) {
            return new ResponseEntity<String>((String) null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
