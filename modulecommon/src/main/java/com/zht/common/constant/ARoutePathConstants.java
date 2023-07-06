package com.zht.common.constant;

/**
 * @Date 2022/12/15 17:53
 * @Author zhanghaitao
 * @Description
 */
public class ARoutePathConstants {

    public interface Home {
        String MODULE = "/moduleHome";
        String MODULE_PACKAGE_NAME = "com.zht.modulehome";
        String HOME_FRAGMENT = MODULE + "/HomeFragment";

        String CAMERAX_ACTIVITY = MODULE + "/activity/CameraXActivity";

        String CAMERAX_PREVIEW_ACTIVITY = MODULE + "/activity/camera/CameraPreviewActivity";
        String CAMERAX_PICTURE_ACTIVITY = MODULE + "/activity/camera/CameraPictureActivity";
        String CAMERAX_VIDEO_ACTIVITY = MODULE + "/activity/camera/CameraVideoActivity";

        String DATA_STORE_ACTIVITY = MODULE + "/activity/DataStoreActivity";

        String JETPACK_ACTIVITY = MODULE + "/activity/JetpackActivity";
        String COMPOSE_ACTIVITY = MODULE + "/activity/ComposeActivity";
        String HILT_ACTIVITY = MODULE + "/activity/HiltActivity";
        String SECURITY_ACTIVITY = MODULE + "/activity/SecurityActivity";
        String BOTTOM_TAB_ACTIVITY = MODULE + "/activity/BottomTabActivity";
    }

    public interface Tool {
        String MODULE = "/moduleTool";
        String MODULE_PACKAGE_NAME = "com.zht.moduletool";

        String TOOLS_FRAGMENT = MODULE + "/ToolsFragment";

        String TOAST_ACTIVITY = MODULE + "/activity/ToastActivity";
        String STATUS_BAR_ACTIVITY = MODULE + "/activity/StatusBarActivity";
        String BITMAP_CACHE_ACTIVITY = MODULE + "/activity/BitmapCacheActivity";
        String VIDEO_ACTIVITY = MODULE + "/activity/VideoActivity";
        String PERMISSION_REQUEST_ACTIVITY = MODULE + "/activity/PermissionRequestActivity";
        String SCREEN_ACTIVITY = MODULE + "/activity/ScreenActivity";
        String INTERACTIVE_VIDEO_ACTIVITY = MODULE + "/activity/InteractiveVideoActivity";
        String STORAGE_ACTIVITY = MODULE + "/activity/StorageActivity";
        String TRIGONOMETRIC_FUNCTIONS_ACTIVITY = MODULE + "/activity/TrigonometricFunctionsActivity";

    }


    public interface Library {
        String MODULE = "/moduleLibrary";
        String MODULE_PACKAGE_NAME = "com.zht.modulelibrary";
        String LIBRARY_FRAGMENT = MODULE + "/LibraryFragment";
        String JSOUP_ACTIVITY = MODULE + "/activity/JsoupActivity";
        String CARTOON_ACTIVITY = MODULE + "/activity/cartoon/CartoonActivity";
        String CARTOON_WATCH_ACTIVITY = MODULE + "/activity/cartoon/CartoonWatchActivity";
        String ZXing_ACTIVITY = MODULE + "/activity/ZXingActivity";
        String HEART_RATE_ACTIVITY = MODULE + "/activity/HeartRateActivity";
        String WIFI_ACTIVITY = MODULE + "/activity/WifiActivity";
        String GIF_ACTIVITY = MODULE + "/activity/GifActivity";
    }

    public interface View {
        String MODULE = "/moduleView";
        String MODULE_PACKAGE_NAME = "com.zht.moduleview";
        String VIEW_FRAGMENT = MODULE + "/ViewFragment";

        String CUSTOMISE_VIEW_ACTIVITY = MODULE + "/activity/CustomiseViewActivity";
        String SYSTEM_VIEW_ACTIVITY = MODULE + "/activity/SystemViewActivity";

        String CALENDAR_ACTIVITY = MODULE + "/CalendarActivity";
        String DRAG_ACTIVITY = MODULE + "/DragActivity";
        String IMAGE_ACTIVITY = MODULE + "/ImageActivity";
        String VERIFY_CODE_EDIT_ACTIVITY = MODULE + "/VerifyCodeEditActivity";
        String QUICK_INDEX_ACTIVITY = MODULE + "/QuickIndexActivity";

        String CUSTOM_CARD_VIEW_ACTIVITY = MODULE + "/CustomCardViewActivity";
        String EDITTEXT_ACTIVITY = MODULE + "/EditTextActivity";
        String PROGRESS_ACTIVITY = MODULE + "/ProgressActivity";
        String SCALENDAR_ACTIVITY = MODULE + "/SCalendarActivity";
        String VIEWPAGER_ACTIVITY = MODULE + "/ViewPagerActivity";


    }

    public interface Personal {
        String MODULE = "/modulePersonal";
        String MODULE_PACKAGE_NAME = "com.zht.modulepersonal";
        String PERSONAL_FRAGMENT = MODULE + "/PersonalFragment";


    }


}
