/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	public static final long ONE_DAY = 86400000L;
	public static final long ONE_WEEK = 604800000L;
	public static final long HALF_HOUR = 1800000L;
	public static final long ONE_HOUR = 3600000L;
	public static final long ONE_MINUTE = 60000L;
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public static boolean isSameDay(long time1, long time2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(time1);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(time2);
		return c1.get(5) == c2.get(5) && c1.get(2) == c2.get(2) && c1.get(1) == c2.get(1);
	}

	public static boolean isSameMonth(long time1, long time2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(time1);
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(time2);
		return c1.get(2) == c2.get(2) && c1.get(1) == c2.get(1);
	}

	public static long now() {
		return System.currentTimeMillis();
	}

	public static String toMillisString() {
		return "" + System.currentTimeMillis();
	}

	public static long toMillis(String strMillis) {
		return Long.valueOf(strMillis).longValue();
	}

	public static long toMillis(Date time) {
		return time == null ? 0L : time.getTime();
	}

	public static long toMillis() {
		return System.currentTimeMillis();
	}

	public static long nextDay(long millis) {
		return millis + 86400000L;
	}

	public static long nextWeek(long millis) {
		return millis + 604800000L;
	}

	public static long nextHour(long millis) {
		return millis + 3600000L;
	}

	public static long nextHalfHour(long millis) {
		return millis + 1800000L;
	}

	public static long lastWeek(long millis) {
		return millis - 604800000L;
	}

	public static long lastDay(long millis) {
		return millis - 86400000L;
	}

	public static long lastHour(long millis) {
		return millis - 3600000L;
	}

	public static long lastMinute(long millis) {
		return millis - 60000L;
	}

	public static long CLOCK_0() {
		Calendar c = Calendar.getInstance();
		c.set(c.get(1), c.get(2), c.get(5), 0, 0, 0);
		c.set(14, 0);
		return c.getTimeInMillis();
	}

	public static long scheduledAt(int HOUR) {
		return scheduledAt(HOUR, 0);
	}

	public static long scheduledAt(int HOUR, int minute) {
		long scheduledAt = CLOCK_0() + 3600000L * (long) HOUR + 60000L * (long) minute;
		if (scheduledAt <= now()) {
			scheduledAt += 86400000L;
		}

		return scheduledAt;
	}

	public static long fixedAt(int HOUR, int minute) {
		return CLOCK_0() + 3600000L * (long) HOUR + 60000L * (long) minute;
	}

	public static long getTime(String timeStr, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date d = sdf.parse(timeStr);
			return d.getTime();
		} catch (Exception arg3) {
			return 0L;
		}
	}

	public static long format(String ss) {
		String[] tt = ss.split(":");
		int[] unit = new int[] { 60, 60, 60, 24 };
		long total = 0L;
		int j = 0;
		int x = 1;

		for (int i = tt.length - 1; i >= 0; ++j) {
			String t = tt[i];
			int time = Integer.parseInt(t);
			time *= x;
			total += (long) time;
			x *= unit[j];
			--i;
		}

		return total;
	}

	public static boolean isSameDay(Date d1, long l2) {
		Date d2 = new Date();
		d2.setTime(l2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str1 = sdf.format(d1);
		String str2 = sdf.format(d2);
		return str1.equals(str2);
	}

	public static boolean isToday(Date date) {
		return isSameDay(date.getTime(), now());
	}

	public static boolean isToday(long time) {
		return isSameDay(time, now());
	}

	public static boolean isThisMonth(long time) {
		return isSameMonth(time, now());
	}

	public static boolean isThisMonth(long time, int sheduledClock) {
		return isSameMonth(time, now() - (long) sheduledClock * 3600000L);
	}

	public static String format(Date d) {
		if (d == null) {
			return "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yy��MM��dd��");
			return sdf.format(d);
		}
	}

	public static String format(Date d, String formatter) {
		if (d == null) {
			return "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(formatter);
			return sdf.format(d);
		}
	}

	public static int scheduledAtHour(int nowClock, int delay, int clockBeforeDelay) {
		return nowClock < clockBeforeDelay ? clockBeforeDelay
				: ((nowClock - clockBeforeDelay) % delay == 0 ? nowClock + delay
						: nowClock - nowClock % delay + clockBeforeDelay % delay);
	}

	public static long addTime(int seconds) {
		long now = System.currentTimeMillis();
		Calendar ca = Calendar.getInstance();
		ca.setTimeInMillis(now);
		ca.add(13, seconds);
		return ca.getTimeInMillis();
	}

	public static int getHour() {
		Calendar c = Calendar.getInstance();
		return c.get(11);
	}

	public static boolean isAfaterTheSameDayOfHour(long time, int hour) {
		boolean isSameDay = isSameDay(time, now());
		if (!isSameDay) {
			int daysBetween = (int) ((System.currentTimeMillis() - time) / 86400000L);
			if (daysBetween >= 1) {
				return true;
			}

			int cuhour = getHour();
			if (cuhour >= hour) {
				return true;
			}
		}

		return false;
	}

	public static Date getDate(long v) {
		try {
			return new Date(Long.valueOf(v).longValue());
		} catch (Exception arg2) {
			arg2.printStackTrace();
			return null;
		}
	}

	public static Date getDate(String timeStr) {
		try {
			return sdf.parse(timeStr);
		} catch (Exception arg1) {
			return null;
		}
	}
}