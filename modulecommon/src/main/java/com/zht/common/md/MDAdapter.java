package com.zht.common.md;

import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Date 2023/1/13 09:28
 * @Author zhanghaitao
 * @Description 1、标题
 * # 一级标题
 * ## 二级标题
 * ### 三级标题
 * #### 四级标题
 * ##### 五级标题
 * ###### 六级标题
 * 2、内容
 * 3、加粗
 * 4、斜体
 * 5、超链接 []()
 * 6、表格
 * 7、代码格式
 * 、、、java
 * 、、、
 * 8、区块引用：在段落开头使用 > 符号加空格
 * 9、图片 ![]()
 */
public class MDAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void test(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        if (text.startsWith("#")) {//标题

        } else if (text.startsWith(">")) {// 内容引用

        } else if (text.startsWith("、、、")) {// 代码

        } else if (text.startsWith("![")) {// 图片

        }


    }
}
