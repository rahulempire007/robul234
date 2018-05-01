                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       package instatag.com.b3ds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
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

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       public class ViewProfilePatient extends AppCompatActivity {
    ContactService_getDetails contactService_getDetails;

    protected static String userid;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String TAG="MainActivity";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    TextView first_name_tv,last_name_tv,email_tv,mobile_tv,identity_type_tv,identity_val_tv,address_tv,height_tv,weight_tv,bmi_tv,bloodgroup_tv,ethnicity_tv,occuptaion_tv,registeredOn_Date;

    Button editButton;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_patient);
        first_name_tv=(TextView)findViewById(R.id.firstNameis);
        last_name_tv=(TextView)findViewById(R.id.lastNameis);
        email_tv=(TextView)findViewById(R.id.Emailis);
        mobile_tv=(TextView)findViewById(R.id.Mobilis);
        //registeredOn_Date=(TextView)findViewById(R.id.registerDateId);
        address_tv=(TextView)findViewById(R.id.addressforPatId);



        editButton=(Button)findViewById(R.id.editprofileID);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewProfilePatient.this,SetupProfile.class);
                intent.putExtra("firstname",first_name_tv.getText().toString());
                intent.putExtra("lastname",last_name_tv.getText().toString());
                intent.putExtra("email",email_tv.getText().toString());
                intent.putExtra("mobile",mobile_tv.getText().toString());
               // intent.putExtra("address",address_tv.getText().toString());
                if(address_tv.getText()==null)
                    intent.putExtra("i",true);
                else
                    intent.putExtra("address",address_tv.getText().toString());


               /* intent.putExtra("consultationtime",consultation_time_tv.getText().toString());
                intent.putExtra("qualification",qualification_tv.getText().toString());
                intent.putExtra("identityid",identity_val_tv.getText().toString());
                intent.putExtra("identity_type",identity_type_tv.getText().toString());*/
                startActivity(intent);
                finish();
            }
        });

        OkHttpClient client = new OkHttpClient();
        Request request = login_request();
        Log.i(TAG, "onClick: "+request);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("Activity", "onFailure: Fail");
            }
            @Override
            public void onResponse(final Response response) throws IOException {

                String body=response.body().string();
                Log.i(TAG, "onResponse: "+body);
                loginJSON(body);
                final String message = contactService_getDetails.getMessage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //pb.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        if (message.compareTo("success") == 0) {

                            first_name_tv.setText(contactService_getDetails.getData().getFirstName());
                            last_name_tv.setText(contactService_getDetails.getData().getLastName());
                            email_tv.setText(contactService_getDetails.getData().getEmail());
                            mobile_tv.setText(contactService_getDetails.getData().getMobile());

                         //   address_tv.setText(contactService_getDetails.getData().getAddress().toString());
                            if(contactService_getDetails.getData().getAddress()!=null)
                                address_tv.setText(contactService_getDetails.getData().getAddress().toString());


                            //   registeredOn_Date.setText(contactService_getDetails.getData().);
                           // qualification_tv.setText(contactService_getDetails.getData().getQualification());
                            //consultation_time_tv.setText(contactService_getDetails.getData().getConsultationTime());
                            //identity_val_tv.setText(contactService_getDetails.getData().getIdentityId());
                            //identity_type_tv.setText(contactService_getDetails.getData().getIdentityType());
                            // Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
                            // userid=contactService_getDetails.getData().getId();


                            //Intent intent2=new Intent(MainActivity.this,DashboardActivity.class);

                           /* if((loginResponse.getData().getUserType()).compareTo("4")==0) {

                                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                intent.putExtra("firstname", loginResponse.getData().getFirstName());
                                intent.putExtra("mobile", loginResponse.getData().getMobile());
                                intent.putExtra("lastname", loginResponse.getData().getLastName());
                                intent.putExtra("email", loginResponse.getData().getEmail());
                                //intent.putExtra("userid", loginResponse.getData().getId());
                                //intent2.putExtras(intent);
                                startActivity(intent);
                            }*/
                           /* else{
                                Intent intent = new Intent(MainActivity.this, DoctorDashboardActivity.class);
                                //intent.putExtra("mci_number", loginResponse.getData().getMciNumber().toString());
                                intent.putExtra("firstname", loginResponse.getData().getFirstName());
                                intent.putExtra("mobile", loginResponse.getData().getMobile());
                                intent.putExtra("lastname", loginResponse.getData().getLastName());
                                intent.putExtra("email", loginResponse.getData().getEmail());
                                intent.putExtra("userid", loginResponse.getData().getId());
                                intent.putExtra("qualification",(String)loginResponse.getData().getQualification());
                                intent.putExtra("consultationtime",loginResponse.getData().getConsultationTime());
                                intent.putExtra("identitytype",(String)loginResponse.getData().getIdentityType());
                                intent.putExtra("identityid",(String)loginResponse.getData().getIdentityId());
                                //intent2.putExtras(intent);
                                startActivity(intent);

                            }*/
                        } /*else if (message.compareTo("Invalid username or password") == 0) {
                            Toast.makeText(getApplicationContext(), "Invalid Email or Password", Toast.LENGTH_LONG).show();
                        }*/ else {
                            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }



       /* bundle=getIntent().getExtras();
        first_name_tv.setText(bundle.getString("firstname"));
        last_name_tv.setText(bundle.getString("lastname"));
        email_tv.setText(bundle.getString("email"));
        mobile_tv.setText(bundle.getString("mobile"));
        qualification_tv.setText(bundle.getString("qualification"));
        consultation_time_tv.setText(bundle.getString("consultationtime"));
        identity_type_tv.setText(bundle.getString("identitytype"));
        identity_val_tv.setText(bundle.getString("identityid"));*/


            //   editButton.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View view) {
            //  Intent intent = new Intent(ViewProfileDoctor.this, SetupProfileForDoctor.class);
        /*intent.putExtra("firstname",bundle.getString("firstname"));
        //intent.putExtra("userid",MainActivity.userid);
        intent.putExtra("mobile",bundle.getString("mobile"));
        intent.putExtra("lastname",bundle.getString("lastname"));
        intent.putExtra("email",bundle.getString("email"));



*/
            //startActivity(intent);
            //finish();

            //  }
        });

    }


    public void loginJSON(String response) {
        Gson gson = new GsonBuilder().create();
        contactService_getDetails = gson.fromJson(response, ContactService_getDetails.class);
    }


    private Request login_request() {
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id", Prefhelper.getInstance(ViewProfilePatient.this).getUserid());
            //postdata.put("password",lpassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url("http://dev.feish.online/apis/getPatientdetails")
                .post(body)
                .build();
        return request;
    }
}