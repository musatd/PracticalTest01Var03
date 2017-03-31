package practicaltest01.eim.systems.cs.pub.ro.practicaltest01var03;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by root on 3/31/17.
 */

public class PracticalTest01Var03Service extends Service {

    private ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String student = intent.getStringExtra("student");
        String group = intent.getStringExtra("group");
        processingThread = new ProcessingThread(this, student, group);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}
