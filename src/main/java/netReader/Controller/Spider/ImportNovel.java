package netReader.Controller.Spider;

import netReader.Model.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

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
        System.out.println("import novel 《" + novel.getName() + "》 chapter " + id);
        JSONObject data =  Syosetu.Analysis(Spider.getHtml(novel.getUrl() + id.intValue()));

        Chapter chapter = chapterRepository.findByNovelIdAndSubId(novel.getId(), id);
        Article article = new Article();

        article.setContent(data.get("content").toString());
        articleRepository.save(article);

        if (chapter.getNovelId() == null) chapter.setNovelId(novel.getId());
        if (chapter.getSubId() == null) chapter.setSubId(id);
        if (chapter.getSubTitle() == null) chapter.setSubTitle(data.get("title").toString());
        chapter.setArticleId(article.getId());
        chapterRepository.save(chapter);

        return article;
    }

    public void importChapters(Novel novel) {
        List<Chapter> chapters = new ArrayList<Chapter>();
        try {
            List<String> data = Syosetu.AnalysisChapters(Spider.getHtml(novel.getUrl()));
            for (Long i = novel.getChapters(); i < data.size(); ++i) {
                Chapter chapter = new Chapter();
                chapter.setNovelId(novel.getId());
                chapter.setSubTitle(data.get(i.intValue()));
                chapter.setSubId(i + 1);
                chapters.add(chapter);
            }
            novel.setChapters((long) data.size());
            chapterRepository.save(chapters);
            novelRepository.save(novel);
        }   catch (Exception e) {
            System.out.println(e.toString());
        }
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
                novelRepository.save(novel);
                System.out.println(e.toString());
                System.out.println("Error occurred @ Get novel 《" + novel.getName() + "》 chapter " + id);
                break;
            }
        }
    }

    public static void main(String[] args) {
    }
}
