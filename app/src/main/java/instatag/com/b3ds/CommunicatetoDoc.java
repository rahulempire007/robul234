package instatag.com.b3ds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import instatag.com.b3ds.models.Prefhelper;

public class CommunicatetoDoc extends AppCompatActivity {
    ServiceData serviceData;
    DoctorService doctorService;
    DoctorDatum doctorData;
    Datum_FetchingMsg datum_fetchingMsg;
    ContactServiceforMsg contactServiceforMsg;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected static String userid;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    EditText msgtoDoctor_et, enterMsg_et, enterSub_et;
    Button submitMsgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicateto_doc);

        doctorData= (DoctorDatum) getIntent().getSerializableExtra("userData");

        msgtoDoctor_et = (EditText) findViewById(R.id.msgToID);
        enterMsg_et = (EditText) findViewById(R.id.enterMessgId);
        enterSub_et = (EditText) findViewById(R.id.enterSubId);
        submitMsgbtn = (Button) findViewById(R.id.submitMsgButtonId);

        submitMsgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                OkHttpClient client = new OkHttpClient();
                Request validation_request = login_request();
                client.newCall(validation_request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Request request, IOException e) {


                        Log.i("Activity", "onFailure: Fail");
                    }

                    @Override
                    public void onResponse(final Response response) throws IOException {

                        final boolean isSuccessful = loginJSON(response.body().string());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccessful) {

                                    Toast.makeText(getApplicationContext(), "Message sent! will respond you shortly!!!", Toast.LENGTH_LONG).show();


                                } else {
                                    Toast.makeText(getApplicationContext(), "OOPS!!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });


            }
        });

    }

    public boolean loginJSON(String response) {
        Gson gson = new GsonBuilder().create();
        contactServiceforMsg = gson.fromJson(response, ContactServiceforMsg.class);
        return contactServiceforMsg.getStatus();
    }


    private Request login_request() {
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id", Prefhelper.getInstance(CommunicatetoDoc.this).getUserid());

            postdata.put("subject", enterSub_et.getText().toString().trim());
            postdata.put("message", enterMsg_et.getText().toString().trim());
            postdata.put("reciever_user_id", doctorData.getService().getUserId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url("http://dev.feish.online/apis/communicateToDoctor")
                .post(body)
                .build();
        return request;
    }
}
