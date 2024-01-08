package com.zht.common.spannable;

import java.util.List;

/**
 * @Date 2023/7/28 17:54
 * @Author zhanghaitao
 * @Description 1、文字 大小 颜色 背景 粗体/斜体/默认 上画线 下划线 点击
 * 2、图片 点击
 */
public class RichText {

    private final static String PLACEHOLDER = "-";

    private int start = 0;
    private int lastStart = 0;
    private int end = 0;

    private List<DesSpanBean> spanList;
    private StringBuffer textBuffer;

    private String originalText;

    public static RichText init() {
        return new RichText();
    }

    public static RichText init(String originalText) {
        return new RichText();
    }


}
