package com.arcao.geocaching.api.data;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.arcao.geocaching.api.data.type.LogType;

public class FieldNote {
	protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	static {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
	}
	
	private final String cacheCode;
	private final LogType logType;
	private final Date dateLogged;
	private final String note;
	
	public FieldNote(String cacheCode, Date dateLogged, LogType logType, String note) {
		this.cacheCode = cacheCode;
		this.dateLogged = dateLogged;
		this.logType = logType;
		this.note = note;
	}

	public String getCacheCode() {
		return cacheCode;
	}

	public Date getDateLogged() {
		return dateLogged;
	}

	public LogType getLogType() {
		return logType;
	}

	public String getNote() {
		return note;
	}
	
	@Override
	public String toString() {
		return String.format("%s,%s,%s,\"%s\"", cacheCode, DATE_FORMAT.format(dateLogged), logType.getFriendlyName(), safeNote(note));
	}
	
	public static FieldNote parseLine(String line) {
		String[] items = line.split(",", 4);
		
		String note = items[3];
		if (note.length() >= 2 && note.startsWith("\"") && note.endsWith("\""))
			note = note.substring(1, note.length() - 1);
		
		try {
			return new FieldNote(items[0], DATE_FORMAT.parse(items[1]), LogType.parseLogType(items[2]), note);
		} catch (ParseException e) {
			return null;
		}
	}
	
	protected static String safeNote(String note) {
		return note.replace('"', '\'').replaceAll("[\r\n\t]+", "");
	}
}
