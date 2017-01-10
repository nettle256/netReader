package netReader.ApiController.Novel;

import netReader.Controller.Spider.ImportNovel;
import netReader.Controller.User.UserAuthority;
import netReader.JsonModel.JArticle;
import netReader.JsonModel.JChapter;
import netReader.JsonModel.JMessage;
import netReader.Model.*;
import netReader.Translate.Baidu.BaiduTranslate;
import netReader.Translate.Translate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nettle on 2017/1/4.
 */

@Controller
@RequestMapping("/api/novel/{novelId}")
public class ChapterApiController {

    @Autowired
    private NovelRepository novelRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private ImportNovel importNovel;


    @Autowired
    private Translate translate;

    @RequestMapping(value="/chapter", method = RequestMethod.GET)
    public @ResponseBody List<Chapter> getChapter(
            @PathVariable(value = "novelId") Long novelId
    ) {
        return chapterRepository.findAllByNovelId(novelId);
    }

    @RequestMapping(value="/chapter/{subId}", method = RequestMethod.GET)
    public ResponseEntity<JArticle> getArticle(
            @PathVariable(value = "novelId") Long novelId,
            @PathVariable(value = "subId") Long subId
    ) {
        Novel novel = novelRepository.findById(novelId);
        Chapter chapter = chapterRepository.findByNovelIdAndSubId(novelId, subId);
        if (chapter.getArticleId() == null) {
            try {
                Article article = importNovel.importNovelById(novelRepository.findById(novelId), subId);
                return new ResponseEntity<JArticle>(new JArticle(novel, chapter, article), HttpStatus.OK);
            }   catch (Exception e) {
                return new ResponseEntity<JArticle>((JArticle) null, HttpStatus.NOT_ACCEPTABLE);
            }
        }
        return new ResponseEntity<JArticle>(new JArticle(novel, chapter, articleRepository.findById(chapter.getArticleId())), HttpStatus.OK);
    }

    @RequestMapping(value="/chapter/{subId}/translation", method = RequestMethod.GET)
    public @ResponseBody List<Translation> getTranslationList(
            @PathVariable(value = "novelId") Long novelId,
            @PathVariable(value = "subId") Long subId
    ) {
        try {
            Chapter chapter = chapterRepository.findByNovelIdAndSubId(novelId, subId);
            List<Translation> ret = translationRepository.findAllBySrcId(chapter.getId());
            if (ret.size() < 1) {
                Translation translation = new Translation(chapter.getId(), null, "BaiduTranslate");
                translationRepository.save(translation);
                ret.add(translation);
            }
            return ret;
        }   catch (Exception e) {
            System.out.println(e.toString());
            return new ArrayList<Translation>();
        }
    }

    @RequestMapping(value="/chapter/{subId}", method = RequestMethod.PUT)
    public ResponseEntity<JMessage> getArticle(
            @RequestBody JChapter jChapter,
            @PathVariable(value = "novelId") Long novelId,
            @PathVariable(value = "subId") Long subId,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    ) {
        if (!UserAuthority.checkCurrentUserAuthority(UserAuthority.TRANSLATOR, currentUser))
            return new ResponseEntity<JMessage>(new JMessage("错误：没有权限"), HttpStatus.FORBIDDEN);

        try {
            Chapter chapter = chapterRepository.findByNovelIdAndSubId(novelId, subId);
            if (jChapter.getCnSubTitle() == null)
                chapter.setCnSubTitle("");
            else chapter.setCnSubTitle(jChapter.getCnSubTitle());
            chapterRepository.save(chapter);
            return new ResponseEntity<JMessage>(new JMessage("成功：章节标题已更新"), HttpStatus.OK);
        }   catch (Exception e) {
            return new ResponseEntity<JMessage>(new JMessage("错误：系统错误"), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value="/chapter/{id}/import", method = RequestMethod.PUT)
    public ResponseEntity<Article> importArticle(
            @PathVariable(value = "novelId") Long novelId,
            @PathVariable(value = "id") Long id,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    ) {
        if (!UserAuthority.checkCurrentUserAuthority(UserAuthority.USER_ADMIN, currentUser))
            return new ResponseEntity<Article>((Article) null, HttpStatus.FORBIDDEN);

        try {
            return new ResponseEntity<Article>(importNovel.importNovelById(novelRepository.findById(novelId), id), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }

    }
}
