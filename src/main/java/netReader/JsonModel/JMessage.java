package netReader.JsonModel;

/**
 * Created by Nettle on 2017/1/6.
 */
public class JMessage {
    private String message;

    public JMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
