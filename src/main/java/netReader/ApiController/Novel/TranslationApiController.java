package netReader.ApiController.Novel;

import netReader.JsonModel.JTranslation;
import netReader.Model.*;
import netReader.Translate.Translate;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static netReader.Model.ArticleRepository.*;

/**
 * Created by Nettle on 2017/1/9.
 */
@Controller
@RequestMapping("api/translation")
public class TranslationApiController {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private Translate translate;

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public @ResponseBody JTranslation getTranslationList(
            @PathVariable(value = "id") Long id
    )   {
        try {
            Translation translation = translationRepository.findById(id);
            Chapter chapter = chapterRepository.findById(translation.getSrcId());
            if (translation.getArticleId() == null) {
                Article article = articleRepository.findById(chapter.getArticleId());
                Article trans = new Article();
                trans.setContent(new JSONArray(translate.translate(article.getContent())).toString());
                articleRepository.save(trans);
                translation.setArticleId(trans.getId());
                translationRepository.save(translation);
                return new JTranslation(chapter, translation, trans);
            }   else {
                return new JTranslation(chapter, translation, articleRepository.findById(translation.getArticleId()));
            }
        }   catch (Exception e) {
            return null;
        }
    }
}
