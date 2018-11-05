package com.fedex.smartpost.etl.types.datetime;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * The purpose of this method is to return the time zone ID associated with a
 * joda {@code DateTime} object.
 * 
 * @param t
 *            - {@code Tuple} containing a Joda {@code DateTime} object
 * @return - ID of the associated {@code DateTime} object.
 * 
 * @author 793344
 *
 */
public class GetTimeZoneId extends EvalFunc<String> {
	@Override
	public String exec(Tuple t) throws IOException {
		if (t == null) {
			return "";
		}

		if (t.size() == 0) {
			return "";
		}

		Object objDate = t.get(0);
		if (objDate == null) {
			return "";
		}
		String sDate = objDate.toString();
		
		Object objFormat = t.get(1);
		if (objFormat == null) {
			return "";
		}
		String sFormat = (String) objFormat;
		
		DateTimeFormatter formatter = DateTimeFormat
				.forPattern(sFormat);

		DateTime dt = formatter.withOffsetParsed().parseDateTime(sDate);
		return dt.getZone().toTimeZone().getID();

	}
}