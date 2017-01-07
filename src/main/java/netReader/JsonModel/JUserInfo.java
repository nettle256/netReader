package netReader.JsonModel;

import netReader.Controller.User.UserAuthority;
import netReader.Model.User;

/**
 * Created by Nettle on 2017/1/6.
 */
public class JUserInfo {

    private Long id;
    private String username;
    private String description;
    private String authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public JUserInfo(User user) {
        if (user == null) return ;
        this.id = user.getId();
        this.username = user.getUsername();
        this.description = user.getDescription();
        this.authority = "user";
        if (UserAuthority.checkCurrentUserAuthority(UserAuthority.TRANSLATOR, user))
            this.authority = "translator";
        if (UserAuthority.checkCurrentUserAuthority(UserAuthority.USER_ADMIN, user))
            this.authority = "user_admin";
        if (UserAuthority.checkCurrentUserAuthority(UserAuthority.ADMIN, user))
            this.authority = "admin";
    }
}
