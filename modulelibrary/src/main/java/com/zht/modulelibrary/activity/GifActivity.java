package com.zht.modulelibrary.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseViewBindingActivity;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.impl.ObserverImpl;
import com.zht.common.util.Logger;
import com.zht.common.util.uri.UriUtils;
import com.zht.modulelibrary.databinding.ActivityGifBinding;
import com.zht.modulelibrary.jsoup.JsoupHelper;

import org.jsoup.nodes.Document;

/**
 * @Date 2023/7/6 14:52
 * @Author zhanghaitao
 * @Description
 */
@Route(path = ARoutePathConstants.Library.GIF_ACTIVITY)
public class GifActivity extends BaseViewBindingActivity<ActivityGifBinding> {

    private ActivityResultLauncher<Intent> activityResultLauncher;

    private String imagePath = "";

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result != null && result.getData() != null) {
                Uri uri = result.getData().getData();
                Logger.e("File Uri: " + uri.toString());
                String path = UriUtils.getAbsolutePath(this, uri);
                if (!TextUtils.isEmpty(path)) {
                    imagePath = path;
                } else {
                    Logger.e("File Path is null ");
                }
            }
        });
    }

    public void clickSelectImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            activityResultLauncher.launch(Intent.createChooser(intent, "Select a File"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(view.getContext(), "请安装文件管理器", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickDecodeImage(View view) {
        if (TextUtils.isEmpty(imagePath)) {
            return;
        }
    }

    public void clickCompressImage(View view) {
        if (TextUtils.isEmpty(imagePath)) {
            return;
        }
    }

}
