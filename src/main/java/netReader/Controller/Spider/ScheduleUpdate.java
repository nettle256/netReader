package netReader.Controller.Spider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import netReader.Model.*;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
/**
 * Created by Nettle on 2017/1/4.
 */

@Component
public class ScheduleUpdate {

    @Autowired
    private static NovelRepository novelRepository;

    @Autowired
    private static ChapterRepository chapterRepository;

    @Autowired
    private static ArticleRepository articleRepository;

    static Spider spider;
    static Syosetu syosetu;

    @Scheduled(fixedRate = 10000)
    public void update() {
    }

    public static void main(String[] args) {
    }
}
