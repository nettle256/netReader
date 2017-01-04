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

    @Autowired
    private NovelRepository novelRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Async
    public void run(Novel novel) {
        System.out.println("import novel 《" + novel.getName() + "》");
        Integer chapterId = novel.getChapters() + 1;
        while (true) {
            try {
                JSONObject data = Syosetu.Analysis(Spider.getHtml(novel.getUrl() + chapterId));

                Article article = new Article();
                article.setAuthor(novel.getAuthor());
                article.setTitle(data.get("title").toString());
                article.setContent(data.get("content").toString());
                articleRepository.save(article);

                Chapter chapter = new Chapter();
                chapter.setNovelId(novel.getId());
                chapter.setSubId(new Long(chapterId));
                chapter.setSubTitle(article.getTitle());
                chapter.setArticleId(article.getId());
                chapterRepository.save(chapter);
            }   catch (Exception e) {
                System.out.println(e);
                System.out.println("Error occurred @ Get novel 《" + novel.getName() + "》 chapter " + chapterId);
                break;
            }
            chapterId = chapterId + 1;
        }
        novel.setChapters(chapterId - 1);
        novelRepository.save(novel);
    }
}
