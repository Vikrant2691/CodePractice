package com.fedex.smartpost.etl.types.datetime;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fedex.smartpost.etl.utils.ParseUtils;

/**
 * The input {@code Tuple} is defined as followed:
 * 
 * <ol>
 *   <li>{@code DataByteArray} representing the {@code String} to be processed.
 *   <li>Beginning offset of the byte array to parse.
 *   <li>End offset of the byte array to parse
 *   <li>Format of the date string to parse
 *  </ol>
 * @author 793344
 *
 */
public class ParseNullableDateTime extends EvalFunc<DateTime> {
	@Override
	public DateTime exec(Tuple input) throws IOException {

		String utf8 = ParseUtils.getWindowedString(input);
		String trimmed = utf8.trim();
		if (trimmed.length() == 0) {
			return null;
		}

		String dateFormat = (String) input.get(3);  // for example: yyyy-mm-dd HH:mm:ss.SSS
		DateTimeFormatter dtf = DateTimeFormat.forPattern(dateFormat);
		DateTime dt = dtf.parseDateTime(trimmed);
		return dt;
	}

}