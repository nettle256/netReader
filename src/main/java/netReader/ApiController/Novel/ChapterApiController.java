package netReader.ApiController.Novel;

import netReader.Controller.Spider.ImportNovel;
import netReader.Controller.User.UserAuthority;
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

    @Autowired
    private UserAuthority userAuthority;

    @RequestMapping(value="/chapter", method = RequestMethod.GET)
    public @ResponseBody List<Chapter> getChapter(
            @PathVariable(value = "novelId") Long novelId
    ) {
        return chapterRepository.findAllByNovelId(novelId);
    }

    @RequestMapping(value="/chapter/{subId}", method = RequestMethod.GET)
    public @ResponseBody Article getArticle(
            @PathVariable(value = "novelId") Long novelId,
            @PathVariable(value = "subId") Long subId
    ) {
        return articleRepository.findById(chapterRepository.findByNovelIdAndSubId(novelId, subId).getArticleId());
    }

    @RequestMapping(value="/chapter/{id}/import", method = RequestMethod.PUT)
    public ResponseEntity<Article> importArticle(
            @PathVariable(value = "novelId") Long novelId,
            @PathVariable(value = "id") Long id,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    ) {
        if (!userAuthority.checkCurrentUserAuthority(UserAuthority.ADMIN, currentUser))
            return new ResponseEntity<Article>((Article) null, HttpStatus.FORBIDDEN);

        try {
            return new ResponseEntity<Article>(importNovel.importNovelById(novelRepository.findById(novelId), id), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }

    }
}
