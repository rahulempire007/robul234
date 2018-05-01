package instatag.com.b3ds;

/**
 * Created by RahulReign on 09-04-2018.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;



public class List_FamilyHistory_ViewAdapter extends BaseAdapter {


    private Context context;
    private List<Datum_for_family_History> data;
    private static LayoutInflater inflater=null;

    public List_FamilyHistory_ViewAdapter(Context context, List<Datum_for_family_History> data) {
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
            view = inflater.inflate(R.layout.list_of_family_history_layout, null);
        TextView name = view.findViewById(R.id.showingMemberNameId); // title
        TextView relationship = view.findViewById(R.id.showingRelationId); // artist name
        TextView age = view.findViewById(R.id.showingAgeId);
        TextView diseaseName = view.findViewById(R.id.showingDiseaseId);
        TextView currentStatus = view.findViewById(R.id.showingCurrentStatusId);
        Datum_for_family_History doctoritem=data.get(i);
        name.setText(doctoritem.getFamilyHistory().getMemberName());
        relationship.setText(doctoritem.getFamilyHistory().getRelationshipId());
        age.setText(doctoritem.getFamilyHistory().getAge());
        diseaseName.setText(doctoritem.getFamilyHistory().getDiseaseId());
        currentStatus.setText(doctoritem.getFamilyHistory().getCurrentStatus());
        return view;
    }
}
