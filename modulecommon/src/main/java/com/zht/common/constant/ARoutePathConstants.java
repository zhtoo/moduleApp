package com.zht.common.constant;

/**
 * @Date 2022/12/15 17:53
 * @Author zhanghaitao
 * @Description
 */
public class ARoutePathConstants {

    public interface Home {
        String MODULE = "/moduleHome";
        String HOME_FRAGMENT = MODULE + "/HomeFragment";
    }

    public interface Tool {
        String MODULE = "/moduleTool";

        String TOOLS_FRAGMENT = MODULE + "/ToolsFragment";


        String TOAST_ACTIVITY = MODULE + "/ToastActivity";
        String STATUS_BAR_ACTIVITY = MODULE + "/StatusBarActivity";
        String BITMAP_CACHE_ACTIVITY = MODULE + "/BitmapCacheActivity";
        String VIDEO_ACTIVITY = MODULE + "/VideoActivity";
        String PERMISSION_REQUEST_ACTIVITY = MODULE + "/PermissionRequestActivity";
        String SCREEN_ACTIVITY = MODULE + "/ScreenActivity";
        String INTERACTIVE_VIDEO_ACTIVITY = MODULE + "/InteractiveVideoActivity";
        String STORAGE_ACTIVITY = MODULE + "/StorageActivity";

    }


    public interface Library {
        String MODULE = "/moduleLibrary";
        String LIBRARY_FRAGMENT = MODULE + "/LibraryFragment";
        String JSOUP_ACTIVITY = MODULE + "/JsoupActivity";
        String CARTOON_ACTIVITY = MODULE + "/CartoonActivity";
        String CARTOON_WATCH_ACTIVITY = MODULE + "/CartoonWatchActivity";

        //
        String ZXing_ACTIVITY = MODULE + "/ZXingActivity";

        String CAMERAX_ACTIVITY = MODULE + "/CameraXActivity";

        String CAMERAX_PREVIEW_ACTIVITY = MODULE + "/camera/CameraPreviewActivity";
        String CAMERAX_PICTURE_ACTIVITY = MODULE + "/camera/CameraPictureActivity";
        String CAMERAX_VIDEO_ACTIVITY = MODULE + "/camera/CameraVideoActivity";
    }

    public interface View {
        String MODULE = "/moduleView";
        String VIEW_FRAGMENT = MODULE + "/ViewFragment";

        String CUSTOMISE_VIEW_ACTIVITY = MODULE + "/CustomiseViewActivity";
        String SYSTEM_VIEW_ACTIVITY = MODULE + "/SystemViewActivity";

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
        String PERSONAL_FRAGMENT = MODULE + "/PersonalFragment";


    }


}
