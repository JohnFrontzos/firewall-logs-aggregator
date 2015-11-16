package john.frontzos.earlywarningsystem.utils;

import com.squareup.otto.Bus;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 18/04/2015
 */
public class BusProvider {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }


}
