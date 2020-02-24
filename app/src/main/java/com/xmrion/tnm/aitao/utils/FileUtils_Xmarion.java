package com.xmrion.tnm.aitao.utils;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 郑艳达 on 2017/7/13.
 * 文件操作的静态方法
 */

public class FileUtils_Xmarion {

    /**
     *  
     * 将Bitmap转换成文件
     *  * 保存文件  
     *  * @param bm  
     *  * @param fileName  
     *  * @throws IOException  
     *  
     */
    public static File saveFile(Bitmap bm, String path, String fileName) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path, fileName);
        BufferedOutputStream bos =
                new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }
}
