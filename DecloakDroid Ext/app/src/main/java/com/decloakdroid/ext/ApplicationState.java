package com.decloakdroid.ext;

import java.lang.reflect.Method;

import com.decloakdroid.logging.LoggerConfig;
import com.saurik.substrate.MS;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class ApplicationState {
//	static private String _TAG = LoggerConfig.getTag();
//	static private String _TAG_LOG = LoggerConfig.getTagLog();
	static private String _TAG_ERROR = LoggerConfig.getTagError();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static void initApplicationState(Class<?> resources) {
		
		String methodName = "getPackageName";
		Class<?>[] params = new Class<?>[]{};						
		Method pMethod = null;
		try { 
			pMethod = resources.getMethod(methodName, params);
		} catch (Exception e) {
            Log.w(_TAG_ERROR, "Error - No such method: " + methodName);
            return;
		}

		final MS.MethodPointer old = new MS.MethodPointer();
		MS.hookMethod(resources, pMethod, new MS.MethodHook() {
					public Object invoked(Object resources, 
						Object... args) throws Throwable {
						
						String packageName = (String)old.invoke(resources, args);
						if (!packageName.equals("android") && 
								ApplicationConfig.getPackageName() == null) {
							
							ApplicationConfig.setPackageName(packageName);
							
							try {
								Class<?> cls = Class.forName("android.app.ContextImpl");
								Class<?> noparams[] = {};
								Method _method = 
										cls.getDeclaredMethod("getApplicationContext", noparams);
								
								Context context = (Context) _method.invoke(resources);
								ApplicationConfig.setContext(context);
								
								//PackageManager pm = context.getPackageManager();
								_method = cls.getDeclaredMethod("getPackageManager", noparams);
								PackageManager pm = (PackageManager) _method.invoke(resources);
	
								
								android.content.pm.ApplicationInfo ai = 
										pm.getApplicationInfo(packageName, 0);

								PackageInfo p = pm.getPackageInfo(packageName, 0);
								ApplicationConfig.setDataDir(p.applicationInfo.dataDir);
								ApplicationConfig.enable();
							}
							catch (Exception e) {
								Log.w(_TAG_ERROR, "Error when setting the " +
										"application state for ["+ packageName +"]: ", e);
							}
						}
						return packageName;
					}
		}, old);
	}
}
