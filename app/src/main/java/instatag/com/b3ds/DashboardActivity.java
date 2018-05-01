package instatag.com.b3ds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout profilelayout,visitReportlayout,checkMsg_Layout,patient_history_layout;
    Bundle bundle;
    TextView consultdoctorTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        bundle= getIntent().getExtras();
        consultdoctorTV=(TextView)findViewById(R.id.consultdoctorid);
        visitReportlayout=(LinearLayout)findViewById(R.id.visitreportlayoutId);
        checkMsg_Layout=(LinearLayout)findViewById(R.id.patienthistoryID);
        checkMsg_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(DashboardActivity.this,FetchingMsgFromDoctor_to_Pat.class);
                startActivity(intent);
            }
        });
patient_history_layout=(LinearLayout)findViewById(R.id.patient_history_layout);
        patient_history_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this,Upload_Family_history_Activity.class);
                startActivity(intent);
            }
        });


        profilelayout=(LinearLayout)findViewById(R.id.dashboardprofile);
visitReportlayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent=new Intent(DashboardActivity.this,OCRReadingCapbality.class);
        startActivity(intent);

        //Toast.makeText(DashboardActivity.this,"Purchase Plan to use this facility",Toast.LENGTH_SHORT).show();

        //Intent intent=new Intent(DashboardActivity.this,VisitReportActivity.class);
        //startActivity(intent);
    }
});
        profilelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(DashboardActivity.this,ViewProfilePatient.class);
               /* intent.putExtra("firstname",bundle.getString("firstname"));
                intent.putExtra("userid",bundle.getString("userid"));
                intent.putExtra("mobile",bundle.getString("mobile"));
                intent.putExtra("lastname",bundle.getString("lastname"));
                intent.putExtra("email",bundle.getString("email"));*/
            startActivity(intent);
            }
        });
        consultdoctorTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this,ConsultDoctorActivity.class);
                startActivity(intent);
            }
        });
    }
}
