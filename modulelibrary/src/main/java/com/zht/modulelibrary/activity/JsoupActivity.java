package com.zht.modulelibrary.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.base.BaseViewBindingActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.constant.ParamsConstants;
import com.zht.common.impl.ObserverImpl;
import com.zht.modulelibrary.bean.ChapterBean;
import com.zht.modulelibrary.databinding.ActivityJsoupBinding;
import com.zht.modulelibrary.jsoup.JsoupHelper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * @Date 2022/12/15 17:47
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Library.JSOUP_ACTIVITY)
public class JsoupActivity extends BaseViewBindingActivity<ActivityJsoupBinding> {

    @Override
    protected ActivityJsoupBinding getViewBinding() {
        return ActivityJsoupBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        binding.jsoupSearchResult.setMovementMethod(ScrollingMovementMethod.getInstance());


        binding.jsoupSearchUrl.setText("https://m.imitui.com/search/?keywords=");
        binding.jsoupSearchName.setText("万人之上");

    }

    public void clickSearch(View view) {
        String url = binding.jsoupSearchUrl.getText().toString();
        String name = binding.jsoupSearchName.getText().toString();

        JsoupHelper.loadFormUrl(url + name, new ObserverImpl<Document>() {

            @Override
            public void onNext(Document document) {
                if (document == null) {
                    binding.jsoupSearchResult.setText("无结果");
                    return;
                }
                dispatchSearch(document);
            }

            @Override
            public void onError(Throwable e) {
                binding.jsoupSearchResult.setText("无结果");
            }
        });

    }

    private void dispatchSearch(@NonNull Document document) {
        String name = binding.jsoupSearchName.getText().toString();
        StringBuffer sb = new StringBuffer();
        sb.append("结果:\n");
        Elements links = document.select("a[href]");
        String linkUrl = "";
        for (Element link : links) {
            if (TextUtils.equals(name, link.text())) {
                sb.append("text:" + link.text() + "\n");
                sb.append("link:" + link.attr("href") + "\n");
                linkUrl = link.attr("href");
            }
        }
        binding.jsoupSearchResult.setText(sb.toString());
        if (!TextUtils.isEmpty(linkUrl)) {
            JsoupHelper.loadFormUrl(linkUrl, new ObserverImpl<Document>() {

                @Override
                public void onNext(Document document) {
                    if (document == null) {
                        binding.jsoupSearchResult.setText("首页无结果");
                    }
                    dispatchHome(document);
                }

                @Override
                public void onError(Throwable e) {
                    binding.jsoupSearchResult.setText("无结果");
                }
            });
        }
    }

    private void dispatchHome(@NonNull Document document) {
        Element chapterElements = document.getElementsByClass("chapter-warp").first();
        Elements links = chapterElements.select("a[href]");
        ArrayList<ChapterBean> list = new ArrayList<>();
        for (Element link : links) {
            list.add(new ChapterBean(link.text(), link.attr("href")));
        }
        ARouter.getInstance()
                .build(ARoutePathConstants.Library.CARTOON_ACTIVITY)
                .withParcelableArrayList(ParamsConstants.ACTIVITY_PARAMS.CHAPTER_LIST, list)
                .navigation();
    }
}
