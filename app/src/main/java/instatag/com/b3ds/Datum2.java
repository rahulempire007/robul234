package instatag.com.b3ds;

/**
 * Created by RahulReign on 27-03-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum2 {

    @SerializedName("Service")
    @Expose
    private ServiceData service;

    public ServiceData getService() {
        return service;
    }

    public void setService(ServiceData service) {
        this.service = service;
    }

}
