package netReader.ApiController.Novel;

import netReader.Controller.Spider.ImportNovel;
import netReader.Controller.Spider.ScheduleUpdate;
import netReader.Controller.User.UserAuthority;
import netReader.Model.Article;
import netReader.Model.Novel;
import netReader.Model.NovelRepository;
import netReader.Model.User;
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
@RequestMapping("/api/novel")
public class NovelApiController {

    @Autowired
    private NovelRepository novelRepository;

    @Autowired
    private ImportNovel importNovel;

    @Autowired
    private UserAuthority userAuthority;

    private ScheduleUpdate scheduleUpdate;

    @RequestMapping(value="", method = RequestMethod.GET)
    public @ResponseBody List<Novel> getNovel() {
        return novelRepository.findAllByDeleted(false);
    }

    @RequestMapping(value="/all", method = RequestMethod.GET)
    public @ResponseBody List<Novel> getAllNovel() {
        return novelRepository.findAll();
    }

    @RequestMapping(value="/trash", method = RequestMethod.GET)
    public @ResponseBody List<Novel> getDeletedNovel() {
        return novelRepository.findAllByDeleted(true);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public @ResponseBody Novel getNovel(
            @PathVariable(value="id") Long id
    ) {
        return novelRepository.findById(id);
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public ResponseEntity<Novel> createNovel(
            @RequestParam(value="name") String name,
            @RequestParam(value="cnName", required = false) String cnName,
            @RequestParam(value="author", required = false) String author,
            @RequestParam(value="url") String url,
            @RequestParam(value="source") String source,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    )   {
        if (!userAuthority.checkCurrentUserAuthority(UserAuthority.ADMIN, currentUser))
            return new ResponseEntity<Novel>((Novel) null, HttpStatus.FORBIDDEN);

        Novel novel = new Novel();
        novel.setName(name);
        novel.setCnName(cnName);
        novel.setAuthor(author);
        novel.setUrl(url);
        novel.setSource(source);
        try {
            novelRepository.save(novel);
            return new ResponseEntity<Novel>(novel, HttpStatus.CREATED);
        }   catch (Exception e) {
            return new ResponseEntity<Novel>((Novel) null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Novel> updateNovel(
            @PathVariable(value="id") Long id,
            @RequestParam(value="name", required = false) String name,
            @RequestParam(value="cnName", required = false) String cnName,
            @RequestParam(value="author", required = false) String author,
            @RequestParam(value="url", required = false) String url,
            @RequestParam(value="source", required = false) String source,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    )   {
        if (!userAuthority.checkCurrentUserAuthority(UserAuthority.ADMIN, currentUser))
            return new ResponseEntity<Novel>((Novel) null, HttpStatus.FORBIDDEN);

        try {
            Novel novel = novelRepository.findById(id);
            if (name   != null) novel.setName(name);
            if (cnName != null) novel.setCnName(cnName);
            if (author != null) novel.setAuthor(author);
            if (url    != null) novel.setUrl(url);
            if (source != null) novel.setSource(source);
            novelRepository.save(novel);
            return new ResponseEntity<Novel>(novel, HttpStatus.OK);
        }   catch (Exception e) {
            return new ResponseEntity<Novel>((Novel) null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value="/{id}/import", method = RequestMethod.PUT)
    public ResponseEntity<Novel> importNovel(
            @PathVariable(value="id") Long id,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    )   {
        if (!userAuthority.checkCurrentUserAuthority(UserAuthority.ADMIN, currentUser))
            return new ResponseEntity<Novel>((Novel) null, HttpStatus.FORBIDDEN);

        try {
            importNovel.run(novelRepository.findById(id));
            return new ResponseEntity<Novel>((Novel) null, HttpStatus.ACCEPTED);
        }   catch (Exception e) {
            return new ResponseEntity<Novel>((Novel) null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Novel> deleteNovel(
            @PathVariable(value="id") Long id,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    )   {
        if (!userAuthority.checkCurrentUserAuthority(UserAuthority.ADMIN, currentUser))
            return new ResponseEntity<Novel>((Novel) null, HttpStatus.FORBIDDEN);

        try {
            Novel novel = novelRepository.findById(id);
            novel.setDeleted(true);
            novelRepository.save(novel);
            return new ResponseEntity<Novel>(novel, HttpStatus.OK);
        }   catch (Exception e) {
            return new ResponseEntity<Novel>((Novel) null, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
