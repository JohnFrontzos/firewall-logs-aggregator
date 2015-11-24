package cs.teilar.gr.earlywarningsystem.util;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.util.UUID;


/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 01/08/2015
 */
public class DeviceUtils {


    /**
     * Returns a {@link UUID} for current device. It combines telephony device ID, SIM serial number (if any)
     * and ANDROID_ID from {@link android.provider.Settings.Secure}.
     *
     * @param context
     * @return {@link UUID}
     */
    public static String getUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();

    }

    /**
     * Returns a simplified permission-free {@link UUID} for current device. It uses only
     * ANDROID_ID from {@link android.provider.Settings.Secure}.
     *
     * @param context
     * @return {@link UUID}
     */
    public static String getSimpleUUID(Context context) {
        String androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        return String.valueOf(androidId.hashCode());

    }
}
