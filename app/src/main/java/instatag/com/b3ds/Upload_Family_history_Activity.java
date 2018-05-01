package instatag.com.b3ds;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

public class Upload_Family_history_Activity extends AppCompatActivity {

    TabLayout MyTabs;
    ViewPager MyPage;
    private AddFamilyHistory  add_family_history;
    private ListHistory list_history;
    public static final MediaType JSON = MediaType.parse("application/json:charset=utf-8");
   // private ContactServiceUploadFamilyHistory contactServiceUploadFamilyHistory;
private  ListFAMILYHistoryContact listFAMILYHistoryContact;


    //private ListServicesContact serviceResponse;//change here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__family_history_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MyTabs = (TabLayout)findViewById(R.id.MyTabs);
        MyPage = (ViewPager)findViewById(R.id.MyPage);

        MyTabs.setupWithViewPager(MyPage);
        SetUpViewPager(MyPage);

        //Thanks For Watching and Keep Learning With ClipCodes on YouTube
    }

    public void SetUpViewPager (final ViewPager viewpage){
        MyViewPageAdapter Adapter = new MyViewPageAdapter(getSupportFragmentManager());
        add_family_history=new AddFamilyHistory();
        //change //addServices=new AddServices();
        //change //listServices=new ListServices();
        list_history=new ListHistory();
         Adapter.AddFragmentPage(add_family_history, "ADD Report");
        Adapter.AddFragmentPage(list_history, "LIST Report");

        //We Need Fragment class now

        viewpage.setAdapter(Adapter);
        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int i;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                i=viewpage.getCurrentItem();
                Log.i("Services Activity", "onPageSelected: "+i);
                if(i==1)
                {
                    OkHttpClient client = new OkHttpClient();
                    Request validation_request = listservices();
                    client.newCall(validation_request).enqueue(new Callback() {

                        @Override
                        public void onFailure(Request request, IOException e) {

                            // Toast.makeText(getApplicationContext(),"Fail",Toast.LENGTH_LONG).show();
                            Log.i("Activity", "onFailure: Fail");
                        }

                        @Override
                        public void onResponse(final Response response) throws IOException {
                            listServiceResponse(response.body().string());
                            final boolean isSuccessful=listFAMILYHistoryContact.getStatus();
                            final List<Datum_for_family_History> l=listFAMILYHistoryContact.getData();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (isSuccessful) {

                                        Toast.makeText(getApplication(), "Record List", Toast.LENGTH_SHORT).show();
                                        List_FamilyHistory_ViewAdapter adapter=new List_FamilyHistory_ViewAdapter(getApplicationContext(),l);
                                        list_history.ls.setAdapter(adapter);
                                        //l.toArray();
                                        /*for (int i=0;i<l.size();i++){
                                            Datum2 d=(Datum2) l.get(i);
                                            d.getService().
                                        }*/
                                        //new ListServices().serviceAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,
                                        //       android.R.id.text1,l.toArray());
                                        //Intent intent=new Intent(VerificationChoiceActivity.this,SetPasswordActivity.class);
                                        //intent.putExtra("userid",userid);
                                        //startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplication(), "Could not load the list!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


    }
    private Request listservices() {
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id", Prefhelper.getInstance(Upload_Family_history_Activity.this).getUserid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, postdata.toString());
        final Request request = new Request.Builder()
                .addHeader("X-Api-Key", "AB5433GMDF657VBB")
                .addHeader("Content-Type", "application/json")
                .url("http://dev.feish.online/apis/listFamilyHistory")
                .post(body)
                .build();
        return request;
    }

    public void listServiceResponse(String response) {
        Gson gson = new GsonBuilder().create();
        listFAMILYHistoryContact = gson.fromJson(response, ListFAMILYHistoryContact .class);
    }

    public class MyViewPageAdapter extends FragmentPagerAdapter{
        private List<Fragment> MyFragment = new ArrayList<>();
        private List<String> MyPageTittle = new ArrayList<>();

        public MyViewPageAdapter(FragmentManager manager){
            super(manager);
        }

        public void AddFragmentPage(Fragment Frag, String Title){
            MyFragment.add(Frag);
            MyPageTittle.add(Title);
        }

        @Override
        public Fragment getItem(int position) {
            return MyFragment.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return MyPageTittle.get(position);
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


}