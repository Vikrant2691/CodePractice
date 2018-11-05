package com.fedex.smartpost.etl.types.datetime;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


public class GetLocalTime extends EvalFunc<DateTime> {
	@Override
	public DateTime exec(Tuple t) throws IOException {
		if (t == null) {
			return null;
		}
		
		if (t.size() == 0) {
			return null;
		}

		Object dtObj = t.get(0);
		if (dtObj == null) {
			return null;
		}
		DateTime dtUtc = (DateTime) dtObj;
		
		// If the DateTime is not in UTC, then just pass it back.
		if(!dtUtc.getZone().equals(DateTimeZone.UTC))
		{
			return dtUtc;
		}
		
		Object tzObj = t.get(1);
		if(tzObj == null)
		{
			return null;
		}
		String sTz = (String) tzObj;  // -07:00
		
		DateTimeZone dtz = DateTimeZone.forID(sTz);
		return dtUtc.toDateTime(dtz);
	}
}