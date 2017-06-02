package bombey77.sa95;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ромашка on 02.06.2017.
 */

public class MyService extends Service {

    private static final String LOG = "myLogs";
    ExecutorService es;
    PendingIntent pi;
    int time;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG, "onCreate");
        es = Executors.newFixedThreadPool(2);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG, "onStartCommand, " + " startId = " + startId);
        pi = intent.getParcelableExtra(MainActivity.PARAM_IPENDING);
        time = intent.getIntExtra(MainActivity.PENDING_TIME, 1);
        MyRun myRun = new MyRun(pi, time, startId);
        es.execute(myRun);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class MyRun implements Runnable {

        int time;
        PendingIntent pi;
        int startId;

        MyRun(PendingIntent pi, int time, int startId) {
            this.time = time;
            this.pi = pi;
            this.startId = startId;

        }

        @Override
        public void run() {
            try {
                pi.send(MainActivity.START);
                TimeUnit.SECONDS.sleep(time);
                Intent intent = new Intent().putExtra(MainActivity.PENDING_RESULT, time*100);
                pi.send(MyService.this, MainActivity.STOP, intent);
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop();
        }

        public void stop() {
            Log.d(LOG, "MyRun#" + startId + " ended, stopSelfResult " + startId + " " + stopSelfResult(startId));
        }
    }

}
