package instatag.com.b3ds;

/**
 * Created by RahulReign on 27-03-2018.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactListDoctor implements Serializable{

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private ArrayList<DoctorDatum> data = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public ArrayList<DoctorDatum> getData() {
        return data;
    }

    public void setData(ArrayList<DoctorDatum> data) {
        this.data = data;
    }

}


