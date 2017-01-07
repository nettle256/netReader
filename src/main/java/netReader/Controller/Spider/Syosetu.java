package netReader.Controller.Spider;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Nettle on 2017/1/4.
 */
public class Syosetu {
    public static String clear(String content) {
        content = content.replaceAll("<br />", "");
        content = content.replaceAll("<br>", "");
        int p, pr, br, bl;
        do {
            p = content.indexOf("\n<ruby>");
            if (p >= 0) {
                pr = content.indexOf("</ruby>");
                bl = content.indexOf("<rb>");
                br = content.indexOf("</rb>");
                content = content.substring(0, p-1) + content.substring(bl+4, br-1).replaceAll("\n", "") + content.substring(pr+7, content.length()-1);
            }
        }	while (p >= 0);
        return content;
    }

    public static JSONObject Analysis(Document document) {

        JSONObject ret = new JSONObject();
        JSONArray article_content = new JSONArray();

        String content_pre = clear(document.select("#novel_p").html());
        String content = clear(document.select("#novel_honbun").html());
        String content_after = clear(document.select("#novel_a").html());

        if (content_pre.length() > 0) {
            String[] pre_lines = content_pre.split("\n");
            for (int i = 0; i < pre_lines.length; ++i)
                article_content.put(pre_lines[i]);
            article_content.put("==========");
        }

        String[] lines = content.split("\n");
        for (int i = 0; i < lines.length; ++i)
            article_content.put(lines[i]);

        if (content_after.length() > 0) {
            article_content.put("==========");
            String[] after_lines = content_after.split("\n");
            for (int i = 0; i < after_lines.length; ++i)
                article_content.put(after_lines[i]);
        }

        ret.put("title", document.select(".novel_subtitle").html());
        ret.put("content", article_content);

        return ret;
    }
}
