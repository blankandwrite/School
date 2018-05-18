package aqtc.gl.school.main.find.utils;

public class DeviceUtil {

	public static int getSdkInt() {
		return android.os.Build.VERSION.SDK_INT;
	}

	public static boolean isICS() {
		return getSdkInt() >= 19;
	}
}
