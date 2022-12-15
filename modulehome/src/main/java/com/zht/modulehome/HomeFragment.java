package com.zht.modulehome;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zht.common.OpenFileBySystem;
import com.zht.common.base.BaseActivity;
import com.zht.common.base.BaseFragment;
import com.zht.common.listener.PermissionCallBack;
import com.zht.common.util.UriUtils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ZhangHaitao on 2018/9/3.
 */
@Route(path = "/modulehome/homefragment")
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private EditText editText;

    private static final int FILE_SELECT_CODE = 100;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 101;

    private static final String TAG = "ChooseFile";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        view.findViewById(R.id.bt_home_file_path).setOnClickListener(this);
        view.findViewById(R.id.bt_home_get_file_path).setOnClickListener(this);

        editText = view.findViewById(R.id.ed_home_file_path);
        editText.setText(Environment.getExternalStorageDirectory().getPath());
    }

    @Override
    public void onClick(View view) {
          if (view.getId() == R.id.bt_home_file_path) {
            String s = editText.getText().toString();
            OpenFileBySystem.openFileByPath(getContext(), s);
        } else if (view.getId() == R.id.bt_home_get_file_path) {
            if (getActivity() != null && getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_READ_EXTERNAL_STORAGE,
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
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.e(TAG, "File Uri: " + uri.toString());
                    // Get the path
                    String path = UriUtils.getPath(getContext(), uri);
                    // Show the path into EditText
                    if (!TextUtils.isEmpty(path)) {
                        Log.e(TAG, "File Path: " + path);
                        editText.setText(path);
                    } else {
                        editText.setText("");
                        Log.e(TAG, "File Path is null ");
                    }
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

}
