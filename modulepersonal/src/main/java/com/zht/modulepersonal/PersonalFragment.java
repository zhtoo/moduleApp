package com.zht.modulepersonal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.OpenFileBySystem;
import com.zht.common.base.BaseActivity;
import com.zht.common.base.BaseFragment;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.listener.PermissionCallBack;
import com.zht.common.util.Logger;
import com.zht.common.util.uri.UriUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ZhangHaitao on 2018/9/29.
 */
@Route(path = ARoutePathConstants.Personal.PERSONAL_FRAGMENT)
public class PersonalFragment extends BaseFragment implements View.OnClickListener {

    private EditText editText;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        view.findViewById(R.id.bt_home_file_path).setOnClickListener(this);
        view.findViewById(R.id.bt_home_get_file_path).setOnClickListener(this);
        view.findViewById(R.id.bt_to_tran).setOnClickListener(this);

        editText = view.findViewById(R.id.ed_home_file_path);
        editText.setText(Environment.getExternalStorageDirectory().getPath());
    }

    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void initData() {
        //需要在onCreate或onAttach中初始化
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result != null && result.getData() != null) {
                Uri uri = result.getData().getData();



                Logger.e("File Uri: " + uri.toString());
                String path = UriUtils.getAbsolutePath(getContext(), uri);
                if (!TextUtils.isEmpty(path)) {
                    Logger.e("File Path: " + path);
                    editText.setText(path);
                } else {
                    editText.setText("");
                    Logger.e("File Path is null ");
                }
            }

        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_home_file_path) {
            String s = editText.getText().toString();
            OpenFileBySystem.openFileByPath(getContext(), s, Intent.ACTION_SEND);
        } else if (view.getId() == R.id.bt_home_get_file_path) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                showFileChooser();
                return;
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                showFileChooser();
                return;
            }
            if (getActivity() != null && getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},

                        new PermissionCallBack() {
                            @Override
                            public void granted() {
                                showFileChooser();
                            }

                            @Override
                            public void denied() {
                                Toast.makeText(getContext(), "无权限访问相应文件.", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        } else if (view.getId() == R.id.bt_to_tran) {

        }
    }

    public void testBitmap(int resId) {
        Bitmap bitmap = getBitmap(getContext(), resId);
        if (bitmap == null) {
            return;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixelArr = new int[width * height];
        bitmap.getPixels(pixelArr, 0, width, 0, 0, width, height);


        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = pixelArr[i * width + j];
                if (pixel == Color.WHITE) {
                    pixelArr[i * width + j] = Color.TRANSPARENT;
                } else {
                    break;
                }
            }
        }

        for (int i = (width - 1); i >= 0; i--) {
            for (int j = (height - 1); j >= 0; j--) {
                int pixel = pixelArr[i * width + j];
                if (pixel == Color.WHITE) {
                    pixelArr[i * width + j] = Color.TRANSPARENT;
                } else {
                    break;
                }
            }
        }

        Bitmap copy = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        copy.setPixels(pixelArr, 0, width, 0, 0, width, height);
        String exportFilePath = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/dddd_" + ".png";
        if (saveBitmap(copy, exportFilePath, Bitmap.CompressFormat.PNG, 100, true)) {
            OpenFileBySystem.openFileByPath(getContext(), exportFilePath, Intent.ACTION_SEND);
        } else {
            Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean saveBitmap(Bitmap bitmap,
                               String savePath,
                               Bitmap.CompressFormat format,
                               int quality,
                               boolean recycle) {
        File file = new File(savePath);
        if (file.exists()) {
            if (!file.delete()) {
                return false;
            }
        }
        if (bitmap == null) {
            Log.e("ImageUtils", "bitmap is empty.");
            return false;
        }
        if (bitmap.isRecycled()) {
            Log.e("ImageUtils", "bitmap is recycled.");
            return false;
        }

        OutputStream os = null;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = bitmap.compress(format, quality, os);
            if (recycle && !bitmap.isRecycled()) bitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    private Bitmap getBitmap(Context context, int resId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = context.getDrawable(resId);
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            if (bitmap != null) {
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
            }
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        }
        return bitmap;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case FILE_SELECT_CODE:
//                if (resultCode == RESULT_OK) {
//                    // Get the Uri of the selected file
//                    Uri uri = data.getData();
//                    Log.e(TAG, "File Uri: " + uri.toString());
//                    // Get the path
//                    String path = UriUtils.getPath(getContext(), uri);
//                    // Show the path into EditText
//                    if (!TextUtils.isEmpty(path)) {
//                        Log.e(TAG, "File Path: " + path);
//                        editText.setText(path);
//                    } else {
//                        editText.setText("");
//                        Log.e(TAG, "File Path is null ");
//                    }
//                }
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    /**
     * 打开文件选择器
     */
    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            activityResultLauncher.launch(Intent.createChooser(intent, "Select a File to Upload"));
            // startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getContext(), "请安装文件管理器", Toast.LENGTH_SHORT).show();
        }
    }


}
