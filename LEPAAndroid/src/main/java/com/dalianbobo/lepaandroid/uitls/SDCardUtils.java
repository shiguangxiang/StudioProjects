package com.dalianbobo.lepaandroid.uitls;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/10/7.
 */
public class SDCardUtils {
    // sd card path
    public static final String SDPATH = Environment
            .getExternalStorageDirectory().toString();
    public static final String APP_SDPATH = SDPATH + "/wine";
    public static final String IMAGES = APP_SDPATH + "/images/";
    /**
     * 判断是否装有SD卡、是否可读写、是否有空间
     * @param size 需存入的文件大小，SD剩余空间必须大于该值
     * @return  true可用，false不可用
     */
    public static boolean checkSDStatus(long size) {
        try {
			/* 读取SD卡大小 */
            File storage = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(storage.getPath());
            long blocks = stat.getAvailableBlocks();
            long blocksize = stat.getBlockSize();

			/* 判断 */
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)
                    && (blocks * blocksize) > size) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  复制文件至某个文件夹
     * @param srcFileName
     * @param destDirName
     * @return
     */
    public static boolean copyFileToFile(String srcFileName, String destDirName) {
        try {
            int byteread = 0;
            File oldfile = new File(srcFileName);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldfile);
                FileOutputStream fs = new FileOutputStream(destDirName);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
