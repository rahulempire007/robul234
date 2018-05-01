package instatag.com.b3ds;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.List;

import instatag.com.b3ds.models.Prefhelper;

import static com.google.android.gms.internal.zzagz.runOnUiThread;


public class AdapterFor_PatientMsg_Activity extends BaseAdapter {

    ContactServiceforMsg contactServiceforMsg;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    ContactService_getDetails contactService_getDetails;
//    private Context context;
    private List<Datum_FetchingMsg> data;
    private static LayoutInflater inflater = null;
    Button replyBtn;
    TextView sub, msg, patient_name, msgDate;
    PatientMessageActivity context;

    Adapter.OnItemClickListener onItemClickListener;
//    Datum_FetchingMsg doctoritem;


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }


    public AdapterFor_PatientMsg_Activity(PatientMessageActivity context, List<Datum_FetchingMsg> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    //make adapterconstructor for fetchingMsgFromDctortopat

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null)
            view = inflater.inflate(R.layout.container_fish, null);
        findids(view);
        setActions(position);

//        Datum_FetchingMsg doctoritem=data.get(i);
        sub.setText(data.get(position).getCommunication().getSubject());
        msg.setText(data.get(position).getCommunication().getMessage());
        patient_name.setText(data.get(position).getUser().getFirstName()+" "+data.get(position).getUser().getLastName());
        msgDate.setText(data.get(position).getCommunication().getCreated());

        return view;


    }

    private void setActions(final int position) {
        replyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }

//            private void openDialog() {
//                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//
//                alertDialog.setTitle("Reply to Patient");
//                alertDialog.setMessage("");
//
//                final EditText input = new EditText(context);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.MATCH_PARENT,
//                        LinearLayout.LayoutParams.MATCH_PARENT);
//                input.setLayoutParams(lp);
//                alertDialog.setView(input);
//
//                alertDialog.setPositiveButton("Reply",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                if(!input.getText().toString().equals("")) {
//                                    apiCall();
//                                }else{
//                                    Toast.makeText(context, "Please enter your message", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//
//                alertDialog.setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog dialog = alertDialog.create();
//                dialog.show();
//            }

           public void  openDialog(){
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = context.getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                dialogBuilder.setView(dialogView);

                final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

                dialogBuilder.setTitle("Reply here");
                dialogBuilder.setMessage("");
                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //do something with edt.getText().toString();
                        if(!edt.getText().toString().equals("")) {
                            apiCall(edt.getText().toString());
                        }
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass
                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();
            }

            private void apiCall(String msg) {
                OkHttpClient client = new OkHttpClient();
                Request validation_request = loginRequest(position,msg);
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
                                    Toast.makeText(context, "Message sent! will respond you shortly!!!", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, "OOPS!!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }


    private void findids(View view) {
        sub = view.findViewById(R.id.getsubID); // title
        msg = view.findViewById(R.id.getmsgID); // artist name
        replyBtn = view.findViewById(R.id.replyToPatId); // artist name
        patient_name = view.findViewById(R.id.getpatientNameID);
        msgDate = view.findViewById(R.id.msgdateID);
    }

    public boolean loginJSON(String response) {
        Gson gson = new GsonBuilder().create();
        contactServiceforMsg = gson.fromJson(response, ContactServiceforMsg.class);
        return contactServiceforMsg.getStatus();
    }


    private Request loginRequest(int position, String msg) {
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("user_id", Prefhelper.getInstance(context).getUserid());
            postdata.put("subject", sub.getText().toString().trim());
            postdata.put("message", msg);
            postdata.put("reciever_user_id", data.get(position).getCommunication().getUserId());

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
