package com.fedex.smartpost.etl.types.java;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import com.fedex.smartpost.etl.utils.ParseUtils;


public class ParseNullableLong extends EvalFunc<Long> {
	@Override
	public Long exec(Tuple input) throws IOException {
		String utf8 = ParseUtils.getWindowedString(input);
		
		return parseString(utf8);
	}
	
	public static Long parseString(String string) {
		String trimmed = string.trim();
		if (trimmed.length() == 0) {
			return null;
		}
		return Long.valueOf(trimmed);
	}
}