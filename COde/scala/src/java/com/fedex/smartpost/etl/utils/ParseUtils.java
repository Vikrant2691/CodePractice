package com.fedex.smartpost.etl.utils;

import java.io.IOException;
import java.util.Arrays;

import org.apache.pig.data.DataByteArray;
import org.apache.pig.data.Tuple;

public final class ParseUtils {

	private ParseUtils() {
	}

	public static String getWindowedString(Tuple t) throws IOException {

		if (t == null) {
			return "";
		}

		if (t.size() == 0) {
			return "";
		}

		Object obj = t.get(0);
		if (obj == null) {
			return "";
		}
		DataByteArray dba = (DataByteArray) obj;
		Integer beginIndex = (Integer) t.get(1);
		Integer endIndex = (Integer) t.get(2);

		if (beginIndex >= endIndex) {
			return "";
		}

		byte[] originalBytes = dba.get();
		if (originalBytes == null) {
			return "";
		}
		if (originalBytes.length < endIndex) {

			return "";
		}

		byte[] slice = Arrays.copyOfRange(originalBytes, beginIndex, endIndex);
		return new String(slice, "UTF8");
	}
}