package com.zht.modulelibrary.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Date 2022/12/15 16:46
 * @Author zhanghaitao
 * @Description
 */
public class JsoupHelper {

    public static void loadFormUrl(String url, Observer<Document> observer) {
        Observable.create(new ObservableOnSubscribe<Document>() {
                    @Override
                    public void subscribe(ObservableEmitter<Document> emitter) throws Exception {
                        Document document = Jsoup.connect(url).get();
                        emitter.onNext(document);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void loadFormFile(String filePath, Observer<Document> observer) {
        loadFormFile(new File(filePath), observer);
    }

    public static void loadFormFile(File file, Observer<Document> observer) {
        Observable.create(new ObservableOnSubscribe<Document>() {
                    @Override
                    public void subscribe(ObservableEmitter<Document> emitter) throws Exception {
                        Document document = Jsoup.parse(file, "utf-8");
                        emitter.onNext(document);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
