package netReader.Translate;

import netReader.Translate.Baidu.BaiduTranslate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Nettle on 2017/1/9.
 */

@Service
public class Translate {
    @Autowired
    private BaiduTranslate baiduTranslate;

    public List<String> translate(List<String> content) {
        List<String> dst = new ArrayList<String>();
        for (String aContent : content) {
            if (aContent.length() > 1) {
                try {
                    JSONObject result = new JSONObject(baiduTranslate.getTransResult(aContent, "jp", "zh"));
                    JSONArray tpArr = result.getJSONArray("trans_result");
                    dst.add((String) ((JSONObject) tpArr.get(0)).get("dst"));
                } catch (Exception e) {
                }
            }   else {
                dst.add("");
            }
        }
        return dst;
    }

    public List<String> translate(String content) {
        JSONArray json = new JSONArray(content);
        List<String> tp = (List) json.toList();
        return this.translate(tp);
    }
}
