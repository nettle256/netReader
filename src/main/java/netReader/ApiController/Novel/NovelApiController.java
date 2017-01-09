package netReader.ApiController.Novel;

import netReader.Controller.Spider.ImportNovel;
import netReader.Controller.Spider.ScheduleUpdate;
import netReader.Controller.User.UserAuthority;
import netReader.JsonModel.JMessage;
import netReader.Model.Article;
import netReader.Model.Novel;
import netReader.Model.NovelRepository;
import netReader.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
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
            @RequestBody Novel novel,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    )   {
        if (!UserAuthority.checkCurrentUserAuthority(UserAuthority.USER_ADMIN, currentUser))
            return new ResponseEntity<Novel>((Novel) null, HttpStatus.FORBIDDEN);
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
            @RequestBody Novel novel,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    )   {
        if (!UserAuthority.checkCurrentUserAuthority(UserAuthority.USER_ADMIN, currentUser))
            return new ResponseEntity<Novel>((Novel) null, HttpStatus.FORBIDDEN);

        try {
            novel.setId(id);
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
        if (!UserAuthority.checkCurrentUserAuthority(UserAuthority.ADMIN, currentUser))
            return new ResponseEntity<Novel>((Novel) null, HttpStatus.FORBIDDEN);

        try {
            importNovel.run(novelRepository.findById(id));
            return new ResponseEntity<Novel>((Novel) null, HttpStatus.ACCEPTED);
        }   catch (Exception e) {
            return new ResponseEntity<Novel>((Novel) null, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<JMessage> deleteNovel(
            @PathVariable(value="id") Long id,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    )   {
        if (!UserAuthority.checkCurrentUserAuthority(UserAuthority.USER_ADMIN, currentUser))
            return new ResponseEntity<JMessage>(new JMessage("错误：没有权限"), HttpStatus.FORBIDDEN);

        try {
            Novel novel = novelRepository.findById(id);
            novel.setDeleted(true);
            novelRepository.save(novel);
            return new ResponseEntity<JMessage>(new JMessage("成功：删除小说 " + novel.getName()), HttpStatus.OK);
        }   catch (Exception e) {
            return new ResponseEntity<JMessage>(new JMessage("错误：系统错误"), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value="/{id}/recover", method = RequestMethod.PUT)
    public ResponseEntity<JMessage> recoverNovel(
            @PathVariable(value="id") Long id,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    )   {
        if (!UserAuthority.checkCurrentUserAuthority(UserAuthority.USER_ADMIN, currentUser))
            return new ResponseEntity<JMessage>(new JMessage("错误：没有权限"), HttpStatus.FORBIDDEN);

        try {
            Novel novel = novelRepository.findById(id);
            novel.setDeleted(false);
            novelRepository.save(novel);
            return new ResponseEntity<JMessage>(new JMessage("成功：恢复小说 " + novel.getName()), HttpStatus.OK);
        }   catch (Exception e) {
            return new ResponseEntity<JMessage>(new JMessage("错误：系统错误"), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @RequestMapping(value="/{id}/scan", method = RequestMethod.PUT)
    public ResponseEntity<JMessage> scanNovel(
            @PathVariable(value="id") Long id,
            @SessionAttribute(value="currentUser", required=false) User currentUser
    )   {
        if (!UserAuthority.checkCurrentUserAuthority(UserAuthority.USER_ADMIN, currentUser))
            return new ResponseEntity<JMessage>(new JMessage("错误：没有权限"), HttpStatus.FORBIDDEN);

        try {
            Novel novel = novelRepository.findById(id);
            if (novel.getScanning())
                return new ResponseEntity<JMessage>(new JMessage("错误：该小说正在扫描中"), HttpStatus.FORBIDDEN);
            novel.setScanning(true);
            importNovel.run(novel);
            novelRepository.save(novel);
            return new ResponseEntity<JMessage>(new JMessage("成功：开始扫描小说 " + novel.getName()), HttpStatus.OK);
        }   catch (Exception e) {
            return new ResponseEntity<JMessage>(new JMessage("错误：系统错误"), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
