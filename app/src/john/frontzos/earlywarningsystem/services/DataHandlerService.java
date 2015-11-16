package john.frontzos.earlywarningsystem.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import static john.frontzos.earlywarningsystem.io.Contracts.Intents.SYNC_LOG;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 13/05/2015
 */
public class DataHandlerService extends Service {



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()){
            case SYNC_LOG:
                //TODO start an background service for sync db with dmesg
                break;
        }


        return Service.START_STICKY;
    }
}
