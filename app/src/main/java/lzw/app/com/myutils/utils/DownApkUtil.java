package lzw.app.com.myutils.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载apk
 */
public class DownApkUtil {

    private static ProgressDialog uploadDialog;
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void downLoadApk(final String apkPath, final Activity activity) {
        if (TextUtils.isEmpty(apkPath)) return;
        uploadDialog = new ProgressDialog(activity);
        uploadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        uploadDialog.setMessage("正在下载更新...");
        uploadDialog.setCancelable(false);
        uploadDialog.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(apkPath);
                    installApk(file, activity);
                    dismissUploadDialog();
                } catch (Exception e) {
                    ToastUtil.showShort("下载失败");
                    dismissUploadDialog();
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private static void dismissUploadDialog() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (uploadDialog != null) {
                    uploadDialog.dismiss();
                }
            }
        });
    }

    private static File getFileFromServer(String path) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = null;
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10000);
                final int lengthOfFile = conn.getContentLength();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (uploadDialog != null) {
                            uploadDialog.setMax(100);
                        }
                    }
                });
                InputStream is = conn.getInputStream();
                file = new File(Environment.getExternalStorageDirectory(), "lhtx.apk");
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream fos = new FileOutputStream(file);
                BufferedInputStream bis = new BufferedInputStream(is);
                byte[] buffer = new byte[1024];
                int len;
                int total = 0;
                while ((len = bis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    total += len;
                    final int finalTotal = total;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (uploadDialog != null) {
                                uploadDialog.setProgress(finalTotal * 100 / lengthOfFile);
                            }
                        }
                    });
                }
                fos.close();
                bis.close();
                is.close();
            } catch (IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showShort("下载失败");
                    }
                });
                e.printStackTrace();
            }
            return file;
        } else {
            ToastUtil.showShort("未挂载SD卡，无法下载");
            return null;
        }
    }

    // 安装apk
    private static void installApk(File file, Activity activity) {
        if (file == null) return;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }
}
