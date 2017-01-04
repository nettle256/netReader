package netReader.ApiController.Novel;

import netReader.Model.Article;
import netReader.Model.ArticleRepository;
import netReader.Model.Chapter;
import netReader.Model.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nettle on 2017/1/4.
 */

@Controller
@RequestMapping("/api/Novel/{novelId}")
public class ChapterApiController {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping(value="/chapter", method = RequestMethod.GET)
    public @ResponseBody List<Chapter> getChapter(
            @RequestParam(value = "novelId") Long novelId
    ) {
        return chapterRepository.findAllByNovelId(novelId);
    }

    @RequestMapping(value="/chapter/{id}", method = RequestMethod.GET)
    public @ResponseBody Article getArticle(
            @RequestParam(value = "id") Long id
    ) {
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        Chapter chapter = chapterRepository.findAllById(ids).get(0);
        ids.clear();
        ids.add(chapter.getArticleId());
        return articleRepository.findAllById(ids).get(0);
    }
}
