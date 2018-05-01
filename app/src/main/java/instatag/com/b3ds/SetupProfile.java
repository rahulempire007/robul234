//package instatag.com.b3ds;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Base64;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.RadioButton;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.squareup.okhttp.Callback;
//import com.squareup.okhttp.MediaType;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.RequestBody;
//import com.squareup.okhttp.Response;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.net.ssl.HttpsURLConnection;
//
///**
// * Created by This Pc on 3/8/2018.
// */
//
//public class SetupProfile extends Activity {
//    ImageView imageUpload;
//    private EditText identityno,firstname,lastname,email,address,height,weight;
//    private int gender=1,marrital=1,occupation_val,ethinicity_val,bloodgroup_val,identitytype_val;
//    private TextView bmi,mobile;
//    private RadioButton radioButtonMale,radioButtonFemale,radioButtonMarried,radioButtonUnmarried;
//    private Spinner ethinicity,bloodgroup,occupation,identitytype;
//    private Button submit;
//    Bundle bundle;
//    public  static  final MediaType JSON= MediaType.parse("application/json:charset=utf-8");
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.newsetupprofileactivity);
//        Bundle bundle = getIntent().getExtras();
//
//        firstname=(EditText)findViewById(R.id.setupfirstname);
//        lastname=(EditText)findViewById(R.id.setuplastname);
//        email=(EditText)findViewById(R.id.setupemail);
//        mobile=(TextView) findViewById(R.id.setupmobile);
//
//        imageUpload=(ImageView)findViewById(R.id.uploadpImageID);
//        submit=(Button)findViewById(R.id.submitprofile);
//        address =(EditText)findViewById(R.id.setupaddress);
//        identityno =(EditText)findViewById(R.id.identityno);
//        height=(EditText)findViewById(R.id.setupheight);
//        weight=(EditText)findViewById(R.id.setupweight);
//        bmi=(TextView) findViewById(R.id.setupbmi);
//
//        radioButtonMale = (RadioButton) findViewById(R.id.radioMale);
//        radioButtonFemale = (RadioButton) findViewById(R.id.radioFemale);
//
//        radioButtonMarried = (RadioButton) findViewById(R.id.marriedid);
//        radioButtonUnmarried = (RadioButton) findViewById(R.id.unmarriedid);
//
//        occupation=(Spinner)findViewById(R.id.setupoccupation);
//
//        occupation.setOnItemSelectedListener(
//                new AdapterView.OnItemSelectedListener() {
//                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                        occupation_val=pos+1;
//                        //Log.i(TAG, "onItemSelected: "+pos);
//                        //Log.i(TAG, "onItemSelected: "+id);
//
//
//                    }
//                    public void onNothingSelected(AdapterView<?> parent) {
//                        occupation_val=1;
//                    }
//                });
//
//        bloodgroup=(Spinner)findViewById(R.id.setupbloodgroup);
//
//        bloodgroup.setOnItemSelectedListener(
//                new AdapterView.OnItemSelectedListener() {
//                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                        bloodgroup_val=pos+1;
//                        //Log.i(TAG, "onItemSelected: "+pos);
//                        //Log.i(TAG, "onItemSelected: "+id);
//
//
//                    }
//                    public void onNothingSelected(AdapterView<?> parent) {
//                        bloodgroup_val=1;
//                    }
//                });
//
//        ethinicity=(Spinner)findViewById(R.id.setupoccupation);
//        ethinicity.setOnItemSelectedListener(
//                new AdapterView.OnItemSelectedListener() {
//                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                        ethinicity_val=pos+1;
//                        //Log.i(TAG, "onItemSelected: "+pos);
//                        //Log.i(TAG, "onItemSelected: "+id);
//
//
//                    }
//                    public void onNothingSelected(AdapterView<?> parent) {
//                        ethinicity_val=1;
//                    }
//
//                });
//
//
//        identitytype=(Spinner)findViewById(R.id.setupoccupation);
//        identitytype.setOnItemSelectedListener(
//                new AdapterView.OnItemSelectedListener() {
//                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                        identitytype_val=pos+1;
//                        //Log.i(TAG, "onItemSelected: "+pos);
//                        //Log.i(TAG, "onItemSelected: "+id);
//
//
//                    }
//                    public void onNothingSelected(AdapterView<?> parent) {
//                        identitytype_val=1;
//                    }
//                });
//
//        imageUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//        firstname.setText(bundle.getString("firstname"));
//        lastname.setText(bundle.getString("lastname"));
//        email.setText(bundle.getString("email"));
//        mobile.setText(bundle.getString("mobile"));
//
//       // address.setText(bundle.getString("address"));
//        if(bundle.getBoolean("i"))
//            address.setText("");
//        else
//            address.setText(bundle.getString("address"));
//
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(radioButtonMale.isChecked())
//                {
//                    gender=1;
//                }
//                else if (radioButtonFemale.isChecked())
//                {
//                    gender=2;
//                }
//
//                if(radioButtonMarried.isChecked())
//                {
//                    marrital=1;
//                }
//                else if (radioButtonUnmarried.isChecked())
//                {
//                    marrital=2;
//                }
//
//                OkHttpClient client = new OkHttpClient();
//                Request validation_request=profile_request();
//                client.newCall(validation_request).enqueue(new Callback() {
//
//                    @Override
//                    public void onFailure(Request request, IOException e) {
//
//                        // Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
//                        Log.i("Activity", "onFailure: Fail");
//                    }
//                    @Override
//                    public void onResponse(final Response response) throws IOException {
//
//                        final boolean isSuccessful=profileresponse(response.body().string());
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if(isSuccessful){
//                                    double wgt=Double.parseDouble(weight.getText().toString());
//                                    double hgt=Double.parseDouble(height.getText().toString());
//                                    double hgtsq=Math.pow(hgt,2);
//                                    bmi.setText(Double.toString(wgt/hgtsq));
//                                    Toast.makeText(getApplicationContext(),"Update successful",Toast.LENGTH_LONG).show();
//
//                                    Intent intent=new Intent(SetupProfile.this,ViewProfilePatient.class);
//                                    //intent.putExtra("userid",userid);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                                else{
//                                    Toast.makeText(getApplicationContext(),"OOPS!!",Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
//                    }
//                });
//            }
//        });
//    }
//    private Request profile_request(){
//        JSONObject postdata = new JSONObject();
//
//        try {
//            postdata.put("user_id",Prefhelper.getInstance(SetupProfile.this).getUserid());
//            postdata.put("weight",weight.getText().toString());
//            postdata.put("height",height.getText().toString());
//            postdata.put("gender",Integer.toString(gender));
//            postdata.put("marital_status",Integer.toString(marrital));
//            postdata.put("address",address.getText().toString());
//            postdata.put("first_name",firstname.getText().toString());
//            postdata.put("last_name",lastname.getText().toString());
//            postdata.put("email",email.getText().toString());
//            postdata.put("mobile",mobile.getText().toString());
//            postdata.put("occupation_id",Integer.toString(occupation_val));
//            postdata.put("ethinicity_id",Integer.toString(ethinicity_val));
//            postdata.put("blood_group",Integer.toString(bloodgroup_val));
//            postdata.put("identity_type",Integer.toString(identitytype_val));
//            postdata.put("identity_id",identityno.getText().toString());
//            //postdata.put("height",height.getText().toString());
//            // postdata.put("address",address.getText().toString());
//        } catch(JSONException e){
//            e.printStackTrace();
//        }
//        RequestBody body = RequestBody.create(JSON,postdata.toString());
//        final Request request = new Request.Builder()
//                .addHeader("X-Api-Key","AB5433GMDF657VBB")
//                .addHeader("Content-Type", "application/json")
//                .url("http://dev.feish.online/apis/set_up_profile")
//                .post(body)
//                .build();
//        return request;
//    }
//    public boolean profileresponse(String response) {
//        Gson gson = new GsonBuilder().create();
//        ContactService profileResponse = gson.fromJson(response,ContactService.class);
//
//        return profileResponse.getStatus();
//    }
//}

package instatag.com.b3ds;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import instatag.com.b3ds.models.Prefhelper;

/**
 * Created by This Pc on 3/8/2018.
 */

public class SetupProfile extends AppCompatActivity {
    ProgressDialog pd;
    ImageView imageUpload;
    private EditText identityno, firstname, lastname, email, address, height, weight;
    private int gender = 1, marrital = 1, occupation_val, ethinicity_val, bloodgroup_val, identitytype_val;
    private TextView bmi, mobile;
    private RadioButton radioButtonMale, radioButtonFemale, radioButtonMarried, radioButtonUnmarried;
    private Spinner ethinicity, bloodgroup, occupation, identitytype;
    private Button submit;
    Bundle bundle;
    public static final MediaType JSON = MediaType.parse("application/json:charset=utf-8");
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 218;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsetupprofileactivity);
        Bundle bundle = getIntent().getExtras();

        pd = new ProgressDialog(SetupProfile.this);
        pd.setMessage("Loading");
        pd.setCancelable(false);


        firstname = (EditText) findViewById(R.id.setupfirstname);
        lastname = (EditText) findViewById(R.id.setuplastname);
        email = (EditText) findViewById(R.id.setupemail);
        mobile = (TextView) findViewById(R.id.setupmobile);

        imageUpload = (ImageView) findViewById(R.id.uploadpImageID);
        submit = (Button) findViewById(R.id.submitprofile);
        address = (EditText) findViewById(R.id.setupaddress);
        identityno = (EditText) findViewById(R.id.identityno);
        height = (EditText) findViewById(R.id.setupheight);
        weight = (EditText) findViewById(R.id.setupweight);
        bmi = (TextView) findViewById(R.id.setupbmi);

        radioButtonMale = (RadioButton) findViewById(R.id.radioMale);
        radioButtonFemale = (RadioButton) findViewById(R.id.radioFemale);

        radioButtonMarried = (RadioButton) findViewById(R.id.marriedid);
        radioButtonUnmarried = (RadioButton) findViewById(R.id.unmarriedid);

        occupation = (Spinner) findViewById(R.id.setupoccupation);

        occupation.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        occupation_val = pos + 1;
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        occupation_val = 1;
                    }
                });

        bloodgroup = (Spinner) findViewById(R.id.setupbloodgroup);

        bloodgroup.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        bloodgroup_val = pos + 1;
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        bloodgroup_val = 1;
                    }
                });

        ethinicity = (Spinner) findViewById(R.id.setupoccupation);
        ethinicity.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        ethinicity_val = pos + 1;
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        ethinicity_val = 1;
                    }

                });


        identitytype = (Spinner) findViewById(R.id.setupoccupation);
        identitytype.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        identitytype_val = pos + 1;
                        //Log.i(TAG, "onItemSelected: "+pos);
                        //Log.i(TAG, "onItemSelected: "+id);


                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        identitytype_val = 1;
                    }
                });

        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploaodImage();
            }
        });
        firstname.setText(bundle.getString("firstname"));
        lastname.setText(bundle.getString("lastname"));
        email.setText(bundle.getString("email"));
        mobile.setText(bundle.getString("mobile"));

        // address.setText(bundle.getString("address"));
        if (bundle.getBoolean("i"))
            address.setText("");
        else
            address.setText(bundle.getString("address"));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (radioButtonMale.isChecked()) {
                    gender = 1;
                } else if (radioButtonFemale.isChecked()) {
                    gender = 2;
                }

                if (radioButtonMarried.isChecked()) {
                    marrital = 1;
                } else if (radioButtonUnmarried.isChecked()) {
                    marrital = 2;
                }

                OkHttpClient client = new OkHttpClient();
                Request validation_request = profile_request();
                client.newCall(validation_request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Request request, IOException e) {

                        // Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                        Log.i("Activity", "onFailure: Fail");
                    }

                    @Override
                    public void onResponse(final Response response) throws IOException {

                        final boolean isSuccessful = profileresponse(response.body().string());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isSuccessful) {
                                    if(!weight.getText().toString().equals("") && !height.getText().toString().equals("")) {
                                        double wgt = Double.parseDouble(weight.getText().toString());
                                        double hgt = Double.parseDouble(height.getText().toString());
                                        double hgtsq = Math.pow(hgt, 2);
                                        bmi.setText(Double.toString(wgt / hgtsq));
                                    }
                                    Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(SetupProfile.this, ViewProfilePatient.class);
                                    //intent.putExtra("userid",userid);
                                    startActivity(intent);
                                    finish();
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

    static final Integer CAMERA = 0x5;

    private void uploaodImage() {

        checkPermission();
//        askForPermission(Manifest.permission.CAMERA, CAMERA);
        addLogo();
    }

    AlertDialog coupandialog;

    private void addLogo() {
        final CharSequence[] options = {"Take Photo",
                "Choose From Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SetupProfile.this);
        builder.setTitle("Add Photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else if (options[item].equals("Choose From Gallery")) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }
        });
        coupandialog = builder.create();
        coupandialog.show();
    }

    Bitmap selectimage;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                selectimage = (Bitmap) data.getExtras().get("data");
//                Uri selectedImageURI = data.getData();
                Uri selectedImageURI = getImageUri(getApplicationContext(), selectimage);
                InputStream imageStream = null;
                try {
                    imageStream = SetupProfile.this.getContentResolver().openInputStream(selectedImageURI);
                    selectimage = BitmapFactory.decodeStream(imageStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (!(selectimage == null)) {
                    imageUpload.setImageBitmap(selectimage);
                    byte[] bytedata = null;
                    apiCall(selectimage, selectedImageURI);
//                    apiCall(selectimage, entity);

                }
            } else if (requestCode == 2) {
                Uri selectedImageURI = data.getData();
                InputStream imageStream = null;
                try {
                    imageStream = SetupProfile.this.getContentResolver().openInputStream(selectedImageURI);
                    selectimage = BitmapFactory.decodeStream(imageStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (selectimage != null) {
                    imageUpload.setImageBitmap(selectimage);
                    apiCall(selectimage, selectedImageURI);

                }
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    private void apiCall(Bitmap selectimage, Uri entity) {
        pd.show();
        OkHttpClient client = new OkHttpClient();
        Request validation_request = profilePicrequest(entity);
        client.newCall(validation_request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(SetupProfile.this, "Failure", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onResponse(final Response response) throws IOException {

                final boolean isSuccessful = profilePicresponse(response.body().string());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();

                        if (isSuccessful) {
//                            double wgt=Double.parseDouble(weight.getText().toString());
//                            double hgt=Double.parseDouble(height.getText().toString());
//                            double hgtsq=Math.pow(hgt,2);
//                            bmi.setText(Double.toString(wgt/hgtsq));
                            Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_LONG).show();

//                            Intent intent=new Intent(SetupProfile.this,ViewProfilePatient.class);
//                            //intent.putExtra("userid",userid);
//                            startActivity(intent);
//                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "OOPS!!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(SetupProfile.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SetupProfile.this, permission)) {
                ActivityCompat.requestPermissions(SetupProfile.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(SetupProfile.this, new String[]{permission}, requestCode);
            }
        }
    }

    private Request profile_request() {
        JSONObject postdata = new JSONObject();

        try {
            postdata.put("user_id", Prefhelper.getInstance(SetupProfile.this).getUserid());
            postdata.put("weight", weight.getText().toString());
            postdata.put("height", height.getText().toString());
            postdata.put("gender", Integer.toString(gender));
            postdata.put("marital_status", Integer.toString(marrital));
            postdata.put("address", address.getText().toString());
            postdata.put("first_name", firstname.getText().toString());
            postdata.put("last_name", lastname.getText().toString());
            postdata.put("email", email.getText().toString());
            postdata.put("mobile", mobile.getText().toString());
            postdata.put("occupation_id", Integer.toString(occupation_val));
            postdata.put("ethinicity_id", Integer.toString(ethinicity_val));
            postdata.put("blood_group", Integer.toString(bloodgroup_val));
            postdata.put("identity_type", Integer.toString(identitytype_val));
            postdata.put("identity_id", identityno.getText().toString());
            //postdata.put("height",height.getText().toString());
            // postdata.put("address",address.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url("http://dev.feish.online/apis/set_up_profile")
                .post(body)
                .build();
        return request;
    }

    public boolean profileresponse(String response) {
        Gson gson = new GsonBuilder().create();
        ContactService profileResponse = gson.fromJson(response, ContactService.class);

        return profileResponse.getStatus();
    }

    public boolean profilePicresponse(String response) {
        Gson gson = new GsonBuilder().create();
        ContactService profileResponse = gson.fromJson(response, ContactService.class);

        return profileResponse.getStatus();
    }

    private Request profilePicrequest(Uri entity) {
        JSONObject postdata = new JSONObject();

        try {
            postdata.put("user_id", Prefhelper.getInstance(SetupProfile.this).getUserid());
            postdata.put("weight", weight.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        File sourceFile = new File(getRealPathFromUri(SetupProfile.this, entity));

        Log.d("", "File...::::" + sourceFile + " : " + sourceFile.exists());

        final MediaType MEDIA_TYPE = getRealPathFromUri(SetupProfile.this, entity).endsWith("png") ?
                MediaType.parse("image/png") : MediaType.parse("image/jpeg");


        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("user_id",Prefhelper.getInstance(SetupProfile.this).getUserid())
                .addFormDataPart("profile_pic", "profile.png", RequestBody.create(MEDIA_TYPE, sourceFile))
                .build();

        Request request = new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url("http://dev.feish.online/apis/uploadProfilePic")
                .post(requestBody)
                .build();

//        RequestBody body = RequestBody.create(JSON,postdata.toString());
//        final Request request = new Request.Builder()
//                .addHeader("X-Api-Key","AB5433GMDF657VBB")
//                .addHeader("Content-Type", "application/json")
//                .url("http://dev.feish.online/apis/set_up_profile")
//                .post(body)
//                .build();
        return request;
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(SetupProfile.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SetupProfile.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
    }
}
