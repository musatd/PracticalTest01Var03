package practicaltest01.eim.systems.cs.pub.ro.practicaltest01var03;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;

/**
 * Created by root on 3/31/17.
 */

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;
    private Random random = new Random();

    private String student;
    private String group;

    public ProcessingThread(Context context, String student, String group) {
        this.context = context;

        this.student = student;
        this.group = group;
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
        intent.putExtra("student", student);
        intent.putExtra("group", group);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }

}
