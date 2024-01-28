package cn.z.tool;

import java.util.Calendar;

/**
 * <h1>数字时间戳</h1>
 *
 * <p>
 * 数字时间戳格式：<code>yyyyMMddHHmmssSSS</code>即<code>xxxx年xx月xx日xx时xx分xx秒xxx毫秒</code><br>
 * 例如时间：<code>2020年11月13日15时40分26秒123毫秒</code>，数字时间戳：<code>20201113154026123</code>
 * </p>
 *
 * <h2>Calendar常量代码</h2>
 * <table>
 * <tr>
 * <th>字段
 * <th>解释
 * <tr>
 * <td>ERA
 * <td>世纪
 * <tr>
 * <td>YEAR
 * <td>年
 * <tr>
 * <td>MONTH
 * <td>月(0一月 1二月 ... 11十二月)
 * <tr>
 * <td>DATE / DAY_OF_MONTH
 * <td>日
 * <tr>
 * <td>AM_PM
 * <td>上午/下午
 * <tr>
 * <td>HOUR
 * <td>12小时制
 * <tr>
 * <td>HOUR_OF_DAY
 * <td>24小时制
 * <tr>
 * <td>MINUTE
 * <td>分钟
 * <tr>
 * <td>SECOND
 * <td>秒
 * <tr>
 * <td>MILLISECOND
 * <td>毫秒
 * <tr>
 * <td>DAY_OF_WEEK
 * <td>周几(1周日 2周一 ... 7周六)
 * <tr>
 * <td>WEEK_OF_YEAR
 * <td>当年第几周
 * <tr>
 * <td>DAY_OF_YEAR
 * <td>当年第几天
 * <tr>
 * <td>DAY_OF_WEEK_IN_MONTH
 * <td>当月当周第几天
 * <tr>
 * <td>WEEK_OF_MONTH
 * <td>当月第几周
 * <tr>
 * <td>ZONE_OFFSET
 * <td>时区
 * <tr>
 * <td>DST_OFFSET
 * <td>夏令时
 * </table>
 *
 * <p>
 * createDate 2020/11/13 15:40:26
 * </p>
 *
 * @author ALI[ali-k@foxmail.com]
 * @since 1.0.0
 */
public class DigitTimestamp {

    /* ==================== 常量 ==================== */
    // region 常量

    /**
     * 时间戳1天的间隔={@value}毫秒
     */
    public static final long INTERVAL_DAY = 86399999;
    /**
     * 数字时间戳1天的间隔={@value}单位
     */
    public static final long DIGIT_INTERVAL_DAY = 235959999;

    // endregion

    private DigitTimestamp() {
    }

    /* ==================== 数字时间戳-时间戳 ==================== */
    // region 数字时间戳-时间戳

    /**
     * 获取当前时间戳<br>
     * 请使用{@link System#currentTimeMillis()}
     *
     * @return 时间戳
     */
    @Deprecated
    public static long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取指定数字时间戳0时0分0秒0毫秒的时间戳
     *
     * @param digitTimestamp 指定数字时间戳
     * @return 时间戳
     */
    public static long getTimestampStart(long digitTimestamp) {
        return getTimestamp(digitTimestamp, true, -1, 0);
    }

