package com.fedex.smartpost.etl.types.java;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import com.fedex.smartpost.etl.utils.ParseUtils;


public class ParseNullableBigDecimal extends EvalFunc<BigDecimal> {
	@Override
	public BigDecimal exec(Tuple input) throws IOException {

		String utf8 = ParseUtils.getWindowedString(input);
		String trimmed = utf8.trim();
		if (trimmed.length() == 0) {
			return null;
		}
		return new BigDecimal(trimmed);
	}
}