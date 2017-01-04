package netReader.Controller.Spider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Nettle on 2017/1/4.
 */
public class Spider {
    public static Document getHtml(String url) throws Exception {
        Connection.Response response = Jsoup.connect(url).timeout(3000).execute();
        return Jsoup.parse(response.body());
    }

    public static void main(String[] args) {
    }
}
