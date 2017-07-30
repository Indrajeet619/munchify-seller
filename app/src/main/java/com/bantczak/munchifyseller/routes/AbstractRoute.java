package com.bantczak.munchifyseller.routes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public abstract class AbstractRoute implements RouteInterface {
    protected Intent mIntent;
    private int mRequestCode; // Required if startActivityForResult() is called

    public AbstractRoute() {
        mIntent = new Intent();
    }

    @Override
    public void go(Context fromActivity) {
        if (getClassName() != null) {
            mIntent.setClass(fromActivity, getClassName());
        }
        setAction();

        if (mRequestCode != 0 && fromActivity instanceof Activity) {
            ((Activity) fromActivity).startActivityForResult(mIntent, mRequestCode);
        } else {
            /* Start activity from context must use new task flag */
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            fromActivity.startActivity(mIntent);
        }
    }

    protected abstract Class<?> getClassName();

    protected void setAction() {
        mIntent.setAction(Intent.ACTION_VIEW);
    }

    public AbstractRoute addFlags(int flag) {
        mIntent.addFlags(flag);
        return this;
    }

    @Override
    public RouteInterface clearTop() {
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return this;
    }

    @Override
    public RouteInterface newTask() {
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return this;
    }
}
