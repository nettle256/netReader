package netReader.JsonModel;

import netReader.Model.Article;
import netReader.Model.Chapter;
import netReader.Model.Translation;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by Nettle on 2017/1/9.
 */
public class JTranslation {
    private Long id;

    private Long novelId;
    private Long chapterId;

    private String author;
    private Long authorId;

    private List<String> content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNovelId() {
        return novelId;
    }

    public void setNovelId(Long novelId) {
        this.novelId = novelId;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public JTranslation(Chapter chapter, Translation translation, Article article) {
        this.id = translation.getId();
        this.novelId = chapter.getNovelId();
        this.chapterId = chapter.getSubId();
        this.author = translation.getAuthor();
        this.authorId = translation.getAuthorId();
        JSONArray json = new JSONArray(article.getContent());
        this.content = (List) json.toList();
    }
}
