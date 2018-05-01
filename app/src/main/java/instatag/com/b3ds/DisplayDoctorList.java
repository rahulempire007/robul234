package instatag.com.b3ds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayDoctorList extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_doctor_list);
        tv=(TextView)findViewById(R.id.showresult);
       // tv.setText();
    }
}
