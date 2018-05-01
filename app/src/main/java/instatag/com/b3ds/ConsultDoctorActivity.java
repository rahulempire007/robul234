package instatag.com.b3ds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.media.MediaRouteProvider;
import android.util.Log;
import android.view.View;
import com.squareup.okhttp.Callback;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ConsultDoctorActivity extends AppCompatActivity {
    RadioGroup plangroup;
    RadioButton free,paid;
    String enteraddLabDoc,enterYouradd;
    EditText enteraddresslabname,enteryourAddress;
    TextView submit;
    private int specialist_doc_val;
    Spinner specialityspinner;
    private String specialityid;
    private ProgressBar pb;
    Button backtodashboardbtn,bookAppointmane;
    ContactListDoctor searchresponse;

    public  static  final MediaType JSON= MediaType.parse("application/json:charset=utf-8");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_doctor);
       // backtodashboardbtn=(Button)findViewById(R.id.dashboardbtnid);
        //bookAppointmane=(Button)findViewById(R.id.bookapointmentID);
        enteraddresslabname=(EditText)findViewById(R.id.searchdoctorID);
        enteryourAddress=(EditText)findViewById(R.id.searchaddressId);
        specialityspinner=(Spinner)findViewById(R.id.specialistID);
        free=(RadioButton)findViewById(R.id.freeplanID);
        paid=(RadioButton)findViewById(R.id.purchaseplanID);
        submit=(TextView)findViewById(R.id.submitDetailsId);


        specialityspinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        specialist_doc_val=pos+1;
                        //Log.i(TAG, "onItemSelected: "+pos);
                        //Log.i(TAG, "onItemSelected: "+id);


                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                        specialist_doc_val=1;
                    }
                });




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validatelogin()) {


                    OkHttpClient client = new OkHttpClient();
                    Request validation_request = search_request();
                    client.newCall(validation_request).enqueue(new Callback() {

                        @Override
                        public void onFailure(Request request, IOException e) {

                            // Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                            Log.i("Activity", "onFailure: Fail");
                        }

                        @Override
                        public void onResponse(final Response response) throws IOException {
                            String body = response.body().string();
                            Log.i(TAG, "onResponse: " + body);
                            searchJSON(body);

                            final String message = searchresponse.getMessage();
                            final ArrayList<DoctorDatum> doctorData = searchresponse.getData();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                pb.setVisibility(View.GONE);
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    if (message.compareTo("success") == 0) {
                                        Toast.makeText(getApplicationContext(), "search Successful", Toast.LENGTH_LONG).show();
                                        //Toast.makeText(getApplicationContext(), "search Successful", Toast.LENGTH_LONG).show();
                                       // Bundle b=new Bundle();
                                       // b.putSerializable("doctordata", (Serializable) doctorData);
                                        Intent i = new Intent(ConsultDoctorActivity.this, CaseDetailActivity.class);
                                        i.putExtra("doctordata",doctorData);
                                        // i.putExtra("data",(Serializable)openList.get(getAdapterPosition()));
                                        startActivity(i);

                                       // Intent i = new Intent(ConsultDoctorActivity.this, CaseDetailActivity.class);
                                       // i.putExtra("doctordata", doctorData);
                                        // i.putExtra("data",(Serializable)openList.get(getAdapterPosition()));
                                        //startActivity(i);


                                        //Intent intent2=new Intent(MainActivity.this,DashboardActivity.class);
                                        //  Intent intent=new Intent(ConsultDoctorActivity.this,DisplayDoctorList.class);
                                        //  intent.putExtra("data",(ArrayList)searchresponse.getData());
                                        //intent.putExtra("lastname",loginResponse.getData().getLastName());
                                        //intent.putExtra("email",loginResponse.getData().getEmail());
                                        //intent.putExtra("userid",loginResponse.getData().getId());
                                        //intent2.putExtras(intent);
                                        //startActivity(intent);
                                    }  else {
                                        Toast.makeText(getApplicationContext(), "No service found", Toast.LENGTH_LONG).show();


                                    }
                                }
                            });
                        }
                    });
//////////////////////////////----
                }
            }
        });


    }

    private boolean validatelogin(){
        enteraddLabDoc=enteraddresslabname.getText().toString();
        //Log.i("validate", "validatepassword: "+lusername);
        enterYouradd=enteryourAddress.getText().toString();
        //Log.i("validate", "validatepassword: "+lpassword);
        if(enteraddLabDoc.compareTo("")==0)
        {
            Toast.makeText(getApplicationContext()," field empty",Toast.LENGTH_LONG).show();
            return false;
        }
        else if (enterYouradd.compareTo("")==0)
        {
            Toast.makeText(getApplicationContext()," field empty",Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }

    private Request search_request(){
        JSONObject postdata = new JSONObject();
        try {

         /*  postdata.put("address",enteryourAddress.getText().toString());
            postdata.put("specialty_id",Integer.toString(specialist_doc_val));
            postdata.put("address",enteraddresslabname.getText().toString());
*/

           // postdata.put("specialty_id","3");
          //  postdata.put("user_id",MainActivity.userid);
            postdata.put("specialty_id",Integer.toString(specialist_doc_val));
            postdata.put("search",enteraddresslabname.getText().toString());
            postdata.put("address",enteryourAddress.getText().toString());



            //postdata.put("height",height.getText().toString());
            // postdata.put("address",address.getText().toString());
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON,postdata.toString());
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key","AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url("http://dev.feish.online/apis/listAvailableDoctor")
                .post(body)
                .build();
        return request;
    }

    public void searchJSON(String response) {
        Gson gson = new GsonBuilder().create();
    searchresponse= gson.fromJson(response,ContactListDoctor.class);
        //Data data= gson.fromJson(response,Data.class);
       // data.getAddress();

    }
}