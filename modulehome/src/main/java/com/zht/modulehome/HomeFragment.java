package com.zht.modulehome;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.OpenFileBySystem;
import com.zht.common.base.BaseFragment;

import java.net.URISyntaxException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ZhangHaitao on 2018/9/3.
 */
@Route(path = "/modulehome/homefragment")
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private EditText editText;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        view.findViewById(R.id.tv_module_one).setOnClickListener(this);
        view.findViewById(R.id.tv_module_two).setOnClickListener(this);
        view.findViewById(R.id.tv_module_fragment).setOnClickListener(this);
        view.findViewById(R.id.bt_home_file_path).setOnClickListener(this);
        view.findViewById(R.id.bt_home_get_file_path).setOnClickListener(this);

        editText = view.findViewById(R.id.ed_home_file_path);

        // Environment.getExternalStorageDirectory() + "/zerobook"

        editText.setText(Environment.getExternalStorageDirectory()
                + "/zerobook" +
                "/courseFile/");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_module_one) {
            // 1. 应用内简单的activity跳转
            ARouter.getInstance().build("/moduleone/main")
                    .navigation();
        } else if (view.getId() == R.id.tv_module_two) {
            // 2. 应用内简带参数的activity跳转
            ARouter.getInstance().build("/moduletwo/main")
                    .withString("name", "zhanghaitao")
                    .navigation();
        } else if (view.getId() == R.id.tv_module_fragment) {
            // 3. 应用内fragment的获取
            Intent intent = new Intent(getContext(), ShowFragmentActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.bt_home_file_path) {
            String s = editText.getText().toString();
            OpenFileBySystem.openFileByPath(getContext(), s);
        } else if (view.getId() == R.id.bt_home_get_file_path) {
            showFileChooser();
        }
    }

    public static final int FILE_SELECT_CODE = 100;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    // Get the path
                    String path = null;
                    try {
                        path = getPath(getContext(), uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    editText.setText(path);

                    Log.d(TAG, "File Path: " + path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    private static final String TAG = "ChooseFile";


    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it  Or Log it.
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


}
