package com.zht.moduletool.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.base.BaseActivity;
import com.zht.common.cache.BitmapUtils;
import com.zht.moduletool.R;

/**
 * Created by ZhangHaitao on 2018/9/5.
 */
@Route(path = "/sample/BitmapCacheActivity")
public class BitmapCacheActivity extends BaseActivity implements View.OnClickListener {


    private ImageView image;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bitmap_cache;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        image = findViewById(R.id.bitmap_cache_iv);
        image1 = findViewById(R.id.bitmap_cache_iv1);
        image2 = findViewById(R.id.bitmap_cache_iv2);
        image3 = findViewById(R.id.bitmap_cache_iv3);


//                "http://img.zcool.cn/community/01311c59e7206ba801202b0ccee35b.jpg@1280w_1l_2o_100sh.jpg";
//                "http://hbimg.b0.upaiyun.com/a09289289df694cd6157f997ffa017cc44d4ca9e288fb-OehMYA_fw658";
//                "http://img.tupianzj.com/uploads/allimg/160719/9-160GZZ151.jpg";

        String url ="http://www.zhanght.com:8080/bitmap.jpg";
        String url1 = "http://www.zhanght.com:8080/bitmap1.jpg" ;
        String url2 = "http://www.zhanght.com:8080/bitmap2.jpg" ;
        String url3 = "http://www.zhanght.com:8080/bitmap4.jpg" ;

        BitmapUtils.display(this, image, url);
        BitmapUtils.display(this, image1, url1);
        BitmapUtils.display(this, image2, url2);
        BitmapUtils.display(this, image3, url3);
//        Glide.with(this)
//                .load(url)
//                .thumbnail(0.3f)
//                .into(image);
//
//        Glide.with(this)
//                .load(url1)
//                .thumbnail(0.5f)
//                .into(image1);
//
//        Glide.with(this)
//                .load(url2)
//                .thumbnail(0.7f)
//                .into(image2);
//
//        Glide.with(this)
//                .load(url3)
//                .into(image3);


    }

    @Override
    public void onClick(View view) {

    }
}
