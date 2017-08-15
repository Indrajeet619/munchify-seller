package com.bantczak.munchifyseller.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Manages the internal cache found in getCacheDir().getPath().
 * Remember to run these methods within a background thread!
 * */

public class CacheManager {
    public CacheManager() {

    }

    public static void cacheData(Context context, byte[] data, String fileName) throws IOException {

        File cacheDir = context.getCacheDir();
        //long size = getDirSize(cacheDir);
        //long newSize = data.length + size;

       /* if (newSize > MAX_SIZE) {
            cleanDir(cacheDir, newSize - MAX_SIZE);
        }*/

        File file = new File(cacheDir, fileName);
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(data);
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }

    public static byte[] retrieveData(Context context, String fileName) throws IOException {

        File cacheDir = context.getCacheDir();
        File file = new File(cacheDir, fileName);

        if (!file.exists()) {
            // Data doesn't exist
            return null;
        }

        byte[] data = new byte[(int) file.length()];
        FileInputStream is = new FileInputStream(file);
        try {
            is.read(data);
        }
        finally {
            is.close();
        }

        return data;
    }
}
