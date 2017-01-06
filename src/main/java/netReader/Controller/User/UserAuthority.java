package netReader.Controller.User;

import netReader.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Nettle on 2017/1/5.
 */
@Service
public class UserAuthority {
    public static Long USER = 1L;
    public static Long TRANSLATOR = 2L;
    public static Long USER_ADMIN = 4L;
    public static Long ADMIN = 8L;

    public static Boolean checkCurrentUserAuthority(
            Long requestAuthority,
            User currentUser
    ) {
        return (currentUser != null && ((currentUser.getAuthority() & requestAuthority) == requestAuthority));
    }
}
