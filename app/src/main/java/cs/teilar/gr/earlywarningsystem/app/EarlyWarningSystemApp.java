package cs.teilar.gr.earlywarningsystem.app;

import android.app.Application;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.HawkBuilder;
import com.orhanobut.hawk.LogLevel;

import net.danlew.android.joda.JodaTimeAndroid;

import cs.teilar.gr.earlywarningsystem.BuildConfig;
import cs.teilar.gr.earlywarningsystem.util.DeviceUtils;
import timber.log.Timber;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 20/11/2015
 */
public class EarlyWarningSystemApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        // Initialize Hawk
        initHawk();

        // Setup Joda Time
        JodaTimeAndroid.init(this);
    }


    void initHawk() {
        if (BuildConfig.DEBUG) {
            Hawk.init(this)
                    .setLogLevel(LogLevel.FULL)
                    .setStorage(HawkBuilder.newSharedPrefStorage(this))
                    .setEncryptionMethod(HawkBuilder.EncryptionMethod.NO_ENCRYPTION)
                    .build();

        } else {
            Hawk.init(this)
                    .setLogLevel(LogLevel.NONE)
                    .setStorage(HawkBuilder.newSharedPrefStorage(this))
                    .setEncryptionMethod(HawkBuilder.EncryptionMethod.MEDIUM)
                    .setPassword(DeviceUtils.getSimpleUUID(this))
                    .build();
        }

    }
}
