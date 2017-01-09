package netReader.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Nettle on 2017/1/9.
 */
@Entity
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 对应Chapters的ID
    private Long srcId;

    private String author;
    private Long authorId;

    private Long articleId;

    @JsonIgnore
    private Boolean machine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSrcId() {
        return srcId;
    }

    public void setSrcId(Long srcId) {
        this.srcId = srcId;
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

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Boolean getMachine() {
        return machine;
    }

    public void setMachine(Boolean machine) {
        this.machine = machine;
    }

    public Translation() {

    }

    public Translation(Long srcId, Long authorId, String author) {
        this.srcId = srcId;
        this.authorId = authorId;
        this.author = author;
        if (authorId == null)
            this.machine = true;
    }
}
