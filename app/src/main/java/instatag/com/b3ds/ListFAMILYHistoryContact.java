package instatag.com.b3ds;

/**
 * Created by RahulReign on 09-04-2018.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListFAMILYHistoryContact {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Datum_for_family_History> data = null;

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

    public List<Datum_for_family_History> getData() {
        return data;
    }

    public void setData(List<Datum_for_family_History> data) {
        this.data = data;
    }

}
