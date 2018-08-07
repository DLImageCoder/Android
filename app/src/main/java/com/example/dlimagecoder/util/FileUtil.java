package com.example.dlimagecoder.util;

import android.graphics.Bitmap;

import com.example.dlimagecoder.DLImageCoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtil {

    public static final String FILE_DIR = DLImageCoder.getInstance()
            .getExternalFilesDir(null).getAbsolutePath()+"/images";

    public static String storeImage(Bitmap bitmap){
        File fileDir = new File(FILE_DIR);
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        String name = ""+System.currentTimeMillis()+".jpg";
        String path = FILE_DIR+"/"+name;
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    new FileOutputStream(new File(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return path;
    }
}
