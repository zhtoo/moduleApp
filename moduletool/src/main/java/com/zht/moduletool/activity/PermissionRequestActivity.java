package com.zht.moduletool.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zht.common.constant.ARoutePathConstants;
import com.zht.common.permission.PermissionBuilder;
import com.zht.common.permission.PermissionUtil;
import com.zht.common.util.ToastUtil;
import com.zht.moduletool.R;

import java.util.List;

/**
 * Created by ZhangHaitao on 2018/10/15
 */
@Route(path = ARoutePathConstants.Tool.PERMISSION_REQUEST_ACTIVITY)
public class PermissionRequestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_permission);

        findViewById(R.id.btn_main_request_single).setOnClickListener(this);
        findViewById(R.id.btn_main_request_group).setOnClickListener(this);
        findViewById(R.id.btn_main_request_location).setOnClickListener(this);
        findViewById(R.id.btn_main_request_sensors).setOnClickListener(this);
        findViewById(R.id.btn_main_request_activity_recognition).setOnClickListener(this);
        findViewById(R.id.btn_main_request_bluetooth).setOnClickListener(this);
        findViewById(R.id.btn_main_request_wifi).setOnClickListener(this);
        findViewById(R.id.btn_main_request_read_media_location).setOnClickListener(this);
        findViewById(R.id.btn_main_request_media_read).setOnClickListener(this);
        findViewById(R.id.btn_main_request_manage_storage).setOnClickListener(this);
        findViewById(R.id.btn_main_request_install).setOnClickListener(this);
        findViewById(R.id.btn_main_request_window).setOnClickListener(this);
        findViewById(R.id.btn_main_request_setting).setOnClickListener(this);
        findViewById(R.id.btn_main_request_notification).setOnClickListener(this);
        findViewById(R.id.btn_main_request_post_notification).setOnClickListener(this);
        findViewById(R.id.btn_main_request_notification_listener).setOnClickListener(this);
        findViewById(R.id.btn_main_request_package).setOnClickListener(this);
        findViewById(R.id.btn_main_request_alarm).setOnClickListener(this);
        findViewById(R.id.btn_main_request_not_disturb).setOnClickListener(this);
        findViewById(R.id.btn_main_request_ignore_battery).setOnClickListener(this);
        findViewById(R.id.btn_main_request_picture_in_picture).setOnClickListener(this);
        findViewById(R.id.btn_main_request_open_vpn).setOnClickListener(this);
        findViewById(R.id.btn_main_app_details).setOnClickListener(this);

    }


    public void toast(String text) {
        ToastUtil.showToast(text);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_main_request_single) {

            PermissionUtil.request(this, Manifest.permission.CAMERA)
                    .subscribe(granted -> {
                        if (granted) {
                            toast("同意");
                        } else {
                            toast("拒绝");
                        }
                    });

        } else if (viewId == R.id.btn_main_request_group) {
//
//            XXPermissions.with(this)
//                    .permission(Permission.RECORD_AUDIO)
//                    .permission(Permission.Group.CALENDAR)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                        }
//                    });

        } else if (viewId == R.id.btn_main_request_location) {
//
//            XXPermissions.with(this)
//                    .permission(Permission.ACCESS_COARSE_LOCATION)
//                    .permission(Permission.ACCESS_FINE_LOCATION)
//                    // 如果不需要在后台使用定位功能，请不要申请此权限
//                    .permission(Permission.ACCESS_BACKGROUND_LOCATION)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                        }
//                    });

        } else if (viewId == R.id.btn_main_request_sensors) {
//
//            XXPermissions.with(this)
//                    .permission(Permission.BODY_SENSORS)
//                    .permission(Permission.BODY_SENSORS_BACKGROUND)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                        }
//                    });

        } else if (viewId == R.id.btn_main_request_activity_recognition) {

//            XXPermissions.with(this)
//                    .permission(Permission.ACTIVITY_RECOGNITION)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                            addCountStepListener();
//                        }
//                    });

        } else if (viewId == R.id.btn_main_request_bluetooth) {

//            long delayMillis = 0;
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
//                delayMillis = 2000;
//                toast(getString(R.string.demo_android_12_bluetooth_permission_hint));
//            }
//
//            view.postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    XXPermissions.with(MainActivity.this)
//                            .permission(Permission.BLUETOOTH_SCAN)
//                            .permission(Permission.BLUETOOTH_CONNECT)
//                            .permission(Permission.BLUETOOTH_ADVERTISE)
//                            .interceptor(new PermissionInterceptor())
//                            .request(new OnPermissionCallback() {
//
//                                @Override
//                                public void onGranted(@NonNull List<String> permissions, boolean all) {
//                                    if (!all) {
//                                        return;
//                                    }
//                                    toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                            PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                                }
//                            });
//                }
//            }, delayMillis);

        } else if (viewId == R.id.btn_main_request_wifi) {
//
//            long delayMillis = 0;
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
//                delayMillis = 2000;
//                toast(getString(R.string.demo_android_13_wifi_permission_hint));
//            }
//
//            view.postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    XXPermissions.with(MainActivity.this)
//                            .permission(Permission.NEARBY_WIFI_DEVICES)
//                            .interceptor(new PermissionInterceptor())
//                            .request(new OnPermissionCallback() {
//
//                                @Override
//                                public void onGranted(@NonNull List<String> permissions, boolean all) {
//                                    if (!all) {
//                                        return;
//                                    }
//                                    toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                            PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                                }
//                            });
//                }
//            }, delayMillis);

        } else if (viewId == R.id.btn_main_request_read_media_location) {

//            long delayMillis = 0;
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
//                delayMillis = 2000;
//                toast(getString(R.string.demo_android_10_read_media_location_permission_hint));
//            }
//
//            view.postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    XXPermissions.with(MainActivity.this)
//                            // Permission.READ_EXTERNAL_STORAGE 和 Permission.MANAGE_EXTERNAL_STORAGE 二选一
//                            // 如果 targetSdk >= 33，则添加 Permission.READ_MEDIA_IMAGES 和 Permission.MANAGE_EXTERNAL_STORAGE 二选一
//                            // 如果 targetSdk < 33，则添加 Permission.READ_EXTERNAL_STORAGE 和 Permission.MANAGE_EXTERNAL_STORAGE 二选一
//                            .permission(Permission.READ_MEDIA_IMAGES)
//                            .permission(Permission.ACCESS_MEDIA_LOCATION)
//                            .interceptor(new PermissionInterceptor())
//                            .request(new OnPermissionCallback() {
//
//                                @Override
//                                public void onGranted(@NonNull List<String> permissions, boolean all) {
//                                    if (!all) {
//                                        return;
//                                    }
//                                    toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                            PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                                    new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            getAllImagesFromGallery();
//                                        }
//                                    }).start();
//                                }
//                            });
//                }
//            }, delayMillis);

        } else if (viewId == R.id.btn_main_request_media_read) {

//            long delayMillis = 0;
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
//                delayMillis = 2000;
//                toast(getString(R.string.demo_android_13_read_media_permission_hint));
//            }
//
//            view.postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    XXPermissions.with(MainActivity.this)
//                            // 不适配分区存储应该这样写
//                            //.permission(Permission.MANAGE_EXTERNAL_STORAGE)
//                            // 适配分区存储应该这样写
//                            .permission(Permission.READ_MEDIA_IMAGES)
//                            .permission(Permission.READ_MEDIA_VIDEO)
//                            .permission(Permission.READ_MEDIA_AUDIO)
//                            .interceptor(new PermissionInterceptor())
//                            .request(new OnPermissionCallback() {
//
//                                @Override
//                                public void onGranted(@NonNull List<String> permissions, boolean all) {
//                                    if (!all) {
//                                        return;
//                                    }
//                                    toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                            PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                                }
//                            });
//                }
//            }, delayMillis);

        } else if (viewId == R.id.btn_main_request_manage_storage) {
//
//            long delayMillis = 0;
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
//                delayMillis = 2000;
//                toast(getString(R.string.demo_android_11_manage_storage_permission_hint));
//            }
//
//            view.postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    XXPermissions.with(MainActivity.this)
//                            // 适配分区存储应该这样写
//                            //.permission(Permission.Group.STORAGE)
//                            // 不适配分区存储应该这样写
//                            .permission(Permission.MANAGE_EXTERNAL_STORAGE)
//                            .interceptor(new PermissionInterceptor())
//                            .request(new OnPermissionCallback() {
//
//                                @Override
//                                public void onGranted(@NonNull List<String> permissions, boolean all) {
//                                    if (!all) {
//                                        return;
//                                    }
//                                    toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                            PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                                }
//                            });
//                }
//            }, delayMillis);

        } else if (viewId == R.id.btn_main_request_install) {

//            XXPermissions.with(this)
//                    .permission(Permission.REQUEST_INSTALL_PACKAGES)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                        }
//                    });

        } else if (viewId == R.id.btn_main_request_window) {

//            XXPermissions.with(this)
//                    .permission(Permission.SYSTEM_ALERT_WINDOW)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                        }
//                    });

        } else if (viewId == R.id.btn_main_request_setting) {

//            XXPermissions.with(this)
//                    .permission(Permission.WRITE_SETTINGS)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                        }
//                    });

        } else if (viewId == R.id.btn_main_request_notification) {

//            XXPermissions.with(this)
//                    .permission(Permission.NOTIFICATION_SERVICE)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                        }
//                    });

        } else if (viewId == R.id.btn_main_request_post_notification) {
//
//            long delayMillis = 0;
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
//                delayMillis = 2000;
//                toast(getString(R.string.demo_android_13_post_notification_permission_hint));
//            }
//
//            view.postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    XXPermissions.with(MainActivity.this)
//                            .permission(Permission.POST_NOTIFICATIONS)
//                            .interceptor(new PermissionInterceptor())
//                            .request(new OnPermissionCallback() {
//
//                                @Override
//                                public void onGranted(@NonNull List<String> permissions, boolean all) {
//                                    if (!all) {
//                                        return;
//                                    }
//                                    toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                            PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                                }
//                            });
//                }
//            }, delayMillis);

        } else if (viewId == R.id.btn_main_request_notification_listener) {
//
//            XXPermissions.with(this)
//                    .permission(Permission.BIND_NOTIFICATION_LISTENER_SERVICE)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//                                toggleNotificationListenerService();
//                            }
//                        }
//                    });

        } else if (viewId == R.id.btn_main_request_package) {
//
//            XXPermissions.with(this)
//                    .permission(Permission.PACKAGE_USAGE_STATS)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                        }
//                    });

        } else if (viewId == R.id.btn_main_request_alarm) {
//
//            XXPermissions.with(this)
//                    .permission(Permission.SCHEDULE_EXACT_ALARM)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                        }
//                    });

        } else if (viewId == R.id.btn_main_request_not_disturb) {
//
//            XXPermissions.with(this)
//                    .permission(Permission.ACCESS_NOTIFICATION_POLICY)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                        }
//                    });

        } else if (viewId == R.id.btn_main_request_ignore_battery) {
//
//            XXPermissions.with(this)
//                    .permission(Permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                        }
//                    });

        } else if (viewId == R.id.btn_main_request_picture_in_picture) {
//
//            XXPermissions.with(this)
//                    .permission(Permission.PICTURE_IN_PICTURE)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                        }
//                    });

        } else if (viewId == R.id.btn_main_request_open_vpn) {
//
//            XXPermissions.with(this)
//                    .permission(Permission.BIND_VPN_SERVICE)
//                    .interceptor(new PermissionInterceptor())
//                    .request(new OnPermissionCallback() {
//
//                        @Override
//                        public void onGranted(@NonNull List<String> permissions, boolean all) {
//                            if (!all) {
//                                return;
//                            }
//                            toast(String.format(getString(R.string.demo_obtain_permission_success_hint),
//                                    PermissionNameConvert.getPermissionString(MainActivity.this, permissions)));
//                        }
//                    });

        } else if (viewId == R.id.btn_main_app_details) {

//            XXPermissions.startPermissionActivity(this);
        }
    }
}
