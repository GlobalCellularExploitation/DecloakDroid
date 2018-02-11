package com.decloakdroid.logging;

import com.decloakdroid.ext.HookConfig;
import com.decloakdroid.ext.IntroStringHelper;

public class LoggerConfig extends IntroStringHelper {
	protected LoggerConfig() {
	}
	
	protected HookConfig _config;
	
	public static String _TAG = "decloakdroid";
	public static String _TAG_ERROR = "decloakdroidError";
	public static String _TAG_LOG = "decloakdroidLog";
	
	public static String getTag() {
		return _TAG;
	}
	
	public static String getTagError() {
		return _TAG_ERROR;
	}
	
	public static String getTagLog() {
		return _TAG_LOG;
	}

	protected String _out = "";
	protected String _notes = "";
	protected String _traces = "";
	
	protected boolean _enableDB = false;
	protected boolean _enableFileLogs = false;

	// this can be enabled via the _config file
	protected boolean _stackTraces = false;
	
	// change this value to get full traces
	protected boolean _fullTraces = false;
	
}
