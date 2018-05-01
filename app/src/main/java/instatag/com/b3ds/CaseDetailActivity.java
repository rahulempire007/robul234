package instatag.com.b3ds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CaseDetailActivity extends AppCompatActivity {
ListView listView;
    Adapter adapter;
    TextView bookappointmentTv;
    List<DoctorDatum> ls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detail);
        listView=(ListView)findViewById(R.id.showingDoctorResult);
        //bookappointmentTv=(TextView)findViewById(R.id.bookappointmentID);
        ls=(List<DoctorDatum>) getIntent().getSerializableExtra("doctordata");
        adapter=new Adapter(getApplicationContext(), ls, new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(CaseDetailActivity.this,CommunicatetoDoc.class);
                intent.putExtra("userData",ls.get(position));

                startActivity(intent);
                           }
        });
        listView.setAdapter(adapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });
    }
}

/*
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.data;

public class CaseDetailActivity extends AppCompatActivity {
    ListView listView;
    Adapter adapter;
    TextView bookappointmentTv;

    ArrayList<DoctorDatum> ls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_case_detail);
        listView=(ListView)findViewById(R.id.showingDoctorResult);
       // bookappointmentTv=(TextView)findViewById(R.id.bookappointmentID);
        ls=(ArrayList<DoctorDatum>) getIntent().getSerializableExtra("doctordata");
        adapter=new Adapter(getApplicationContext(),ls, new Adapter().OnItemClickLitener() {
            @Override
            public void onitemClick(View v, int position) {


            }
        });
        listView.setAdapter(adapter);
    }
}
*/