package cs.teilar.gr.earlywarningsystem.util;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;


/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 18/04/2015
 */
public class BusProvider {

    /**
     * {@link Bus} subclass that always posts events to the main thread.
     */
    private static class AndroidBus extends Bus {

        private final Handler handler = new Handler(Looper.getMainLooper());

        public AndroidBus() {
            super(ThreadEnforcer.ANY);
        }

        @Override
        public void post(final Object event) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                AndroidBus.super.post(event);
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        AndroidBus.super.post(event);
                    }
                });
            }
        }

    }

    private static final Bus BUS = new AndroidBus();

    public static Bus get() {
        return BUS;
    }

    private BusProvider() {
        // Disable instances
    }

}
