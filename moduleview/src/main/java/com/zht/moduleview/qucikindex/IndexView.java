package com.zht.moduleview.qucikindex;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zht.moduleview.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ZhangHaitao on 2019/4/18
 */
public class IndexView implements CustomAdapter.OnItemCilckListener {

    public View getView() {
        return view;
    }

    private View view;

    private ListView listview;
    private TextView tv_letter;
    private ArrayList<Custom> customs = new ArrayList<>();
    private Context context;
    private List<String> customstr;
    private QuickIndexBar quickIndexBar;


    public IndexView(Context context, List<String> customstr) {
        this.context = context;
        this.customstr = customstr;
        view = initView();
        intData();
        initListener();
    }

    private void initListener() {
        //listview.setItemChecked(context);
    }

    private void intData() {
        quickIndexBar.setOnLetterChangeListener(new QuickIndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                //根据letter找到列表中首字母和letter相同的条目，然后置顶
                for (int i = 0; i < customs.size(); i++) {
                    String firstWord = customs.get(i).pinyin.charAt(0) + "";
                    if (firstWord.equalsIgnoreCase(letter)) {
                        //说明找到了和letter同样字母的条目
                        listview.setSelection(i);
                        //找到立即中断
                        break;
                    }
                }
                //显示出字母
                showLetter(letter);
            }
        });
        //转换数据
        prepareData();

        Collections.sort(customs);

        CustomAdapter adapter = new CustomAdapter(customs);
        //给listview填充数据
        listview.setAdapter(adapter);
        adapter.setItemCilckListener(this);
    }

    @Override
    public void onItemCilck(int position) {
        Toast.makeText(context, "条目" + position, Toast.LENGTH_SHORT).show();
    }

    private View initView() {
        View view = LayoutInflater
                .from(context).inflate(R.layout.quick_index, null);
        listview = (ListView) view.findViewById(R.id.listview);
        tv_letter = (TextView) view.findViewById(R.id.tv_letter);
        quickIndexBar = (QuickIndexBar) view.findViewById(R.id.quiclIndexBar);
        return view;
    }

    private Handler handler = new Handler();

    /**
     * 显示字母
     *
     * @param letter
     */
    private void showLetter(String letter) {
        //一开始就先移除之前的任务
        handler.removeCallbacksAndMessages(null);
        tv_letter.setText(letter);
        tv_letter.setVisibility(View.VISIBLE);
        //延时隐藏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_letter.setVisibility(View.GONE);
            }
        }, 1000);
    }


    private void prepareData() {
        // 数据
        for (String str : customstr) {
            customs.add(new Custom(str));
        }
        //对集合数据按照拼音进行排序
       // customs = new Custom().Collections(customs);
    }
}