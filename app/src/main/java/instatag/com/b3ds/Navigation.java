package instatag.com.b3ds;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    ContactService_getDetails contactService_getDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


       final ImageView cover = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.showingPatImgId); //get your ImageView
      //load the image into ImageView
        final TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.currentUserId);
        final TextView txtProfileNameEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userEmailId);
        setSupportActionBar(toolbar);

       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
            //@Override
           // public void onClick(View view) {
          //      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //            .setAction("Action", null).show();
        //    }
       // });
///////////////////////////////////////////////////////////////////////////////////
        OkHttpClient client = new OkHttpClient();
        Request request = login_request();
       // Log.i(TAG, "onClick: "+request);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("Activity", "onFailure: Fail");
            }
            @Override
            public void onResponse(final Response response) throws IOException {

                String body=response.body().string();
              //  Log.i(TAG, "onResponse: "+body);
                loginJSON(body);
                final String message = contactService_getDetails.getMessage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //pb.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                        if (message.compareTo("success") == 0) {

                            txtProfileName.setText("Welcome"+" "+contactService_getDetails.getData().getFirstName());
                            txtProfileNameEmail.setText(contactService_getDetails.getData().getEmail());
                            navigationView.setNavigationItemSelectedListener(Navigation.this);
                            Glide.with(Navigation.this).load(contactService_getDetails.getData().getAvatar()).into(cover);

                            //first_name_tv.setText(contactService_getDetails.getData().getFirstName());
                            //last_name_tv.setText(contactService_getDetails.getData().getLastName());
                            //email_tv.setText(contactService_getDetails.getData().getEmail());
                            //mobile_tv.setText(contactService_getDetails.getData().getMobile());

                            //   address_tv.setText(contactService_getDetails.getData().getAddress().toString());
                            //if(contactService_getDetails.getData().getAddress()!=null)
                              //  address_tv.setText(contactService_getDetails.getData().getAddress().toString());


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


////////////////////////////////////////////////////////////////////////////////////////


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_DashboardforPatient) {
            Intent intent=new Intent(Navigation.this,DashboardActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.nav_consultDoctor) {
            Intent intent=new Intent(Navigation.this,ConsultDoctorActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_checkmsg) {

            Intent intent=new Intent(Navigation.this,FetchingMsgFromDoctor_to_Pat.class);
            startActivity(intent);

        } else if (id == R.id.nav_uploadhistory) {
            Intent intent=new Intent(Navigation.this,Upload_Family_history_Activity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            startActivity(new Intent(Navigation.this,MainActivity.class));
            Prefhelper.getInstance(Navigation.this).setLoggedIn(false);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void loginJSON(String response) {
        Gson gson = new GsonBuilder().create();
        contactService_getDetails = gson.fromJson(response, ContactService_getDetails.class);
    }


    private Request login_request() {
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id", Prefhelper.getInstance(Navigation.this).getUserid());
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
