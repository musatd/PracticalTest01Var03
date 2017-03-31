package practicaltest01.eim.systems.cs.pub.ro.practicaltest01var03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var03MainActivity extends AppCompatActivity {

    private Button display_button = null;
    private Button change_activity = null;
    private CheckBox student_checkbox;
    private CheckBox group_checkbox;
    private EditText student_field;
    private EditText group_field;
    private EditText display_field;


    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.intent_button) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03SecondaryActivity.class);
                intent.putExtra("student", student_field.getText().toString());
                intent.putExtra("group", group_field.getText().toString());
                startActivityForResult(intent, 200);
                return;
            }

            boolean studentChecked = student_checkbox.isChecked();
            boolean groupChecked = group_checkbox.isChecked();
            String errorMessage = "";
            String message = "";

            if (studentChecked) {
                String student = student_field.getText().toString();

                if (student == null || student.equals("")) {
                    errorMessage += "Student necompletat ";
                } else {
                    message += student;
                }
            }

            if (groupChecked) {
                String group = group_field.getText().toString();

                if (group == null || group.equals("")) {
                    errorMessage += "group necompletat";
                } else {
                    message += " " + group;
                }
            }

            if (!errorMessage.equals("")) {
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            }

            display_field.setText(message);

            if (groupChecked && studentChecked) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var03Service.class);
                intent.putExtra("student", student_field.getText().toString());
                intent.putExtra("group", group_field.getText().toString());
                getApplicationContext().startService(intent);
            }
        }

    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_main);

        display_button = (Button) findViewById(R.id.display_information_button);
        change_activity = (Button) findViewById(R.id.intent_button);
        student_checkbox = (CheckBox) findViewById(R.id.checkBox_student);
        group_checkbox = (CheckBox) findViewById(R.id.checkBox_group);
        student_field = (EditText) findViewById(R.id.student_field);
        group_field = (EditText) findViewById(R.id.group_field);
        display_field = (EditText) findViewById(R.id.information);

        display_button.setOnClickListener(buttonClickListener);
        change_activity.setOnClickListener(buttonClickListener);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var03Service.class);
        stopService(intent);
        super.onDestroy();
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("student_field", student_field.getText().toString());
        savedInstanceState.putString("group_field", group_field.getText().toString());
        savedInstanceState.putString("display_field", display_field.getText().toString());
        savedInstanceState.putBoolean("student_checked", student_checkbox.isChecked());
        savedInstanceState.putBoolean("group_checked", group_checkbox.isChecked());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("student_field")) {
            student_field.setText(savedInstanceState.getString("student_field"));
        } else {
            student_field.setText("");
        }
        if (savedInstanceState.containsKey("group_field")) {
            group_field.setText(savedInstanceState.getString("group_field"));
        } else {
            group_field.setText("");
        }

        if (savedInstanceState.containsKey("display_field")) {
            display_field.setText(savedInstanceState.getString("display_field"));
        } else {
            display_field.setText("");
        }

        if (savedInstanceState.containsKey("student_checked")) {
            student_checkbox.setChecked(savedInstanceState.getBoolean("student_checked"));
        } else {
            student_checkbox.setChecked(false);
        }

        if (savedInstanceState.containsKey("group_checked")) {
            group_checkbox.setChecked(savedInstanceState.getBoolean("group_checked"));
        } else {
            student_checkbox.setChecked(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 200) {
            Toast.makeText(this, "Activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }
}
