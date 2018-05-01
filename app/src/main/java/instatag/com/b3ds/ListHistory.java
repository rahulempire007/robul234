package instatag.com.b3ds;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListHistory extends Fragment {
  //  protected ServiceListViewAdapter serviceAdapter;
    protected ListView ls;

    public ListHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View PageTwo = inflater.inflate(R.layout.fragment_list_history, container, false);
        ls=PageTwo.findViewById(R.id.historyListId);

        return PageTwo;
    }

}
