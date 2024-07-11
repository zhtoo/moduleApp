package com.zht.modulelibrary.activity.cartoon;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.view_binding.BaseViewBindingActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.constant.ParamsConstants;
import com.zht.common.impl.ObserverImpl;
import com.zht.modulelibrary.adapter.CartoonWatchAdapter;
import com.zht.modulelibrary.bean.ChapterBean;
import com.zht.modulelibrary.databinding.ActivityCartoonWatchBinding;
import com.zht.modulelibrary.jsoup.JsoupHelper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * @Date 2022/12/16 16:09
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Library.CARTOON_WATCH_ACTIVITY)
public class CartoonWatchActivity extends BaseViewBindingActivity<ActivityCartoonWatchBinding> {


    @Autowired(name = ParamsConstants.ACTIVITY_PARAMS.CHAPTER_LIST)
    ArrayList<ChapterBean> mChapterList;

    @Autowired(name = ParamsConstants.ACTIVITY_PARAMS.POSITION)
    int position;

    CartoonWatchAdapter mAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (binding == null) {
            return;
        }
        binding.cartoonWatchRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CartoonWatchAdapter();
        binding.cartoonWatchRv.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        if (mChapterList == null || mChapterList.isEmpty()) {
            return;
        }
        loadCartoon();
    }

    public void loadCartoon() {
        ChapterBean bean = mChapterList.get(position);
        binding.tvChapterName.setText(bean.getChapterName());
        JsoupHelper.loadFormUrl(bean.getChapterUrl(), new ObserverImpl<Document>() {

            @Override
            public void onNext(Document document) {
                if (document == null) {
                    return;
                }
                dispatchWatchResult(document, bean);
            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }

    private void dispatchWatchResult(@NonNull Document document, ChapterBean bean) {
        Elements images = document.select("img[src~=(?i)\\.(png|jpg|jpe?g|gif)]");
        ArrayList<String> list = new ArrayList<>();
        for (Element image : images) {
            String alt = image.attr("alt");
            if (!TextUtils.isEmpty(alt) && alt.contains(bean.getChapterName())) {
                Log.e("cartoon",alt);
                String src = image.attr("src");
                String dataSrc = image.attr("data-src");
                if(TextUtils.isEmpty(src) && TextUtils.isEmpty(dataSrc)){
                    continue;
                }
                if (!TextUtils.isEmpty(src)) {
                    if (src.endsWith(".gif")) {
                        Log.e("cartoon",dataSrc);
                        list.add(dataSrc);
                    } else {
                        Log.e("cartoon",src);
                        list.add(src);
                    }
                } else if (TextUtils.isEmpty(src) && !TextUtils.isEmpty(dataSrc)) {
                    Log.e("cartoon",dataSrc);
                    list.add(dataSrc);
                }
            }
        }
        mAdapter.setNewData(list);
        binding.cartoonWatchRv.smoothScrollToPosition(0);

    }

    public void clickPre(View view) {
        position--;
        if (position >= 0) {
            loadCartoon();
        } else {
            Toast.makeText(this, "已经是第一章了", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickNext(View view) {
        position++;
        if (position < mChapterList.size()) {
            loadCartoon();
        } else {
            Toast.makeText(this, "没有下一章了", Toast.LENGTH_SHORT).show();
        }
    }

}
