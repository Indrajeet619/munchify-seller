package com.bantczak.munchifyseller.routes;

import com.bantczak.munchifyseller.CameraActivity;

public class CameraViewRoute extends AbstractRoute {
    @Override
    protected Class<?> getClassName() {
        return CameraActivity.class;
    }
}
