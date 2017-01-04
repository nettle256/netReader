package netReader.ApiController.Novel;

import netReader.Controller.Spider.ImportNovel;
import netReader.Controller.Spider.ScheduleUpdate;
import netReader.Model.Novel;
import netReader.Model.NovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nettle on 2017/1/4.
 */
@Controller
@RequestMapping("/api")
public class NovelApiController {

    @Autowired
    private NovelRepository novelRepository;

    @Autowired
    private ImportNovel importNovel;

    private ScheduleUpdate scheduleUpdate;

    @RequestMapping(value="/novel", method = RequestMethod.GET)
    public @ResponseBody List<Novel> getNovel() {
        Novel novel = new Novel();
        return novelRepository.findAllByDeleted(false);
    }

    @RequestMapping(value="/novel/all", method = RequestMethod.GET)
    public @ResponseBody List<Novel> getAllNovel() {
        Novel novel = new Novel();
        return novelRepository.findAll();
    }

    @RequestMapping(value="/novel/trash", method = RequestMethod.GET)
    public @ResponseBody List<Novel> getDeletedNovel() {
        Novel novel = new Novel();
        return novelRepository.findAllByDeleted(true);
    }

    @RequestMapping(value="/novel/{id}", method = RequestMethod.GET)
    public @ResponseBody Novel getNovel(
            @PathVariable(value="id") Long id
    ) {
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        try {
            return novelRepository.findAllById(ids).get(0);
        }   catch (Exception e) {

        }
        return null;
    }

    @RequestMapping(value="/novel", method = RequestMethod.POST)
    public @ResponseBody Novel createNovel(
            @RequestParam(value="name") String name,
            @RequestParam(value="cnName", required = false) String cnName,
            @RequestParam(value="author", required = false) String author,
            @RequestParam(value="url") String url,
            @RequestParam(value="source") String source
    )   {
        Novel novel = new Novel();
        novel.setName(name);
        novel.setCnName(cnName);
        novel.setAuthor(author);
        novel.setUrl(url);
        novel.setSource(source);
        return novel;
    }

    @RequestMapping(value="/novel/{id}", method = RequestMethod.PUT)
    public @ResponseBody Novel updateNovel(
            @PathVariable(value="id") Long id,
            @RequestParam(value="name", required = false) String name,
            @RequestParam(value="cnName", required = false) String cnName,
            @RequestParam(value="author", required = false) String author,
            @RequestParam(value="url", required = false) String url,
            @RequestParam(value="source", required = false) String source
    )   {
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        try {
            Novel novel = novelRepository.findAllById(ids).get(0);
            if (name   != null) novel.setName(name);
            if (cnName != null) novel.setCnName(cnName);
            if (author != null) novel.setAuthor(author);
            if (url    != null) novel.setUrl(url);
            if (source != null) novel.setSource(source);
            novelRepository.save(novel);
            return novel;
        }   catch (Exception e) {

        }
        return null;
    }

    @RequestMapping(value="/novel/{id}/import", method = RequestMethod.PUT)
    public @ResponseBody Novel importNovel(
            @PathVariable(value="id") Long id
    )   {
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        try {
            Novel novel = novelRepository.findAllById(ids).get(0);
            importNovel.run(novel);
        }   catch (Exception e) {
        }
        return null;
    }

    @RequestMapping(value="/novel/{id}", method = RequestMethod.DELETE)
    public @ResponseBody Novel deleteNovel(
            @PathVariable(value="id") Long id
    )   {
        List<Long> ids = new ArrayList<Long>();
        ids.add(id);
        try {
            Novel novel = novelRepository.findAllById(ids).get(0);
            novel.setDeleted(true);
            novelRepository.save(novel);
            return null;
        }   catch (Exception e) {

        }
        return null;
    }
}
