package scheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is returns the current date and time.
 * 
 * @author S3
 * @version 0.1, first alpha version
 * @update date 2023/10/28
 **/
public class Now_DateTime_Controller {
	private LocalDateTime ldt;

	Now_DateTime_Controller() {
		ldt = LocalDateTime.now();
	}

	/*
	 * This function return the current year as int.
	 */
	public int getNowYearInt() {
		return ldt.getYear();
	}

	/*
	 * This function return the current month as int.
	 */
	public int getNowMonthInt() {
		return ldt.getMonthValue();
	}

	/*
	 * This function return the current date as int.
	 */
	public int getNowDateInt() {
		return ldt.getDayOfMonth();
	}

	/*
	 * This function return the current hour as 12-type or 24-type int.
	 * 
	 * @ param int type: 12 or 24
	 */
	public int getNowHourInt(int type) {
		if (type == 12) {
			int changer = ldt.getHour();
			changer = changer % 12;

			if (changer == 0) {
				return 12;
			} else {
				return changer;
			}
		} else if (type == 24) {
			return ldt.getHour();
		} else {
			return ldt.getHour();
		}
	}

	/*
	 * This function return the current minute as int.
	 */
	public int getNowMinuteInt() {
		return ldt.getMinute();
	}

	/*
	 * This function return the current second as int.
	 */
	public int getNowSecondInt() {
		return ldt.getSecond();
	}

	/*
	 * This function return the current year as String.
	 */
	public String getNowYearString() {
		return Integer.toString(getNowYearInt());
	}

	/*
	 * This function return the current month as String type.
	 */
	public String getNowMonthString() {
		return String.format("%02d", (getNowMonthInt()));
	}

	/*
	 * This function return the current date as String type.
	 */
	public String getNowDateString() {
		return String.format("%02d", (getNowDateInt()));
	}

	/*
	 * This function return the current day of week.
	 * 
	 * @return df.format(ldt): 일(Sun) or 월(Mon) or 화(Tue) or 수(Wed) or 목(Thu) or
	 * 금(Fri) or 토(Sat)
	 */
	public String getNowDayOfWeek() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("E");
		return df.format(ldt);
	}

	/*
	 * This function return the 오전(am) or 오후(pm).
	 */
	public String getNowAmPm() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("a");
		return df.format(ldt);
	}

	/*
	 * This function return the current hour as String.
	 * 
	 * @ param int type: 12 or 24
	 * 
	 * @ return <String> hour
	 */
	public String getNowHourString(int type) {
		if (type == 12) {
			return String.format("%02d", (getNowHourInt(12)));
		} else if (type == 24) {
			return String.format("%02d", (getNowHourInt(24)));
		} else {
			return String.format("%02d", (getNowHourInt(24)));
		}
	}

	/*
	 * This function return the current minute as String.
	 */
	public String getNowMinuteString() {
		return String.format("%02d", (getNowMinuteInt()));
	}

	/*
	 * This function return the current second as String.
	 */
	public String getNowSecondString() {
		return String.format("%02d", (getNowSecondInt()));
	}

	/*
	 * This function return the current datetime as String
	 * 
	 * @ return <String >nowYear + nowMinute + nowDate + nowHour + nowMinute +
	 * nowSecond
	 */
	public String getNowDateTimeString() {
		return getNowYearString() + getNowMonthString() + getNowDateString() + getNowHourString(24)
				+ getNowMinuteString() + getNowSecondString();
	}

	/*
	 * This function return the current datetime as long
	 */
	public long getNowDateTimeLong() {
		return Long.parseLong(getNowDateTimeString());
	}
}