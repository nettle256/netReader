package netReader.JsonModel;

import netReader.Model.Article;
import netReader.Model.Chapter;
import netReader.Model.Novel;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by Nettle on 2017/1/7.
 */
public class JArticle {
    private Long novelId;
    private Long subId;
    private String subTitle;
    private String cnSubTitle;
    private String novelName;
    private String novelCnName;
    private String author;
    private List content;
    private String url;

    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getCnSubTitle() {
        return cnSubTitle;
    }

    public void setCnSubTitle(String cnSubTitle) {
        this.cnSubTitle = cnSubTitle;
    }

    public String getNovelName() {
        return novelName;
    }

    public void setNovelName(String novelName) {
        this.novelName = novelName;
    }

    public String getNovelCnName() {
        return novelCnName;
    }

    public void setNovelCnName(String novelCnName) {
        this.novelCnName = novelCnName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JArticle(Novel novel, Chapter chapter, Article article) {
        this.novelId = novel.getId();
        this.subId = chapter.getSubId();
        this.novelName = novel.getName();
        this.novelCnName = novel.getCnName();
        this.subTitle = chapter.getSubTitle();
        this.cnSubTitle = chapter.getCnSubTitle();
        this.author = novel.getAuthor();
        JSONArray json = new JSONArray(article.getContent());
        this.content = (List) json.toList();
        this.url = novel.getUrl() + this.subId;
    }
}
