package com.n22.util;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static final String FULL_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	public static String getString(Date date) {
		return getString(date, DEFAULT_DATE_PATTERN);
	}

	public static String getString(Date date, String pattern) {
		String str = "";
		try {
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			str = sf.format(date);
		} catch (Exception e) {
		}
		return str;
	}

	public static Date getDate(String dateStr) {
		return getDate(dateStr, DEFAULT_DATE_PATTERN);
	}

	public static Date getDate(String dateStr, String pattern) {
		Date date = null;
		try {
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			date = sf.parse(dateStr);
		} catch (Exception e) {
			// LogUtil.w(e);
			// LogUtil.d("date trans error..");
		}
		return date;
	}

	public static String getCurrentTime(String pattern) {
		return getString(new Date(), pattern);
	}

	public static String getCurrentTime() {
		return getCurrentTime(DEFAULT_DATE_PATTERN);
	}

	public static Date getAfterDate(Date date, int dayCount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime() + dayCount * 1000 * 60 * 60 * 24 * dayCount);
		return calendar.getTime();
	}

	/**
	 * 获取两日期相差的天数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getDiff(Date startTime, Date endTime) {
		long diff = 0;
		diff = startTime.getTime() - endTime.getTime();
		diff = diff / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(diff));
	}

	/**
	 * 判断日期相差几个月
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static double getMounthDiff(String startTime, String endTime) {
		String[] beginDates = startTime.split("-");
		String[] endDates = endTime.split("-");

		double len = (Integer.valueOf(endDates[0]) - Integer.valueOf(beginDates[0])) * 12
				+ (Integer.valueOf(endDates[1]) - Integer.valueOf(beginDates[1]));
		int day = Integer.valueOf(endDates[2]) - Integer.valueOf(beginDates[2]);
		if (day > 0) {
			len += 0.5;
		} else if (day < 0) {
			len -= 0.5;
		}
		return len;
	}

	/**
	 * 获取两日期相差的秒数
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long getDateDiff(Date startTime, Date endTime) {
		long diff = 0;
		diff = startTime.getTime() - endTime.getTime();
		return diff;
	}

	/**
	 * 获取年龄---精确到年
	 * 
	 * @param date
	 * @return
	 */
	public static int getAge(Date date) {
		int age = 0;
		if (date != null) {
			int oldYear = date.getYear() + 1900;
			int nowYear = Calendar.getInstance().get(Calendar.YEAR);
			if (oldYear < nowYear && (nowYear - oldYear) < 120) {
				age = nowYear - oldYear;
			}
		}
		return age;
	}

	/**
	 * 获取年龄---精确到日
	 * 
	 * @return
	 */
	public static int getAgeByBirthday(Date birthday) {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthday)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		return age;
	}

	/**
	 * 比较两个日期时间的大小
	 * 
	 * @param datetime1
	 * @param datetime2
	 * @return -1:datetime1>datetime2 1:datetime1<datetime2
	 */
	@SuppressLint("SimpleDateFormat")
	public static int compareDateTime(String datetime1, String datetime2) {
		int tag = Integer.MAX_VALUE;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date date1 = dateFormat.parse(datetime1);
			Date date2 = dateFormat.parse(datetime2);
			if (date1.getTime() > date2.getTime()) {
				tag = -1;
			} else if (date1.getTime() < date2.getTime()) {
				tag = 1;
			} else {
				tag = 0;
			}
		} catch (Exception exception) {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date date1 = dateFormat1.parse(datetime1);
				Date date2 = dateFormat1.parse(datetime2);
				if (date1.getTime() > date2.getTime()) {
					tag = -1;
				} else if (date1.getTime() < date2.getTime()) {
					tag = 1;
				} else {
					tag = 0;
				}
			} catch (Exception e) {
			}
		}
		return tag;
	}
	
	public static boolean isDiffBirthday(int diff, Date birthday) {
		int year = Calendar.getInstance().get(Calendar.YEAR);// 默认设置当前年
		// 生日
		Calendar birthdayCalendar = Calendar.getInstance();
		birthdayCalendar.setTime(birthday);
		birthdayCalendar.set(Calendar.YEAR, year);
		
		System.out.println("今年生日：" + birthdayCalendar.getTime().toLocaleString());

		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.set(Calendar.HOUR_OF_DAY, 0);
		nowCalendar.set(Calendar.MINUTE, 0);
		nowCalendar.set(Calendar.SECOND, 0);
		
		System.out.println("当前日期：" + nowCalendar.getTime().toLocaleString());

		nowCalendar.set(Calendar.DAY_OF_YEAR, nowCalendar.get(Calendar.DAY_OF_YEAR) + diff);
		
		System.out.println("当前日期+偏移量：" + nowCalendar.getTime().toLocaleString());

		return birthdayCalendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR)
				&& birthdayCalendar.get(Calendar.MONTH) == nowCalendar.get(Calendar.MONTH)
				&& birthdayCalendar.get(Calendar.DAY_OF_MONTH) == nowCalendar.get(Calendar.DAY_OF_MONTH);
	}
}
