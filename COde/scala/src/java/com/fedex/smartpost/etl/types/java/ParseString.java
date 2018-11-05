package com.fedex.smartpost.etl.types.java;

import java.io.IOException;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;

import com.fedex.smartpost.etl.utils.ParseUtils;

/**
 * The input {@code Tuple} is defined as followed:
 * 
 * <ol>
 *   <li>{@code DataByteArray} representing the {@code String} to be processed.
 *   <li>Beginning offset of the byte array to parse.
 *   <li>End offset of the byte array to parse
 *   <li>Format of the date string to pars
 *  </ol>
 * @author 793344
 *
 */
public class ParseString extends EvalFunc<String> {
	@Override
	public String exec(Tuple input) throws IOException {
		String utf8 = ParseUtils.getWindowedString(input);
		String trimmed = utf8.trim();
		if (trimmed.length() == 0) {
			return null;
		}
		return trimmed;
	}
}