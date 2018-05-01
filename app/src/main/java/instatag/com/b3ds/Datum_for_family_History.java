package instatag.com.b3ds;

/**
 * Created by RahulReign on 09-04-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum_for_family_History {

    @SerializedName("FamilyHistory")
    @Expose
    private FamilyHistory familyHistory;

    public FamilyHistory getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(FamilyHistory familyHistory) {
        this.familyHistory = familyHistory;
    }

}
