package com.zht.common.listUI;

import java.util.List;

/**
 * Created by ZhangHaitao on 2018/9/11.
 * 管理P层和V层的接口
 */

public interface ListContract {

    interface Presenter {
        //获取数据
        void getData(int pager);
    }

    interface View {
        //设置成功数据
        void setData(int currentPage, int totalPages, List mData);
        //设置请求失败
        void setError(String text);
    }

}
