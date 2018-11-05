package com.fedex.smartpost.etl.types.datetime;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.joda.time.DateTimeZone;

/**
 * Converts a timezone offset given in minutes to a valid Java {@code TimeZone} ID.
 * 
 * @param input - {@code Tuple} containing an integer offset in minutes
 * 
 * @return {@code TimeZone} ID offset
 * 
 * @author 5019053
 *
 */
public class ConvertToTimeZoneId extends EvalFunc<String> {

	@Override
	public String exec(Tuple input) throws IOException {
		if (input == null || input.size() == 0) {
			return "";
		}
		Object timezone = input.get(0);
		if (timezone == null) {
			return "";
		}
		
		int hourOffset = Integer.parseInt(timezone.toString()) / 60 * 100;
		
		String timezoneWithPaddingAndSign = String.format("%+05d", hourOffset);
		
		DateTimeZone dateTimeZone = DateTimeZone.forID(timezoneWithPaddingAndSign);
		
		return dateTimeZone.toTimeZone().getID();
	}

}
