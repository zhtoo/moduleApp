package com.zht.common.permission;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * @Date 2022/3/19
 * @Author ZhangHaitao
 * @Description 继承自RxPermissions，将源码中request(Observable<?> trigger, String... permissions)由private改为public
 */
public class CustomRxPermissions extends RxPermissions {

    private WeakReference<FragmentActivity> mActivity;
    private WeakReference<Fragment> mFragment;

    private final LocalPermissionCacheInterface mLocalPermissionCache;

    public CustomRxPermissions(@NonNull FragmentActivity activity) {
        super(activity);
        mActivity = new WeakReference<>(activity);
        mLocalPermissionCache = new LocalPermissionCacheImpl();
    }

    public CustomRxPermissions(@NonNull Fragment fragment) {
        super(fragment);
        mFragment = new WeakReference<>(fragment);
        mLocalPermissionCache = new LocalPermissionCacheImpl();
    }

    private Context getContext() {
        if (mActivity != null && mActivity.get() != null) {
            return mActivity.get();
        }
        if (mFragment != null && mFragment.get() != null) {
            return mFragment.get().getContext();
        }
        return null;
    }

    @Override
    public Observable<Boolean> request(String... permissions) {
        if (getContext() == null) {
            return createResultObservable(false);
        }
        // 权限列表为空时，直接返回true
        if(permissions == null || permissions.length == 0){
            return createResultObservable(true);
        }
        // 当已经授予过这些权限时，将不再申请
        if (lacksPermissions(getContext(), permissions)) {
            return createResultObservable(true);
        }
        boolean hasRequestDenied = !mLocalPermissionCache.checkPermissions(getContext(),permissions);
        // 当用户明确拒绝过这些权限时，将不再申请
        if (hasRequestDenied) {
            return createResultObservable(false);
        } else {
            return super.request(permissions);
        }
    }

    @SuppressWarnings("WeakerAccess")
    @Override
    public <T> ObservableTransformer<T, Boolean> ensure(final String... permissions) {
        return o -> request(o, permissions)
                .buffer(permissions.length)
                .flatMap((Function<List<Permission>, ObservableSource<Boolean>>) permissionList -> {
                    if (permissionList.isEmpty()) {
                        return Observable.empty();
                    }
                    boolean granted = true;
                    for (Permission p : permissionList) {
                        mLocalPermissionCache.cachePermission(getContext(),p.name,p.granted);
                        if (!p.granted) {
                            granted = false;
                        }
                    }
                    return Observable.just(granted);
                });
    }

    private Observable<Boolean> createResultObservable(boolean result) {
        return Observable.create(emitter -> {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(result);
                        emitter.onComplete();
                    }
                }
        );
    }

    /**
     * 判断权限集合
     * permissions 权限数组
     * return false-表示没有授权权限  true-表示权限已开启
     */
    public boolean lacksPermissions(Context mContexts, String... mPermissions) {
        for (String permission : mPermissions) {
            if (ContextCompat.checkSelfPermission(mContexts, permission) ==
                    PackageManager.PERMISSION_DENIED) {
                //没有开启权限
                return false;
            }
        }
        //权限已开启
        return true;
    }


}
