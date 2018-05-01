package instatag.com.b3ds;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.app.Activity;
import android.app.AlertDialog;
public class BookAppointmentActivity extends Activity implements OnClickListener{
    private static String TAG = "PermissionDemo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookappointment_activity);


          findViewById(R.id.button1).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

        String phoneNumber = ((EditText)
                findViewById(R.id.editView1)).getText().toString();
        try {

            SmsManager.getDefault().sendTextMessage(phoneNumber, null, "Book Appointment SMS!", null, null);
        } catch (Exception e) {
            AlertDialog.Builder alertDialogBuilder = new
                    AlertDialog.Builder(this);
            AlertDialog dialog = alertDialogBuilder.create();


            dialog.setMessage(e.getMessage());


            dialog.show();


        }


    }


}
