package instatag.com.b3ds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;



public class ListServices extends Fragment {

    //Constructor default
    protected ServiceListViewAdapter serviceAdapter;
    protected  ListView ls;
    public ListServices(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View PageTwo = inflater.inflate(R.layout.ist_services_fragment, container, false);
        ls=PageTwo.findViewById(R.id.servicelist);
        //serviceAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,serviceList);
        //ls.setAdapter(serviceAdapter);
        //Log.i(TAG, "onCreateView: ");
        return PageTwo;
    }



}
