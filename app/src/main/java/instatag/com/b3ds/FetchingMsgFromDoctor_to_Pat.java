package instatag.com.b3ds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import instatag.com.b3ds.models.Prefhelper;

public class FetchingMsgFromDoctor_to_Pat extends AppCompatActivity {

/*
    ListView listView;
    Adapter adapter;
    TextView bookappointmentTv;
    ArrayList<DoctorDatum> ls;
*/



    ListView listView;
    AdapterFor_PatientMsg_Activity adapterFor_patientMsg_activity;
    ArrayList<Datum_FetchingMsg>ls;
    AdapterFor_PatientMsg_Activity adapter;

    public static final MediaType JSON = MediaType.parse("application/json:charset=utf-8");


    private ContactServiceForFetchingMsgPatient serviceResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetching_msg_from_doctor_to__pat);
        listView=(ListView)findViewById(R.id.showingmsgID);


        //changinh coding here-below--------------------

        //  ls=(ArrayList<Datum_FetchingMsg>) getIntent().getSerializableExtra("doctordata");


        OkHttpClient client = new OkHttpClient();
        Request validation_request = listservices();
        client.newCall(validation_request).enqueue(new Callback() {

            @Override
            public void onFailure(Request  request, IOException e) {
                Log.i("Activity", "onFailure: Fail");
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                listServiceResponse(response.body().string());
                final boolean isSuccessful=serviceResponse.getStatus();
                final List<Datum_FetchingMsg> l=serviceResponse.getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isSuccessful) {

                            Toast.makeText(getApplication(), "List of Message ", Toast.LENGTH_SHORT).show();
                            AdapterForDoctorReplyToPat adapter=new AdapterForDoctorReplyToPat(FetchingMsgFromDoctor_to_Pat.this,l);
                            listView.setAdapter(adapter);
                        } else {
                            Toast.makeText(getApplication(), "Could not load the list!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
    private Request listservices() {
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id", Prefhelper.getInstance(FetchingMsgFromDoctor_to_Pat.this).getUserid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url("http://dev.feish.online/apis/patientCommunications")
                .post(body)
                .build();
        return request;
    }

    public void listServiceResponse(String response) {
        Gson gson = new GsonBuilder().create();
        serviceResponse = gson.fromJson(response, ContactServiceForFetchingMsgPatient.class);
    }
}
