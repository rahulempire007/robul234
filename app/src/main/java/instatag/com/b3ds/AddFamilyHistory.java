package instatag.com.b3ds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static com.google.android.gms.internal.zzagz.runOnUiThread;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFamilyHistory extends Fragment {
    private EditText enter_memberName_et, enter_diseaseNmae_et, enter_description_et, enter_Year_et, enterAge_et;
    TextView save;
    private  int chooseRelationship_val,chooseCurrentStatus_val;
    private Button submit;
    private Spinner chooseRelationShip,chooseCurrentStatus;
    private String specialitypos;
    ArrayList<StateVO> listVOs = new ArrayList<>();
    public static final MediaType JSON = MediaType.parse("application/json:charset=utf-8");



    public AddFamilyHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_family_history, container, false);
        View PageOne = inflater.inflate(R.layout.fragment_add_family_history, container, false);
        enter_memberName_et = (EditText)PageOne.findViewById(R.id.entermemberNameID);
        enter_diseaseNmae_et = (EditText) PageOne.findViewById(R.id.diseaseNameID);
        enter_description_et = (EditText)PageOne.findViewById(R.id.enterDescriptionID);
        enter_Year_et = (EditText) PageOne.findViewById(R.id.enterYearID);
        enterAge_et = (EditText) PageOne.findViewById(R.id.enterAgeOfMemberID);
        chooseRelationShip = (Spinner) PageOne.findViewById(R.id.selectRelationID);
        chooseCurrentStatus = (Spinner) PageOne.findViewById(R.id.selectCurrentStatusID);
        save = (TextView) PageOne.findViewById(R.id.savehistorybuttonid);


        chooseRelationShip.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        chooseRelationship_val=pos+1;
                        //Log.i(TAG, "onItemSelected: "+pos);
                        //Log.i(TAG, "onItemSelected: "+id);
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                        chooseRelationship_val=1;
                    }
                });
        chooseCurrentStatus.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        chooseCurrentStatus_val=pos+1;
                        //Log.i(TAG, "onItemSelected: "+pos);
                        //Log.i(TAG, "onItemSelected: "+id);
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                        chooseCurrentStatus_val=1;
                    }
                });



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
            postdata.put("user_id", Prefhelper.getInstance(getActivity()).getUserid());
            postdata.put("member_name", enter_memberName_et.getText().toString());
            postdata.put("description", enter_description_et.getText().toString());
            postdata.put("age", enterAge_et.getText().toString());
            postdata.put("disease_id", enter_diseaseNmae_et.getText().toString());
            postdata.put("year", enter_Year_et.getText().toString());
           // postdata.put("pin_code", pincode.getText().toString());
           // postdata.put("user_id", MainActivity.userid);
            postdata.put("relationship_id",Integer.toString(chooseRelationship_val));
            postdata.put("current_status",Integer.toString(chooseCurrentStatus_val));
           // postdata.put("phone", phone.getText().toString());
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
                .url("http://dev.feish.online/apis/addPatientFamilyHistory")
                .post(body)
                .build();
        return request;
    }

    public boolean addServiceResponse(String response) {
        Gson gson = new GsonBuilder().create();
     ContactServiceUploadFamilyHistory addServiceResponse = gson.fromJson(response, ContactServiceUploadFamilyHistory.class);

        return addServiceResponse.getStatus();
    }

}
