package instatag.com.b3ds;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;


import android.widget.Button;

import android.widget.TextView;

import com.squareup.okhttp.MediaType;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.ArrayList;

import instatag.com.b3ds.models.Prefhelper;

import static android.content.ContentValues.TAG;
import static com.google.android.gms.internal.zzagz.runOnUiThread;

/**
 * Created by haade on 13/09/2017.
 */

public class AddServices extends Fragment {
    private EditText facilityname, description, address, placename, locality, pincode, phone;
    TextView save;
    private  int choosespecialist_val;
   // private Button submit;
    private Spinner chooseSpecialist;
   // private String specialitypos;
    ArrayList<StateVO> listVOs = new ArrayList<>();
    public static final MediaType JSON = MediaType.parse("application/json:charset=utf-8");

    final String[] select_qualification = {"Select Specialty", "Andrology", "Anesthesia", "Angiology", "Aviation Medicine", "BioMedicine", "Cardiology",
            "Dentistry", "Dermatology", "Disaster Medicine", "Emergency Medicine", "Family Medicine", "General Practice", "Dentistry", "Medical Genetics", "Gynaecology", "Infectious Disease"};
    //Constructor default
    /*public AddServices() {
    }
    ;*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View PageOne = inflater.inflate(R.layout.add_services_fragment, container, false);

        facilityname = (EditText)PageOne.findViewById(R.id.doctorfacilityNameID);
        description = (EditText) PageOne.findViewById(R.id.doctordescriptionID);
        address = (EditText)PageOne.findViewById(R.id.fulladdressID);
        placename = (EditText) PageOne.findViewById(R.id.placenameID);
        //submit = (Button) PageOne.findViewById(R.id.submitprofile);
        locality = (EditText) PageOne.findViewById(R.id.localityID);
        pincode = (EditText) PageOne.findViewById(R.id.pincodeID);
        phone = (EditText) PageOne.findViewById(R.id.phonenumID);
        chooseSpecialist = (Spinner) PageOne.findViewById(R.id.spinner);
        save = (TextView) PageOne.findViewById(R.id.saveservicebuttonid);



        chooseSpecialist.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        choosespecialist_val=pos+1;
                        //Log.i(TAG, "onItemSelected: "+pos);
                        //Log.i(TAG, "onItemSelected: "+id);


                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                        choosespecialist_val=1;
                    }
                });



        // firstname.setText(bundle.getString("firstname"));
        //lastname.setText(bundle.getString("lastname"));
        //email.setText(bundle.getString("email"));
        //mobile.setText(bundle.getString("mobile"));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OkHttpClient client = new OkHttpClient();
                Request validation_request = add_service_request();
                client.newCall(validation_request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Request request, IOException e) {

                        // Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                        Log.i("Activity", "onFailure: Fail");
                    }

                    @Override
                    public void onResponse(final Response response) throws IOException {

                        final boolean isSuccessful = addServiceResponse(response.body().string());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccessful) {

                                   // Toast.makeText(getContext(), "Update successful "+MainActivity.userid, Toast.LENGTH_LONG).show();
                                    Toast.makeText(getContext(), "Succesfully Added ", Toast.LENGTH_LONG).show();
                                    //Intent intent=new Intent(VerificationChoiceActivity.this,SetPasswordActivity.class);
                                    //intent.putExtra("userid",userid);
                                    //startActivity(intent);
                                } else {
                                    Toast.makeText(getContext(), "OOPS!!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
            }
        });

        return PageOne;
    }

    private Request add_service_request() {
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("title", facilityname.getText().toString());
            postdata.put("description", description.getText().toString());
            postdata.put("address", address.getText().toString());
            postdata.put("city", placename.getText().toString());
            postdata.put("locality", locality.getText().toString());
            postdata.put("pin_code", pincode.getText().toString());
            postdata.put("user_id", Prefhelper.getInstance(getActivity()).getUserid());
            postdata.put("specialty_id",Integer.toString(choosespecialist_val));
            postdata.put("phone", phone.getText().toString());
           // postdata.put("specialty_id", specialitypos);
            //postdata.put("height",height.getText().toString());
            // postdata.put("address",address.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url("http://dev.feish.online/apis/addService")
                .post(body)
                .build();
        return request;
    }

    public boolean addServiceResponse(String response) {
        Gson gson = new GsonBuilder().create();
        ContactService addServiceResponse = gson.fromJson(response, ContactService.class);

        return addServiceResponse.getStatus();
    }
}


