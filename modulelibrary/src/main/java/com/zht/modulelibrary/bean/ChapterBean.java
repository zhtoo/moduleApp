package com.zht.modulelibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Date 2022/12/16 16:06
 * @Author zhanghaitao
 * @Description
 */
public class ChapterBean implements Parcelable {

    private String chapterName;
    private String chapterUrl;

    public ChapterBean() {

    }

    public ChapterBean(String chapterName, String chapterUrl) {
        this.chapterName = chapterName;
        this.chapterUrl = chapterUrl;
    }

    protected ChapterBean(Parcel in) {
        chapterName = in.readString();
        chapterUrl = in.readString();
    }

    public static final Creator<ChapterBean> CREATOR = new Creator<ChapterBean>() {
        @Override
        public ChapterBean createFromParcel(Parcel in) {
            return new ChapterBean(in);
        }

        @Override
        public ChapterBean[] newArray(int size) {
            return new ChapterBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chapterName);
        dest.writeString(chapterUrl);
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getChapterUrl() {
        return chapterUrl;
    }

    public void setChapterUrl(String chapterUrl) {
        this.chapterUrl = chapterUrl;
    }
}
