package com.zht.common.permission;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @Date 2022/3/20
 * @Author ZhangHaitao
 * @Description
 */
public class AppSettingTools {

    private static final String TAG = AppSettingTools.class.getSimpleName();

    static void toast(Context context, String message) {
        if (null == context) {
            return;
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 跳转app设置界面
     * @param context
     */
    public static void jumpAppSettingPage(Context context) {
        String name = Build.MANUFACTURER;
        switch (name) {
            case "HUAWEI":
                goHuaWeiManager(context);
                break;
            case "vivo":
                goVivoManager(context);
                break;
            case "OPPO":
                goOppoManager(context);
                break;
            case "Coolpad":
                goCoolpadManager(context);
                break;
            case "Meizu":
                goMeizuManager(context);
                break;
            case "Xiaomi":
                goXiaoMiManager(context);
                break;
            case "samsung":
                goSamsungManager(context);
                break;
            case "Sony":
                goSonyManager(context);
                break;
            case "LG":
                goLGManager(context);
                break;
            default:
                goOtherIntentSetting(context);
                break;
        }
    }

    private static void goHuaWeiManager(Context context) {
        try {
            Intent intent = new Intent(context.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            goOtherIntentSetting(context);
        }
    }

    private static void goVivoManager(Context context) {
        //<intent-filter> in place for this activity, but they marked it as not exported
        //com.vivo.permissionmanager/com.vivo.permissionmanager.activity.SoftPermissionDetailActivity/
        doStartApplicationWithPackageName(context, "com.vivo.permissionmanager");
    }

    private static void goOppoManager(Context context) {
        doStartApplicationWithPackageName(context, "com.coloros.safecenter");
    }

    private static void goCoolpadManager(Context context) {
        // doStartApplicationWithPackageName("com.yulong.android.security:remote")
        // 和Intent open = getPackageManager().getLaunchIntentForPackage("com.yulong.android.security:remote");
        doStartApplicationWithPackageName(context, "com.yulong.android.security:remote");
    }

    private static void goMeizuManager(Context context) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", context.getPackageName());
            context.startActivity(intent);
        } catch (Exception e) {
            goOtherIntentSetting(context);
        }
    }

    private static void goXiaoMiManager(Context context) {
        try {
            String rom = getMiuiVersion();
            Intent intent = new Intent();
            if ("V5".equals(rom) || "V6".equals(rom) || "V7".equals(rom)) {
                intent.setAction("miui.intent.action.APP_PERM_EDITOR");
                intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                intent.putExtra("extra_pkgname", context.getPackageName());
            } else if ("V8".equals(rom) || "V9".equals(rom) || "V10".equals(rom)) {
                intent.setAction("miui.intent.action.APP_PERM_EDITOR");
                intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                intent.putExtra("extra_pkgname", context.getPackageName());
            } else {
                goOtherIntentSetting(context);
                return;
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            goOtherIntentSetting(context);
        }
    }

    private static String getMiuiVersion() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    private static void goSamsungManager(Context context) {
        goOtherIntentSetting(context);
    }

    private static void goSonyManager(Context context) {
        try {
            Intent intent = new Intent(context.getPackageName());
            ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            goOtherIntentSetting(context);
        }
    }

    private static void goLGManager(Context context) {
        try {
            Intent intent = new Intent(context.getPackageName());
            ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            goOtherIntentSetting(context);
        }
    }

    private static void doStartApplicationWithPackageName(Context context, String packagename) {
        PackageInfo packageinfo = null;
        try {
            packageinfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            goOtherIntentSetting(context);
            Log.e("PermissionPageManager", "PackageManager.NameNotFoundException");
            return;
        }
        if (packageinfo == null) {
            goOtherIntentSetting(context);
            Log.e("PermissionPageManager", "packageinfo == null");
            return;
        }

        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);
        List<ResolveInfo> resolveinfoList = context.getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        Log.e("PermissionPageManager", "resolveinfoList" + resolveinfoList.size());
        for (int i = 0; i < resolveinfoList.size(); i++) {
            Log.e("PermissionPageManager", resolveinfoList.get(i).activityInfo.packageName + resolveinfoList.get(i).activityInfo.name);
        }
        if (resolveinfoList.iterator().hasNext()) {
            ResolveInfo resolveinfo = resolveinfoList.iterator().next();
            if (resolveinfo != null) {
                String packageName = resolveinfo.activityInfo.packageName;
                String className = resolveinfo.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                try {
                    context.startActivity(intent);
                } catch (Exception e) {
                    goOtherIntentSetting(context);
                }
            }
        } else {
            goOtherIntentSetting(context);
        }
    }

    private static void goOtherIntentSetting(Context context) {
        Intent intent = getAppManageIntent(context);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            goHomeSetting(context);
        }
    }

    private static Intent getAppManageIntent(Context context) {
        Intent intent;
        try {
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
        } catch (Exception e) {
            intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
        }
        return intent;
    }

    private static void goHomeSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            toast(context, "跳转到应用市场失败");
            e.printStackTrace();
        }
    }

}
