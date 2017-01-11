package com.mtel.location.Rest;


import android.util.Log;

import rx.Observer;

/**
 * Created by action on 17/1/6.
 */
public class UIObserver<T> implements Observer<T> {
    CallBack<T> result;
    public UIObserver(CallBack<T> result) {
        this.result=result;
    }

    @Override
    public void onCompleted() {
        Log.e(" http onCompleted","onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Log.e("Http Observer error",e.getMessage());
        result.fail(e.getMessage());

    }

    @Override
    public void onNext(T t) {

        result.success(t);

    }
}
