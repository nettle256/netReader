package netReader;

import netReader.Translate.Baidu.BaiduTranslate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by Nettle on 2017/1/5.
 */

@Controller
//@SessionAttributes("currentUser")
public class Test {

    @Autowired
    private static BaiduTranslate baiduTranslate;

    public static void main(String[] args) {
        try {
            JSONObject json= new JSONObject(baiduTranslate.getTransResult("「戻りました。遅くなってごめんなさい」", "jp", "zh"));
            JSONArray tpArr = json.getJSONArray("trans_result");
            JSONObject item = (JSONObject) tpArr.get(0);
            String zhs = (String) item.get("dst");
            System.out.println(zhs);
        }   catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
