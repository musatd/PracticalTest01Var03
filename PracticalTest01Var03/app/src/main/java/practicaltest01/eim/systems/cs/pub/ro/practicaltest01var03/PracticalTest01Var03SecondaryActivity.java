package practicaltest01.eim.systems.cs.pub.ro.practicaltest01var03;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var03SecondaryActivity extends AppCompatActivity {

    private TextView student = null;
    private TextView group = null;
    private Button okButton = null;
    private Button cancelButton = null;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.ok:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancel:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_secondary);

        student = (TextView) findViewById(R.id.student);
        group = (TextView) findViewById(R.id.group);
        Intent intent = getIntent();

        if (intent != null) {
            student.setText(intent.getStringExtra("student"));
            group.setText(intent.getStringExtra("group"));
        }



        okButton = (Button)findViewById(R.id.ok);
        okButton.setOnClickListener(buttonClickListener);
        cancelButton = (Button)findViewById(R.id.cancel);
        cancelButton.setOnClickListener(buttonClickListener);

    }
}
