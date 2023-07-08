package com.jlkj.common.core.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

/**
 * 时间工具类
 *
 * @author jlkj
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期路径 即年-月-日 如2018-08-08
     */
    public static final String dateTime1() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy-MM-dd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获得当前月--结束日期
     *
     * @param date
     * @return
     */
    public static String getMaxMonthDate(String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(dayFormat.parse(date));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            return dayFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得当前月--结束日期
     *
     * @param date 欲计算结束日期的时间
     * @return 当前月结束日期
     */
    public static Date getMaxMonthDate1(String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM");
        try {
            calendar.setTime(dayFormat.parse(date));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            return calendar.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取服务器启动时间
     *
     * @return Date 服务器启动时间
     * @author 111191
     * @Date 上午 09:00:12 2023年6月23日, 0023
     */

    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     *
     * @param endDate 结束时间
     * @param nowDate 当前日期
     * @return 返回字符串两个时间差（X天X小时X分钟）
     * @author 111191
     * @Date 上午 09:00:45 2023年6月23日, 0023
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 增加 LocalDateTime ==> Date
     */
    public static Date toDate(LocalDateTime temporalAccessor) {
        ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * 增加 LocalDate ==> Date
     */
    public static Date toDate(LocalDate temporalAccessor) {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    /**
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author sunran   判断当前时间在时间区间内
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return false;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 月份计算
     *
     * @param oDate 日期
     * @param num   月数
     * @return
     */
    public static String addMonth(String oDate, Integer num) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newDate = df.parse(oDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(newDate);
            cal.add(Calendar.MONTH, num);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算两个月中间所有月份
     *
     * @param minDate 日期
     * @param maxDate 月数
     * @return
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        //这里要注意 有的需求可能是用2020-01来表示20年第一周 格式就应该为yyyy-MM
        //如果是用2020-1来表示20年第一周 格式就应该为yyyy-M
        // 格式化为年月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();

            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }

            // 实现排序方法
            Collections.sort(result, new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    String str1 = (String) o1;
                    String str2 = (String) o2;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                    Date date1 = null;
                    Date date2 = null;
                    try {
                        date1 = format.parse(str1);
                        date2 = format.parse(str2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (date2.compareTo(date1) > 0) {
                        return -1;
                    }
                    return 1;
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 比较两个字符串（日期格式）的大小
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return boolean 开始日期大于结束日期 True; 否则为False
     * @author 111191
     * @Date 上午 08:50:29 2023年6月23日, 0023
     */
    public boolean compareDate(String beginDate, String endDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.parse(beginDate).before(df.parse(endDate));
    }

    /**
     * 比较两个字符串（日期格式）的大小, compareTo比较
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return boolean 开始日期大于结束日期 True; 否则为False
     * @author 111191
     * @Date 上午 08:55:55 2023年6月23日, 0023
     */
    public boolean compareDate2(String beginDate, String endDate) throws ParseException {
        return beginDate.compareTo(endDate) > 0 ? true : false;
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return 某年最后一天日期
     * @author 266861
     * @Date 2023/7/6 9:44
     **/
    public static Date getLastOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return 某年第一天日期
     * @author 266861
     * @Date 2023/7/6 9:44
     **/
    public static Date getFirstOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }
}
