package netReader.ApiController.Novel;

import netReader.Controller.Spider.ImportNovel;
import netReader.Controller.User.UserAuthority;
import netReader.JsonModel.JArticle;
import netReader.JsonModel.JChapter;
import netReader.JsonModel.JMessage;
import netReader.Model.*;
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
    private ImportNovel importNovel;

    @RequestMapping(value="/chapter", method = RequestMethod.GET)
    public @ResponseBody List<Chapter> getChapter(
            @PathVariable(value = "novelId") Long novelId
    ) {
        return chapterRepository.findAllByNovelId(novelId);
    }

    @RequestMapping(value="/chapter/{subId}", method = RequestMethod.GET)
    public @ResponseBody JArticle getArticle(
            @PathVariable(value = "novelId") Long novelId,
            @PathVariable(value = "subId") Long subId
    ) {
        Novel novel = novelRepository.findById(novelId);
        Chapter chapter = chapterRepository.findByNovelIdAndSubId(novelId, subId);
        return new JArticle(novel, chapter, articleRepository.findById(chapter.getArticleId()));
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
