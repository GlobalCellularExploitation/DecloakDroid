package com.decloakdroid.logging;

import com.decloakdroid.ext.*;

import android.util.Log;

// note: flush only when done with the logging 
// for a function as it dumps stack traces for the call

public class Logger extends LoggerDB {
	
	private void clean() {
		_out = "";
	}

	protected void _log(String data) {
		_out += data;
	}
	
	// ####### public

	public void logInit(HookConfig config) {
		_config = config;
	}	
	
	public void logLine(String line) {
		_out += line + "\n";
	}

	public void logFlush(String notes) {
		_notes = notes;
		_out += notes;
		logFlush();
	}
	public void logFlush_I(String notes) {
		_notes = notes;
		_out += notes;
		logFlush_I();
	}
	public void logFlush_W(String notes) {
		_notes = notes;
		_out += "-> !!! " + notes;
		logFlush_W();
	}

	// use a static ref to synchronize across threads
	public void logFlush() {
		_addTraces();
		synchronized (_TAG) {
			Log.v(getTag(), _out);
			if (_enableFileLogs)
				LoggerFile.logToFile(
						"V", _out,
						_config.getType());
		}
		clean();
	}

	public void logFlush_I() {
		_addTraces();
		synchronized (_TAG) {
			Log.i(getTag(), _out);
			if (_enableFileLogs)
				LoggerFile.logToFile(
						"I", _out,
						_config.getType());
		}
		clean();
	}

	public void logFlush_W() {
		_addTraces();
		synchronized (_TAG) {
			Log.w(getTag(), _out);
			if (_enableFileLogs)
				LoggerFile.logToFile(
						"W", _out,
						_config.getType());
		}
		clean();
	}
	
	public void logParameter(String name, String value) {
	}
	
	public void logParameter(String name, Object value) {
	}
	
	public void logReturnValue(String name, String value) {
	}
	
	public void logReturnValue(String name, Object value) {
	}
	
	public void logBasicInfo() {
		_log("### "+ _config.getCategory()+" ### " + 
				ApplicationConfig.getPackageName() + 
				" - " + _config.getClassName() + "->" 
				+ _config.getMethodName() + "()\n");
	}
}
