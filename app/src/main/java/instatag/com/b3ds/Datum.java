package instatag.com.b3ds;

/**
 * Created by RahulReign on 21-03-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Datum implements Serializable{

    @SerializedName("0")
    @Expose
    private instatag.com.b3ds._0 _0;
    @SerializedName("Service")
    @Expose
    private Service service;

    public instatag.com.b3ds._0 get0() {
        return _0;
    }

    public void set0(instatag.com.b3ds._0 _0) {
        this._0 = _0;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}