    /**
     * 获取指定数字时间戳+偏移日0时0分0秒0毫秒的时间戳
     *
     * @param digitTimestamp 指定数字时间戳
     * @param dayOffset      相对于指定数字时间戳的偏移日
     * @return 时间戳
     */
    public static long getTimestampStart(long digitTimestamp, int dayOffset) {
        return getTimestamp(digitTimestamp, true, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取指定数字时间戳23时59分59秒999毫秒的时间戳<br>
     * 如果已经调用过{@link #getTimestampStart(long)}，请保存变量并+{@link #INTERVAL_DAY}，这样速度更快
     *
     * @param digitTimestamp 指定数字时间戳
     * @return 时间戳
     */
    public static long getTimestampEnd(long digitTimestamp) {
        return getTimestamp(digitTimestamp, false, -1, 0);
    }

    /**
     * 获取指定数字时间戳+偏移日23时59分59秒999毫秒的时间戳<br>
     * 如果已经调用过{@link #getTimestampStart(long, int)}，请保存变量并+{@link #INTERVAL_DAY}，这样速度更快
     *
     * @param digitTimestamp 指定数字时间戳
     * @param dayOffset      相对于指定数字时间戳的偏移日
     * @return 时间戳
     */
    public static long getTimestampEnd(long digitTimestamp, int dayOffset) {
        return getTimestamp(digitTimestamp, false, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取指定数字时间戳的(指定偏移字段、偏移大小)的(开始/结束/当前)时间戳
     *
     * @param digitTimestamp 指定数字时间戳
     * @param isStart        开始(true)/结束(false)/当前(null)
     * @param offsetField    偏移字段(offsetAmount为0时，此参数任意)
     * @param offsetAmount   偏移大小(0为不偏移)
     * @return 时间戳
     */
    public static long getTimestamp(long digitTimestamp, Boolean isStart, int offsetField, int offsetAmount) {
        // 当前时间戳
        Calendar calendar = Calendar.getInstance();
        // 设置年月日时分秒
        calendar.set( //
                (int) (digitTimestamp / 10000000000000L), // 年
                (int) (((digitTimestamp / 100000000000L) % 100) - 1), // 月
                (int) ((digitTimestamp / 1000000000L) % 100), // 日
                (int) ((digitTimestamp / 10000000) % 100), // 时
                (int) ((digitTimestamp / 100000) % 100), // 分
                (int) ((digitTimestamp / 1000) % 100) // 秒
        );
        // 设置毫秒
        calendar.set(Calendar.MILLISECOND, (int) (digitTimestamp % 1000));
        if (isStart != null) {
            // 设置时分秒毫秒
            if (isStart) {
                // 开始时间
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            } else {
                // 结束时间
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
            }
        }
        // 指定偏移
        if (offsetField > -1 && offsetAmount > 0) {
            calendar.add(offsetField, offsetAmount);
        }
        // 返回时间戳
        return calendar.getTimeInMillis();
    }

    // endregion

    /* ==================== 时间戳-数字时间戳 ==================== */
    // region 时间戳-数字时间戳

    /**
     * 获取当前数字时间戳
     *
     * @return 数字时间戳
     */
    public static long getDigitTimestamp() {
        return getDigitTimestamp(-1, null, -1, 0);
    }

    /**
     * 获取指定时间戳的数字时间戳
     *
     * @param timestamp 指定时间戳
     * @return 数字时间戳
     */
    public static long getDigitTimestamp(long timestamp) {
        return getDigitTimestamp(timestamp, null, -1, 0);
    }

    /**
     * 获取当前时间戳0时0分0秒0毫秒的数字时间戳
     *
     * @return 数字时间戳
     */
    public static long getDigitTimestampStart() {
        return getDigitTimestamp(-1, true, -1, 0);
    }

    /**
     * 获取指定时间戳0时0分0秒0毫秒的数字时间戳
     *
     * @param timestamp 指定时间戳
     * @return 数字时间戳
     */
    public static long getDigitTimestampStart(long timestamp) {
        return getDigitTimestamp(timestamp, true, -1, 0);
    }

    /**
     * 获取当前时间戳+偏移日0时0分0秒0毫秒的数字时间戳
     *
     * @param dayOffset 相对于当前时间戳的偏移日
     * @return 数字时间戳
     */
    public static long getDigitTimestampStart(int dayOffset) {
        return getDigitTimestamp(-1, true, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取指定时间戳+偏移日0时0分0秒0毫秒的数字时间戳
     *
     * @param timestamp 指定时间戳
     * @param dayOffset 相对于指定时间戳的偏移日
     * @return 数字时间戳
     */
    public static long getDigitTimestampStart(long timestamp, int dayOffset) {
        return getDigitTimestamp(timestamp, true, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取当前时间戳23时59分59秒999毫秒的数字时间戳<br>
     * 如果已经调用过{@link #getDigitTimestampStart()}，请保存变量并+{@link #DIGIT_INTERVAL_DAY}，这样速度更快
     *
     * @return 数字时间戳
     */
    public static long getDigitTimestampEnd() {
        return getDigitTimestamp(-1, false, -1, 0);
    }

    /**
     * 获取指定时间戳23时59分59秒999毫秒的数字时间戳<br>
     * 如果已经调用过{@link #getDigitTimestampStart(long)}，请保存变量并+{@link #DIGIT_INTERVAL_DAY}，这样速度更快
     *
     * @param timestamp 指定时间戳
     * @return 数字时间戳
     */
    public static long getDigitTimestampEnd(long timestamp) {
        return getDigitTimestamp(timestamp, false, -1, 0);
    }

    /**
     * 获取当前时间戳+偏移日23时59分59秒999毫秒的数字时间戳<br>
     * 如果已经调用过{@link #getDigitTimestampStart(int)}，请保存变量并+{@link #DIGIT_INTERVAL_DAY}，这样速度更快
     *
     * @param dayOffset 相对于当前时间戳的偏移日
     * @return 数字时间戳
     */
    public static long getDigitTimestampEnd(int dayOffset) {
        return getDigitTimestamp(-1, false, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取指定时间戳+偏移日23时59分59秒999毫秒的数字时间戳<br>
     * 如果已经调用过{@link #getDigitTimestampStart(long, int)}，请保存变量并+{@link #DIGIT_INTERVAL_DAY}，这样速度更快
     *
     * @param timestamp 指定时间戳
     * @param dayOffset 相对于指定时间戳的偏移日
     * @return 数字时间戳
     */
    public static long getDigitTimestampEnd(long timestamp, int dayOffset) {
        return getDigitTimestamp(timestamp, false, Calendar.DAY_OF_YEAR, dayOffset);
    }

    /**
     * 获取(当前/指定)时间戳的(指定偏移字段、偏移大小)的(开始/结束/当前)数字时间戳
     *
     * @param timestamp    指定时间戳(-1为当前时间)
     * @param isStart      开始(true)/结束(false)/当前(null)
     * @param offsetField  偏移字段(offsetAmount为0时，此参数任意)
     * @param offsetAmount 偏移大小(0为不偏移)
     * @return 数字时间戳
     */
    public static long getDigitTimestamp(long timestamp, Boolean isStart, int offsetField, int offsetAmount) {
        Calendar calendar;
        if (timestamp < 0) {
            // 当前时间戳
            calendar = Calendar.getInstance();
        } else {
            // 指定时间戳
            calendar = new Calendar.Builder().setInstant(timestamp).build();
        }
        if (isStart != null) {
            // 设置时分秒毫秒
            if (isStart) {
                // 开始时间
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
            } else {
                // 结束时间
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
            }
        }
        // 指定偏移
        if (offsetField > -1 && offsetAmount > 0) {
            calendar.add(offsetField, offsetAmount);
        }
        // 返回数字时间戳
        return ( //
                calendar.get(Calendar.YEAR) * 10000000000000L) // 年
                + ((calendar.get(Calendar.MONTH) + 1) * 100000000000L) // 月
                + (calendar.get(Calendar.DAY_OF_MONTH) * 1000000000L) // 日
                + (calendar.get(Calendar.HOUR_OF_DAY) * 10000000) // 时
                + (calendar.get(Calendar.MINUTE) * 100000) // 分
                + (calendar.get(Calendar.SECOND) * 1000) // 秒
                + (calendar.get(Calendar.MILLISECOND) // 毫秒
        );
    }

    // endregion

    /* ==================== 工具 ==================== */
    // region 工具

    /**
     * 填充缺损的数字时间戳<br>
     * 需要保证长度为4-17位
     *
     * @param timestamp 缺损数字时间戳
     * @return 成功返回17位数字时间戳，失败返回传入参数
     */
    public static long complement(long timestamp) {
        int length = 0;
        // 计算长度
        for (long i = timestamp; i > 0; i /= 10) {
            length++;
        }
        switch (length) {
            case 4 -> {
                // 年(补充为xxxx年01月01日00时00分00秒000毫秒)
                return timestamp * 10000000000000L + 101000000000L;
            }
            case 5 -> {
                // 年月(少1位)(末尾为0补充为xxxx年x1月01日00时00分00秒000毫秒，否则补充为xxxx年x0月01日00时00分00秒000毫秒)
                return timestamp % 10 == 0 //
                        ? timestamp * 1000000000000L + 101000000000L // 1-9月变成1月
                        : timestamp * 1000000000000L + 1000000000L; // 10-12月变成10月
            }
            case 6 -> {
                // 年月(补充为xxxx年xx月01日00时00分00秒000毫秒)
                return timestamp * 100000000000L + 1000000000L;
            }
            case 7 -> {
                // 年月日(少1位)(末尾为0补充为xxxx年xx月x1日00时00分00秒000毫秒，否则补充为xxxx年xx月x0日00时00分00秒000毫秒)
                return timestamp % 10 == 0 //
                        ? timestamp * 10000000000L + 1000000000L // 1-9日变成1日
                        : timestamp * 10000000000L; // 10-31日变成10日
            }
            case 8 -> {
                // 年月日(补充为xxxx年xx月xx日00时00分00秒000毫秒)
                return timestamp * 1000000000;
            }
            case 9 -> {
                // 年月日时(少1位)(补充为xxxx年xx月xx日x0时00分00秒000毫秒)
                return timestamp * 100000000;
            }
            case 10 -> {
                // 年月日时(补充为xxxx年xx月xx日xx时00分00秒000毫秒)
                return timestamp * 10000000;
            }
            case 11 -> {
                // 年月日时分(少1位)(补充为xxxx年xx月xx日xx时x0分00秒000毫秒)
                return timestamp * 1000000;
            }
            case 12 -> {
                // 年月日时分(补充为xxxx年xx月xx日xx时xx分00秒000毫秒)
                return timestamp * 100000;
            }
            case 13 -> {
                // 年月日时分秒(少1位)(补充为xxxx年xx月xx日xx时xx分x0秒000毫秒)
                return timestamp * 10000;
            }
            case 14 -> {
                // 年月日时分秒(补充为xxxx年xx月xx日xx时xx分xx秒000毫秒)
                return timestamp * 1000;
            }
            case 15 -> {
                // 年月日时分秒毫秒(少2位)(补充为xxxx年xx月xx日xx时xx分xx秒x00毫秒)
                return timestamp * 100;
            }
            case 16 -> {
                // 年月日时分秒毫秒(少1位)(补充为xxxx年xx月xx日xx时xx分xx秒xx0毫秒)
                return timestamp * 10;
            }
            default -> {
                // 年月日时分秒毫秒(为xxxx年xx月xx日xx时xx分xx秒xxx毫秒)
                return timestamp;
            }
        }
    }

    /**
     * 填充缺损的数字时间戳<br>
     * 需要保证长度为4-17位
     *
     * @param timestamp 缺损数字时间戳
     * @return 成功返回17位数字时间戳，失败返回传入参数
     */
    public static String complement(String timestamp) {
        switch (timestamp.length()) {
            case 4 -> {
                // 年(补充为xxxx年01月01日00时00分00秒000毫秒)
                return timestamp + "0101000000000";
            }
            case 5 -> {
                // 年月(少1位)(末尾为0补充为xxxx年x1月01日00时00分00秒000毫秒，否则补充为xxxx年x0月01日00时00分00秒000毫秒)
                return timestamp.charAt(timestamp.length() - 1) == '0' //
                        ? timestamp + "101000000000" // 1-9月变成1月
                        : timestamp + "001000000000"; // 10-12月变成10月
            }
            case 6 -> {
                // 年月(补充为xxxx年xx月01日00时00分00秒000毫秒)
                return timestamp + "01000000000";
            }
            case 7 -> {
                // 年月日(少1位)(末尾为0补充为xxxx年xx月x1日00时00分00秒000毫秒，否则补充为xxxx年xx月x0日00时00分00秒000毫秒)
                return timestamp.charAt(timestamp.length() - 1) == '0' //
                        ? timestamp + "1000000000" // 1-9日变成1日
                        : timestamp + "0000000000"; // 10-31日变成10日
            }
            case 8 -> {
                // 年月日(补充为xxxx年xx月xx日00时00分00秒000毫秒)
                return timestamp + "000000000";
            }
            case 9 -> {
                // 年月日时(少1位)(补充为xxxx年xx月xx日x0时00分00秒000毫秒)
                return timestamp + "00000000";
            }
            case 10 -> {
                // 年月日时(补充为xxxx年xx月xx日xx时00分00秒000毫秒)
                return timestamp + "0000000";
            }
            case 11 -> {
                // 年月日时分(少1位)(补充为xxxx年xx月xx日xx时x0分00秒000毫秒)
                return timestamp + "000000";
            }
            case 12 -> {
                // 年月日时分(补充为xxxx年xx月xx日xx时xx分00秒000毫秒)
                return timestamp + "00000";
            }
            case 13 -> {
                // 年月日时分秒(少1位)(补充为xxxx年xx月xx日xx时xx分x0秒000毫秒)
                return timestamp + "0000";
            }
            case 14 -> {
                // 年月日时分秒(补充为xxxx年xx月xx日xx时xx分xx秒000毫秒)
                return timestamp + "000";
            }
            case 15 -> {
                // 年月日时分秒毫秒(少2位)(补充为xxxx年xx月xx日xx时xx分xx秒x00毫秒)
                return timestamp + "00";
            }
            case 16 -> {
                // 年月日时分秒毫秒(少1位)(补充为xxxx年xx月xx日xx时xx分xx秒xx0毫秒)
                return timestamp + "0";
            }
            default -> {
                // 年月日时分秒毫秒(为xxxx年xx月xx日xx时xx分xx秒xxx毫秒)
                return timestamp;
            }
        }
    }

    // endregion

}
