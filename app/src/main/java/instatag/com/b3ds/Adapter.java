package instatag.com.b3ds;

/**
 * Created by RahulReign on 27-03-2018.
 */
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

import java.io.IOException;
import java.util.List;
import java.util.prefs.Preferences;

import instatag.com.b3ds.models.Prefhelper;


public class Adapter extends BaseAdapter {
    private Context context;
    private List<DoctorDatum> data;
    private static LayoutInflater inflater = null;
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public Adapter(Context context, List<DoctorDatum> data, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.data = data;
        this.onItemClickListener = onItemClickListener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

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
    public View getView(final int position, View view, ViewGroup viewGroup) {

        if (view == null)
            view = inflater.inflate(R.layout.post, null);
        TextView name = view.findViewById(R.id.docNameid); // title
        TextView address = view.findViewById(R.id.addressid); // artist name
        TextView phone = view.findViewById(R.id.mobNumid);
        TextView docID = view.findViewById(R.id.useridgone);
        ImageView navigateToDotor = view.findViewById(R.id.navigate_to_map);

        final DoctorDatum doctoritem = data.get(position);
        // name.setText(doctoritem.getService().getTitle());
        name.setText(doctoritem.getUser().getFirstName());
        //name.setText(doctoritem.getService().getTitle());
        address.setText(doctoritem.getService().getAddress());
        phone.setText(doctoritem.getService().getPhone());
        docID.setText(doctoritem.getService().getUserId());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position);
            }
        });

        navigateToDotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Barcode.GeoPoint geoPoint= getLocationFromAddress(doctoritem.getService().getAddress());

                if(geoPoint!=null ) {
                    Location loc=new Location("");
                    loc.setLatitude(geoPoint.lat);
                    loc.setLongitude(geoPoint.lng);
                    Prefhelper.getInstance(context).setLoc(loc);
                }
               // context.startActivity(new Intent(context,MapActivity.class));
                Intent i=new Intent(context,MapActivity.class);
                i.putExtra("doctor address:",doctoritem.getService().getAddress()+" "+doctoritem.getService().getPhone());

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(i);
            }
        });


        return view;
    }



    /*
    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.TextView;

    import java.util.List;



    public class Adapter extends BaseAdapter {

    private Adapter  adapter;
        private Context context;
        private List<DoctorDatum> data;
        private static LayoutInflater inflater=null;
        OnItemClicklistener onItemClicklistener;

        public Adapter(Context context, List<DoctorDatum> data,
                       OnItemClicklistener onItemClicklistener) {
            this.context = context;
            this.data=data;
            this.onItemClicklistener=onItemClicklistener;
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public interface OnItemClicklistener {
            public void onItemClick(View view, int position);
        }


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
        public View getView(int i, View view, ViewGroup viewGroup) {

            if(view==null)
                view = inflater.inflate(R.layout.post, null);
            TextView name = view.findViewById(R.id.addresssid);
            TextView address = view.findViewById(R.id.docNameid);
            TextView phone = view.findViewById(R.id.mobNumid);
            DoctorDatum doctoritem=data.get(i);
            name.setText(doctoritem.getService().getTitle());
            address.setText(doctoritem.getService().getAddress());
            phone.setText(doctoritem.getService().getPhone());





            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onItemClicklistener.onItemClick(v, getItemId());

                }
            });
            return view;
        }
    }
    */
    public Barcode.GeoPoint getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        Barcode.GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new Barcode.GeoPoint((double) (location.getLatitude() * 1E6),
                    (double) (location.getLongitude() * 1E6));

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p1;
    }
}