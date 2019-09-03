package com.tecacet.jflat.impl;

import com.tecacet.jflat.RowRecord;

/**
 * Parses a line with fixed width fields
 * 
 * @author Dimitri Papaioannou
 * 
 */
public class FixedWidthLineMapper implements LineMapper {

	private final int[] widths;
	private final boolean trimWhitespace;
	
	/**
	 * 
	 * @param widths
	 *            the width of each token in the line
	 */
	public FixedWidthLineMapper(int[] widths) {
		this(widths, true);
	}

	public FixedWidthLineMapper(int[] widths, boolean trimWhitespace) {
		super();
		this.widths = widths;
		this.trimWhitespace = trimWhitespace;
	}

	@Override
	public RowRecord apply(Long lineNumber, String line) {
		int lastIndex = 0;
		String[] tokens = new String[widths.length];
		for (int i = 0; i < widths.length; i++) {
			String thisToken = line.substring(lastIndex, lastIndex + widths[i]);
			if (trimWhitespace) {
				thisToken = thisToken.trim();
			}
			if (thisToken.length() == 0) {
				thisToken = null;
			}
			tokens[i] = thisToken;
			lastIndex += widths[i];
		}
		return new ArrayRowRecord(lineNumber, tokens);
	}

}
