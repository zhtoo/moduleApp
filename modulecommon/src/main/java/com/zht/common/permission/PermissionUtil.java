package com.zht.common.permission;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.zht.common.R;

import io.reactivex.Observable;

/**
 * @Date 2022/12/28 17:16
 * @Author zhanghaitao
 * @Description
 */
public class PermissionUtil {

    public static Observable<Boolean> request(Fragment fragment, String... permissions) {
        return PermissionBuilder.with(fragment)
                .addPermissions(permissions)
                .request()
                .doOnNext(granted -> {
                    if (!granted) {
                        showPermissionDialog(fragment.getContext());
                    }
                });
    }

    public static Observable<Boolean> request(FragmentActivity fragmentActivity, String... permissions) {
        return PermissionBuilder.with(fragmentActivity)
                .addPermissions(permissions)
                .request()
                .doOnNext(granted -> {
                    if (!granted) {
                        showPermissionDialog(fragmentActivity);
                    }
                });
    }

    public static void showPermissionDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_alert_dialog, null, false);
        view.<TextView>findViewById(R.id.alert_title).setText("权限未授予");
        view.<TextView>findViewById(R.id.alert_content).setText("获取权限失败，请到应用中心授予该权限后，才能使用后功能");
        view.<Button>findViewById(R.id.alert_cancel).setText("取消");
        view.<Button>findViewById(R.id.alert_submit).setText("前往应用中心");

        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
        window.getDecorView().setPadding(padding, 0, padding, 0);
        alertDialog.show();

        view.<Button>findViewById(R.id.alert_cancel).setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        view.<Button>findViewById(R.id.alert_submit).setOnClickListener(v -> {
            alertDialog.dismiss();
            AppSettingTools.jumpAppSettingPage(v.getContext());
        });
    }

}
