package com.mtel.location.bean;

/**
 * Created by Action on 16/7/29.
 */
public class GoogleResult<T> {

    private String status;

    private T results;

    public T getResults() {
        return results;
    }

    public void getResults(T results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
