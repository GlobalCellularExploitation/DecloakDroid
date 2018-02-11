package com.decloakdroid.logging;

import android.util.Log;

public class LoggerDB extends LoggerTraces {
	
	public void disableDBlogger() {
		_enableDB  = false;
	}
	
	public void enableDBlogger() {
		_enableDB  = true;
	}

	public void disableFilelogger() {
		_enableFileLogs  = false;
	}

	public void enableFilelogger() {
		_enableFileLogs  = true;
	}
}
