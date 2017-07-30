package com.bantczak.munchifyseller.routes;

import android.content.Context;

/**
 * The interface that contains the respective route functions used to launch an activity
 * */
public interface RouteInterface {
    void go(final Context fromActivity);
    RouteInterface clearTop();
    RouteInterface newTask();
}
