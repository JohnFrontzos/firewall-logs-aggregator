package cs.teilar.gr.earlywarningsystem.util;

import android.os.SystemClock;

import org.joda.time.DateTime;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 26/05/2015
 */
public class DateUtils {

    /**
     * Returns Joda DateTime of the kernel's timestamp given
     *
     * @param kernelTimeStamp String timestamp
     * @return @see org.joda.DateTime of the timestamp
     */
    public static DateTime getDate(String kernelTimeStamp) {
        long timeSinceBoot = SystemClock.elapsedRealtime();
        DateTime bootDate = DateTime.now().minus(timeSinceBoot);
        int timestamp = Math.round(Float.parseFloat(kernelTimeStamp.trim()));
        return bootDate.plusSeconds(timestamp);
    }


}
