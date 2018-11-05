package com.fedex.smartpost.etl.types.java;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import com.fedex.smartpost.etl.utils.ParseUtils;


public class ParseNullableInt extends EvalFunc<Integer> {
	@Override
	public Integer exec(Tuple input) throws IOException {

		String utf8 = ParseUtils.getWindowedString(input);
		String trimmed = utf8.trim();
		if (trimmed.length() == 0) {
			return null;
		}
		return Integer.valueOf(trimmed);
	}
}