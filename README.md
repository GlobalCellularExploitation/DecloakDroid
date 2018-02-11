DecloakDroid
========

Blackbox tool to help understand what traditional and system Android applications do at runtime
and assist in the identification of potential security issues.


Description
-----------

DecloakDroid comprises two separate components: a GUI interface to configure 
hooks, filters and options and a Cydia Substrate extension containing the core of 
the tool functionalities, including hooks and analysis of potential issues.

DecloakDroid can be installed on a rooted device and dynamically
configured to hook security-sensitive Android APIs at run-time. The tool records
all the relevant API calls made by an application, including function calls, arguments 
and return values. It then perform tests for security issues in real time and persists 
the results in a database and in the Android logging system.

Usage
---------------


* Ensure that Cydia Substrate has been deployed on your test device. The installer requires a rooted device and can be found on the Google Play store at https://play.google.com/store/apps/details?id=com.saurik.substrate&hl=en 
* Install DecloakDroid_Ext.apk on a device where Cydia Substrate is installed with:

        adb install DecloakDroid_Ext.apk

* Install DecloakDroid Config.apk:

        adb install DecloakDroid_UI.apk

The DecloakDroid_UI application displays apps the Core application will hook and the
various filters and options applied to them. This application need root access (you can use supersu to give temporary root access to the application). The changes are dynamic and you do not need to restard the applications for them to be effective.

* Once configured with the Config application, logs are dumped in the system logs and in a database in the directory of the application hooked (in databases/DecloakDroid.db)

It should be noted that the Core application can work on a device running Android 2.3 and above whereas the Config application can only run from Android 3.0 on due to the use of specific APIs. In order to test applications on older SDKs without the GUI by only using the Core application, you can simply create a file named "DecloakDroid.config" containing filters you want to hook in the directory of the applications you want to test. Example:

        adb shell su -c echo "GENERAL CRYPTO, KEY, HASH, FS, IPC, PREF, URI, WEBVIEW" > /data/data/com.YOUR_APP_NAME/DecloakDroid.config

### How to uninstall

        adb uninstall com.decloakdroid.core
        adb uninstall com.decloakdroid.config

### What if the extension crashes
This tool has not been tested on all versions of Android. If the tool does not work on your version, please send us your error logs:

        adb logcat -s “DecloakDroidError”

If due to the error the phone does not boot anymore, you can still connect to it via adb and simply remove the extension to fix it with:

        adb shell su -c rm /data/app/com.DecloakDroid.core*

If you still have issues, it may be due to Cydia Substrate itself, which may not be compatible with your device? To uninstall it you can do the following (from Cydia Substrate's website): "By holding down the volume-up button on your device you can disable Substrate while it is attempting to load modifications (such as while it is turning on and starting); this will give you an opportunity to use Google Play to uninstall things that might be broken."

Reporting
-----------------

#### Reporting

* Relevant data including potential issues related to the APIs hooked is dumped in
a database and in the system logs. You can do the following commands to display them:

* Display the complete logs:

        adb logcat -s "DecloakDroid"
        
* Display potential issues:

        adb logcat -s "DecloakDroid:W"
        
* Use the Android version of the analyzer (TBD, it will be pushed to a different 
github repository soon) to generate an HTML formatted report.

### Display relevant call stacks

Checking the "STACK TRACES" option within the Config tool will dump a relevant call 
stack (comprising of 3 calls) for the selected filters.

### What is being analysed/logged, exactly?
###### General Crypto:
* Log encrypted/decrypted data before/after calls and the algo used
(Note: "readable data is displayed if at least 75% of characters are readable, unreadable characters are stored as ".". If data is not readable, it is stored as base 64)
* Spot static IVs and broken algorithms
* Spot weak RNG

###### Hash:
* Log data that is being hashed and the resulting hash
* Display algo used and warns if weak (MD5)

###### Key:
* Log any keys used to encrypt
* Log PBKDF key creation (key, passcode, iterations)
* Log passcode used with a keystore

###### FS:
* Log only some file system accesses as they are very noisy
* Spot read/write on SD card and the creation of file (or set property) as world readable/writable

###### IPC:
* Log IPC creations with details
* Log some Intent sent with details (and extra)
* Programmatic permissions and creation

###### Pref:
* Log read/write of preferences with its data, the type and the default value (value set if nothing is returned)
* Dump all preferences when getAll is called, this is done only once to avoid noise
* Log world read/writeable prefs
* Warn for access to preferences that don't exist (could be a hidden preferences to enable logs for instance)

###### SSL:
* Warn if SSL is used but any hostname is validated for a valid cert
* Warn if the app validates any cert (self-signed etc.)
* Log if cert pinning is potentially implemented
* Log if SSL not used

###### Webview:
* Log when JS, plugins or FS access are enabled for a webview
* Warn/log when a JS interface is used (JS bridge)

###### SQLite:
* Log data passed to execSQL, update*, insert*, replace

Doing It Yourself
-----------------

### Building From Source

Most users should just download and install the pre-compiled packages.
However, if you want to modify the tool's functionality you will have to
clone the source repository and build the packages yourself.

Then you need to add the Cydia Substrate SDK to eclipse. See here for instructions on how to do so: http://www.cydiasubstrate.com/id/73e45fe5-4525-4de7-ac14-6016652cc1b8/.

### Adding hooks
Adding hooks is simple and can be done within the com.decloakdroid.custom_hooks module. See the pre-filled example in the code (CustomHookList.java and HookExampleImpl.java) and make sure to enable the "CUSTOM HOOKS" option in the DecloakDroid Config application. See http://www.latent-io.com for more instructions.

Notes: Some methods simply cannot be hooked due to potential issues in Cydia Substrate and the hook may just crash the process. Also, make sure to not try hooking abstract methods as it just throws an exception that is never caught by Cydia Substrate (and will just crash the process). You need to hook their implementation, which is sometimes not documented but can be easily found in the Android code base (for example: android.content.Context is implemented in android.content.ContextImpl).

License
-------

See ./LICENSE.

Author
-------
Marc Blanchou


Contributor
-------
Mathew Solnik
