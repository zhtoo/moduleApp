package com.zht.moduleview.qucikindex;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.moduleview.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by ZhangHaitao on 2019/4/18
 */
@Route(path = ARoutePathConstants.View.QUICK_INDEX_ACTIVITY)
public class QuickIndexActivity  extends AppCompatActivity implements CustomAdapter.OnItemCilckListener {
    ListView listview;
    TextView tv_letter;
    ArrayList<Custom> customs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_index);
        listview = (ListView) findViewById(R.id.listview);
        tv_letter = (TextView) findViewById(R.id.tv_letter);


        QuickIndexBar quickIndexBar = (QuickIndexBar) findViewById(R.id.quiclIndexBar);

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


        //准备数据
        prepareData();
        //对集合数据按照拼音进行排序
        Collections.sort(customs);

        CustomAdapter adapter = new CustomAdapter(customs);
        //给listview填充数据
        listview.setAdapter(adapter);
        adapter.setItemCilckListener(this);


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
        // 虚拟数据
        customs.add(new Custom("李伟"));
        customs.add(new Custom("张三"));
        customs.add(new Custom("阿三"));
        customs.add(new Custom("阿四"));
        customs.add(new Custom("段誉"));
        customs.add(new Custom("段正淳"));
        customs.add(new Custom("张三丰"));
        customs.add(new Custom("陈坤"));
        customs.add(new Custom("林俊杰1"));
        customs.add(new Custom("陈坤2"));
        customs.add(new Custom("王二a"));
        customs.add(new Custom("刘杰"));
        customs.add(new Custom("林俊杰a"));
        customs.add(new Custom("张四"));
        customs.add(new Custom("林俊杰"));

        customs.add(new Custom("\uD83C\uDF1F nana\uD83C\uDF1F下周一飞韩国"));
        customs.add(new Custom("王二"));

        customs.add(new Custom("王二b"));
        customs.add(new Custom("赵四"));
        customs.add(new Custom("杨坤"));
        customs.add(new Custom("赵子龙"));
        customs.add(new Custom("杨坤1"));
        customs.add(new Custom("李伟1"));
        customs.add(new Custom("宋江"));
        customs.add(new Custom("宋江1"));
        customs.add(new Custom("李伟3"));
        customs.add(new Custom("吖"));
        customs.add(new Custom("不知道"));
        customs.add(new Custom("蔡明"));
        customs.add(new Custom("董不"));
        customs.add(new Custom("额"));
        customs.add(new Custom("发"));
        customs.add(new Custom("个"));
        customs.add(new Custom("和"));
        customs.add(new Custom("int"));
        customs.add(new Custom("就"));
        customs.add(new Custom("看"));
        customs.add(new Custom("了"));
        customs.add(new Custom("没"));
        customs.add(new Custom("你"));
        customs.add(new Custom("哦"));
        customs.add(new Custom("怕"));
        customs.add(new Custom("去"));
        customs.add(new Custom("人"));
        customs.add(new Custom("是"));
        customs.add(new Custom("他"));
        customs.add(new Custom("use"));
        customs.add(new Custom("view"));
        customs.add(new Custom("我"));
        customs.add(new Custom("下"));
        customs.add(new Custom("有"));
        customs.add(new Custom("在"));
    }

    @Override
    public void onItemCilck(int position) {
        Toast.makeText(this, "条目" + position, Toast.LENGTH_SHORT).show();
    }
}