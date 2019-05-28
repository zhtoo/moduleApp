package com.zht.moduleview.qucikindex;

import android.util.Log;

/**
 * Created by ZhangHaitao on 2019/4/18
 */
public class Custom implements Comparable<Custom> {
    public String TAG = "Custom";
    public String name;
    public String pinyin;

    public Custom() {
    }

    public Custom(String name) {
        this.name = name;
        this.pinyin = PinYinUtil.getPinyin(name.trim());
        Log.e(TAG, name + "=" + pinyin);
    }

    @Override
    public int compareTo(Custom another) {
        return pinyin.compareTo(another.pinyin);
    }

    /**
     * 排序
     */
//    public ArrayList<Custom> Collections(ArrayList<Custom> customs) {
//        Collections
//                .sort(customs, new Comparator<Custom>() {
//
//                    public int compare(Custom o1, Custom o2) {
//                        String s1 = o1.pinyin.charAt(0) + "";
//                        s1 = s1.toUpperCase();
//                        String s2 = o2.pinyin.charAt(0) + "";
//                        s2 = s2.toUpperCase();
//                        int i = s1.charAt(0) - s2.charAt(0);
//                        return i;
//                    }
//                });
//        return customs;
//    }
}
