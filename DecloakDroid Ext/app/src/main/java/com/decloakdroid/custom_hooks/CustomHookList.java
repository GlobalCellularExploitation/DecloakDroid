package com.decloakdroid.custom_hooks;

import com.decloakdroid.ext.HookConfig;

public class CustomHookList {
	
	static public HookConfig[] getHookList() {
		return _hookList;
	}
	
	static private HookConfig[] _hookList = new HookConfig[] {

		new HookConfig(false,
				"android.telephony.TelephonyManager", "getNetworkType", new Class<?>[]{},
				new HookNetworkType(), "Change network type"),

		new HookConfig(false,
				"android.telephony.TelephonyManager", "getPhoneType", new Class<?>[]{},
				new HookPhoneType(), "Change phone type"),

	};
}

