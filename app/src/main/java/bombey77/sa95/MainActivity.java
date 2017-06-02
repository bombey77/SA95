package bombey77.sa95;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final static int TASK1 = 1;
    final static int TASK2 = 2;
    final static int TASK3 = 3;

    final static int START = 100;
    final static int STOP = 200;

    final static String PENDING_RESULT = "pendingResult";
    final static String PENDING_TIME = "pendingTime";
    final static String PARAM_IPENDING = "paramIPending";

    Intent intent;
    PendingIntent pi;

    TextView tvTask1, tvTask2, tvTask3;

    private static final String LOG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTask1 = (TextView) findViewById(R.id.tvTask1);
        tvTask2 = (TextView) findViewById(R.id.tvTask2);
        tvTask3 = (TextView) findViewById(R.id.tvTask3);

        tvTask1.setText("Task 1");
        tvTask2.setText("Task 2");
        tvTask3.setText("Task 3");
    }

    public void onClickStart(View view) {

        pi = createPendingResult(TASK1, new Intent(), 0);
        intent = new Intent(this, MyService.class).putExtra(PENDING_TIME, 7).putExtra(PARAM_IPENDING, pi);
        startService(intent);

        pi = createPendingResult(TASK2, new Intent(), 0);
        intent = new Intent(this, MyService.class).putExtra(PENDING_TIME, 4).putExtra(PARAM_IPENDING, pi);
        startService(intent);

        pi = createPendingResult(TASK3, new Intent(), 0);
        intent = new Intent(this, MyService.class).putExtra(PENDING_TIME, 6).putExtra(PARAM_IPENDING, pi);
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG, "requestCode = " + requestCode + ", resultCode = "
                + resultCode);
        if (resultCode == START) {
            switch (requestCode) {
                case TASK1:
                    tvTask1.setText("Task 1 started");
                    break;
                case TASK2:
                    tvTask2.setText("Task 2 started");
                    break;
                case TASK3:
                    tvTask3.setText("Task 3 started");
                    break;
                default:
                    break;
            }
        }

        if (resultCode == STOP) {
            int result = data.getIntExtra(PENDING_RESULT, 0);
            switch (requestCode) {
                case TASK1:
                    tvTask1.setText("Task 1 finished with result = " + result);
                    break;
                case TASK2:
                    tvTask2.setText("Task 2 finished with result = " + result);
                    break;
                case TASK3:
                    tvTask3.setText("Task 3 finished with result = " + result);
                    break;
                default:
                    break;
            }
        }
    }
}
