package com.zht.common.spannable;

/**
 * @Date 2023/7/28 17:59
 * @Author zhanghaitao
 * @Description
 */
public class DesSpanBean {

    private Object span;
    private int start;
    private int end;
    private int flags;

    public DesSpanBean(Object span, int start, int end, int flags) {
        this.span = span;
        this.start = start;
        this.end = end;
        this.flags = flags;
    }

    public Object getSpan() {
        return span;
    }


    public int getStart() {
        return start;
    }


    public int getEnd() {
        return end;
    }


    public int getFlags() {
        return flags;
    }

}
