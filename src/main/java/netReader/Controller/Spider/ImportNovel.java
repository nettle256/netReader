package netReader.Controller.Spider;

import netReader.Model.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Nettle on 2017/1/4.
 */
@Service
public class ImportNovel {

    private static Long SLEEP_TIMES = 0L;

    @Autowired
    private NovelRepository novelRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public Article importNovelById(Novel novel, Long id) throws Exception {
        JSONObject data =  Syosetu.Analysis(Spider.getHtml(novel.getUrl() + id.intValue()));

        Chapter chapter = chapterRepository.findByNovelIdAndSubId(novel.getId(), id);
        Article article = new Article();
        if (chapter == null) {
            chapter = new Chapter();
        }   else {
            article = articleRepository.findById(chapter.getArticleId());
        }

        article.setAuthor(novel.getAuthor());
        article.setTitle(data.get("title").toString());
        article.setContent(data.get("content").toString());
        articleRepository.save(article);

        chapter.setNovelId(novel.getId());
        chapter.setSubId(id);
        chapter.setSubTitle(article.getTitle());
        chapter.setArticleId(article.getId());
        chapterRepository.save(chapter);

        novel.setChapters(id);
        novelRepository.save(novel);

        return article;
    }

    @Async
    public void run(Novel novel) {
        System.out.println("import novel 《" + novel.getName() + "》");
        Long id = novel.getChapters();
        while (true) {
            try {
                id += 1L;
                importNovelById(novel, id);
                Thread.sleep(SLEEP_TIMES);
            }   catch (Exception e) {
                System.out.println(e.toString());
                System.out.println("Error occurred @ Get novel 《" + novel.getName() + "》 chapter " + id);
                break;
            }
        }
    }
}
