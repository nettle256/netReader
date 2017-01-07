package netReader.JsonModel;

import netReader.Controller.User.UserAuthority;
import netReader.Model.User;

/**
 * Created by Nettle on 2017/1/6.
 */
public class JUser {
    private Long id;
    private String username;
    private String password;
    private String description;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
