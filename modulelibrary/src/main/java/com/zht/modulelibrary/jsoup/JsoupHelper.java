package com.zht.modulelibrary.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;

/**
 * @Date 2022/12/15 16:46
 * @Author zhanghaitao
 * @Description
 */
public class JsoupHelper {

    public static Document loadFormUrl(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

    public static Document loadFormFile(String filePath) {
        return loadFormFile(new File(filePath));
    }

    public static Document loadFormFile(File file) {
        Document document = null;
        try {
            document = Jsoup.parse(file, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }

}
