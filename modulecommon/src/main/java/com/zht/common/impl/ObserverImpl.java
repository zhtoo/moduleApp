package com.zht.common.impl;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @Date 2022/12/16 15:42
 * @Author zhanghaitao
 * @Description
 */
public abstract class ObserverImpl<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }
}
