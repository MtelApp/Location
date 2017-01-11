package com.mtel.location.Rest;

/**
 * Created by action on 17/1/6.
 */

public interface CallBack<T> {

    void success(T t);
    void fail(String message);
}
