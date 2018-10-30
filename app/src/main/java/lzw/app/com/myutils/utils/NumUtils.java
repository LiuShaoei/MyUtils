package lzw.app.com.myutils.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import lzw.app.com.myutils.BuildConfig;

/**
 * Created by LiuZhaowei on 2018/9/28.
 */

public class NumUtils {

    /**
     * 提供精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商, 保留指定位数小数
     */
    public static double formatRound(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("保留的小数位数必须大于零");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal bigDecimal = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    /**
     * 保留两位小数
     */
    public static String formatTwo(double v1) {
        BigDecimal b1 = new BigDecimal(v1);
        return b1.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 格式化手机号码,这里发生异常,直接返回原数字.不需要做判空等操作.
     */
    public static String formatPhone(String phone) {

        try {
            return phone.substring(0, 3) + "****" + phone.substring(7, 11);
        } catch (Exception e) {
            return phone;
        }
    }

    /**
     * 格式化日期到秒
     */
    public static String formatTimeToSecond(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化日期到分钟
     */
    public static String formatTimeToMinute(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化日期到日期
     */
    public static String formatTimeToDate(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }


    /***
     *拨打电话-（跳转到拨号界面）
     */
    public static void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /***
     *格式化数据 逗号
     */
    public static String formatComma(String num) {
        String data = formatZero(num);
        if (data.contains(".")) {
            String front = data.substring(0, data.indexOf("."));
            String point = data.substring(data.indexOf("."), data.length());
            BigDecimal b = new BigDecimal(front);
            DecimalFormat df = new DecimalFormat("#,###");
            return df.format(b) + point;
        } else {
            BigDecimal b = new BigDecimal(data);
            DecimalFormat df = new DecimalFormat("#,###");
            return df.format(b);
        }
    }


    /**
     * 格式化掉小数点后面的0
     *
     * @param data
     * @return
     */
    public static String formatZero(String data) {
        BigDecimal bigDecimal = new BigDecimal(data);
        DecimalFormat df = new DecimalFormat("#.###");
        return df.format(bigDecimal);
    }

    public static String formatDou00(String data) {
        BigDecimal b = new BigDecimal(data);
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(b);
    }

    /**
     * RoundingMode.FLOOR  远离0方向的舍入
     *
     * @param data
     * @return
     */
    public static String formatDou000(String data) {
        BigDecimal b = new BigDecimal(data);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(b);
    }

    /**
     * RoundingMode.FLOOR  远离0方向的舍入
     *
     * @param data
     * @return
     */
    public static String formatDou0000(String data) {
        BigDecimal b = new BigDecimal(data);
        DecimalFormat df = new DecimalFormat("#,#00.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(b);
    }

    /**
     * 四舍五入的方式
     *
     * @param data
     * @return
     */
    public static String formatDou00002(String data) {
        BigDecimal b = new BigDecimal(data);
        DecimalFormat df = new DecimalFormat("#,#00.00");
        return df.format(b);
    }

    /***
     *格式化数据 不要0,并且去尾
     */
    public static String formatNoPoint(String data) {
        BigDecimal b = new BigDecimal(data);
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(b);
    }

    /***
     *格式化数据 保留一位小数点
     */
    public static String format1Point(String data) {
        BigDecimal b = new BigDecimal(data);
        DecimalFormat df = new DecimalFormat("#0.0");
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(b);
    }

    /***
     *格式化数据 保留两位小数点
     */
    public static String format2Point(String data) {
        BigDecimal b = new BigDecimal(data);
        DecimalFormat df = new DecimalFormat("#0.00");
        df.setRoundingMode(RoundingMode.FLOOR);
        return df.format(b);
    }

    /**
     * 自定义时间格式转换
     *
     * @param dateFormat
     * @param mTime      NumUtils.transferStringToDate("yyyy-MM-dd","5115112251555");
     */
    public static String transferStringToDate(String dateFormat, String mTime) {
        Long time = Long.parseLong(mTime);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(time);
        return sdf.format(date);
    }
    /**
     * 自定义时间格式转换
     *
     * @param dateFormat
     * @param mTime      NumUtils.transferStringToDate("yyyy-MM-dd",5115112251555);
     */
    public static String transferStringToDate(String dateFormat, long mTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(mTime);
        return sdf.format(date);
    }

    /**
     * 获取版本号name
     *
     * @return
     */
    public static String getAppName() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * 获取版本code
     *
     * @return
     */
    public static int getAppCode() {
        return BuildConfig.VERSION_CODE;
    }
}
