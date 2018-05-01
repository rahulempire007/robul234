package instatag.com.b3ds;

/**
 * Created by RahulReign on 27-03-2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;



public class ServiceListViewAdapter extends BaseAdapter {


    private Context context;
    private List<Datum2> data;
    private static LayoutInflater inflater=null;

    public ServiceListViewAdapter(Context context, List<Datum2> data) {
        this.context = context;
        this.data=data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
            view = inflater.inflate(R.layout.servicelistitem, null);
        TextView name = view.findViewById(R.id.servicelistname); // title
        TextView address = view.findViewById(R.id.servicelistaddress); // artist name
        TextView phone = view.findViewById(R.id.servicelistphone);
        Datum2 doctoritem=data.get(i);
        name.setText(doctoritem.getService().getTitle());
        address.setText(doctoritem.getService().getAddress());
        phone.setText(doctoritem.getService().getPhone());
        return view;
    }
}
