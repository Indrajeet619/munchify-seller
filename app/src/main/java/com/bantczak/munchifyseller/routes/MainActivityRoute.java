package com.bantczak.munchifyseller.routes;

import com.bantczak.munchifyseller.MainActivity;

public class MainActivityRoute extends AbstractRoute {
    @Override
    protected Class<?> getClassName() {
        return MainActivity.class;
    }
}
