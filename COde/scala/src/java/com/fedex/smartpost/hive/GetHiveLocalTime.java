package com.fedex.smartpost.hive;

import java.sql.Timestamp;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

public class GetHiveLocalTime extends UDF {

/**
	 * 
	 * @param ts
	 *            - UTC based {@code Timestamp
	 * @param timeZone
	 *            - format is -07:00 representing the difference from UTC
	 * @return String representing the local time
	 */
	public Text evaluate(Timestamp ts, Text timeZone) {
		if (ts == null) {
			return null;
		}

		DateTime dt = new DateTime(ts.getTime());
		DateTimeZone dtz = getTimeZone(timeZone);
		if (dtz == null) {
			return null;
		}
		DateTime localTime = dt.withZone(dtz);

		LocalDateTime ldt = localTime.toLocalDateTime();

		return new Text(ldt.toString());
	}

	private static DateTimeZone getTimeZone(Text t) {
		if (t == null) {
			return null;
		}

		//It will return an empty string if there is no content
		String trimmed = t.toString().trim();
		
		if (trimmed.length() == 0) {
			return null;
		}
		return DateTimeZone.forID(trimmed);
	}
}