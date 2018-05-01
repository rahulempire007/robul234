package instatag.com.b3ds;

import android.*;
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
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import instatag.com.b3ds.models.Prefhelper;
import okhttp3.MultipartBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;

import static android.R.attr.data;
import static instatag.com.b3ds.SetupProfile.getRealPathFromUri;

public class SetupProfileForDoctor extends AppCompatActivity {

    ProgressDialog pd;
    public static final int PICK_IMAGE = 100;



    private EditText identityno,qualification,consultationTime, firstname,lastname;
    TextView email,mobile;
    private Spinner identitytype;
    private int identitytype_val;
    Bundle bundle;
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 218;
    //ImageView uploadImage;

    ImageView imageUpload;
    Button updateProfilebtn;
    public  static  final MediaType JSON= MediaType.parse("application/json:charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_setup_profile_activity);
        firstname = (EditText) findViewById(R.id.setupfirstNameid);
        lastname = (EditText) findViewById(R.id.setuplastnameID);
        email = (TextView) findViewById(R.id.setupemailID);
        mobile = (TextView) findViewById(R.id.setupmobileID);
        imageUpload = (ImageView) findViewById(R.id.btn_upload);
        identitytype = (Spinner) findViewById(R.id.setupidentityproof);

        qualification = (EditText) findViewById(R.id.enterqualificationID);
        consultationTime = (EditText) findViewById(R.id.consultationtimeID);
        updateProfilebtn = (Button) findViewById(R.id.submitprofileDoctorID);
        identityno = (EditText) findViewById(R.id.enteridenetitynumID);
        bundle = getIntent().getExtras();
        pd = new ProgressDialog(SetupProfileForDoctor.this);
        pd.setMessage("Loading");
        pd.setCancelable(false);

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
        identityno.setText(bundle.getString("identityid"));

        //consultationTime.setText(bundle.getString("consultationtime"));
        //qualification.setText(bundle.getString("qualification"));
       // identitytype.setTag(bundle.get("identity_type"));


        if(bundle.getBoolean("i"))
        {
            consultationTime.setText("");
            qualification.setText("");
            identitytype.setTag("");

        }
        else {
            consultationTime.setText(bundle.getString("consultationtime"));
            qualification.setText(bundle.getString("qualification"));
            identitytype.setTag(bundle.get("identity_type"));
        }
        //intent.putExtra("identitytype",bundle.getString("identitytype"));


        //tyring to upload image-----------------------------------------------------








                identitytype.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        identitytype_val=pos+1;
                        identitytype.setPrompt("Select one");
                        //Log.i(TAG, "onItemSelected: "+pos);
                        //Log.i(TAG, "onItemSelected: "+id);


                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                        identitytype_val=1;
                    }
                });

        updateProfilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                OkHttpClient client = new OkHttpClient();
                Request validation_request=profile_request();
                client.newCall(validation_request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Request request, IOException e) {

                        // Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                        Log.i("Activity", "onFailure: Fail");
                    }
                    @Override
                    public void onResponse(final Response response) throws IOException {

                        final boolean isSuccessful=profileresponse(response.body().string());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(isSuccessful){

                                    Toast.makeText(getApplicationContext(),"Update successful",Toast.LENGTH_LONG).show();


                                    Intent intent=new Intent(SetupProfileForDoctor.this,ViewProfileDoctor.class);
                                    /*intent.putExtra("firstname",bundle.getString("firstname"));
                                    intent.putExtra("lastname",bundle.getString("lastname"));
                                    intent.putExtra("email",bundle.getString("email"));
                                    intent.putExtra("mobile",bundle.getString("mobile"));
                                    intent.putExtra("qualification",bundle.getString("qualification"));
                                    intent.putExtra("consultation_time",bundle.getString("consultation_time"));
                                    intent.putExtra("identity_type",bundle.getString("identity_type"));
                                    intent.putExtra("identity_id",bundle.getString("identity_id"));
                                  intent.putExtra("userid",userid);*/
                                   startActivity(intent);finish();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"OOPS!!",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });




            }
        });


    }
///////////////////////////////////////////////////////////////
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
        AlertDialog.Builder builder = new AlertDialog.Builder(SetupProfileForDoctor.this);
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
                    imageStream = SetupProfileForDoctor.this.getContentResolver().openInputStream(selectedImageURI);
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
                    imageStream = SetupProfileForDoctor.this.getContentResolver().openInputStream(selectedImageURI);
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
                Toast.makeText(SetupProfileForDoctor.this, "Failure", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }

            @Override
            public void onResponse(final Response response) throws IOException {

                final boolean isSuccessful = profileresponse(response.body().string());
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
        if (ContextCompat.checkSelfPermission(SetupProfileForDoctor.this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(SetupProfileForDoctor.this, permission)) {
                ActivityCompat.requestPermissions(SetupProfileForDoctor.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(SetupProfileForDoctor.this, new String[]{permission}, requestCode);
            }
        }
    }


    private Request profile_request(){
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id", Prefhelper.getInstance(SetupProfileForDoctor.this).getUserid());

            //postdata.put("bmi",bmi.getText().toString());

           postdata.put("first_name",firstname.getText().toString());
            postdata.put("last_name",lastname.getText().toString());
            postdata.put("email",email.getText().toString());
            postdata.put("mobile",mobile.getText().toString());
           postdata.put("qualification",qualification.getText().toString());
            postdata.put("consultation_time",consultationTime.getText().toString());

            postdata.put("identity_type",Integer.toString(identitytype_val));
            postdata.put("identity_id",identityno.getText().toString());
            //postdata.put("height",height.getText().toString());
            // postdata.put("address",address.getText().toString());
        } catch(JSONException e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON,postdata.toString());
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key","AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url("http://dev.feish.online/apis/set_up_profile")
                .post(body)
                .build();
        return request;
    }

    public boolean profileresponse(String response) {
        Gson gson = new GsonBuilder().create();
        ContactService profileResponse = gson.fromJson(response,ContactService.class);
        return profileResponse.getStatus();
    }
    private Request profilePicrequest(Uri entity) {
        JSONObject postdata = new JSONObject();

        try {
            postdata.put("user_id", Prefhelper.getInstance(SetupProfileForDoctor.this).getUserid());
           // postdata.put("weight", weight.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        File sourceFile = new File(getRealPathFromUri(SetupProfileForDoctor.this, entity));

        Log.d("", "File...::::" + sourceFile + " : " + sourceFile.exists());

        final MediaType MEDIA_TYPE = getRealPathFromUri(SetupProfileForDoctor.this, entity).endsWith("png") ?
                MediaType.parse("image/png") : MediaType.parse("image/jpeg");


        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addFormDataPart("user_id",Prefhelper.getInstance(SetupProfileForDoctor.this).getUserid())
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
            if (ContextCompat.checkSelfPermission(SetupProfileForDoctor.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SetupProfileForDoctor.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
    }
}
