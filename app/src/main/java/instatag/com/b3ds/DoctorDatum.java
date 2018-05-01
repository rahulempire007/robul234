package instatag.com.b3ds;

/**
 * Created by RahulReign on 27-03-2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DoctorDatum implements Serializable {

    @SerializedName("Service")
    @Expose
    private DoctorService service;
    @SerializedName("User")
    @Expose
    private UserForListAvailbleDoctor user;
    @SerializedName("0")
    @Expose
    private instatag.com.b3ds.Doctor_0 _0;

    public DoctorService getService() {
        return service;
    }

    public void setService(DoctorService service) {
        this.service = service;
    }

    public instatag.com.b3ds.Doctor_0 get0() {
        return _0;
    }

    public void set0( instatag.com.b3ds.Doctor_0 _0) {
        this._0 = _0;
    }

    public UserForListAvailbleDoctor getUser() {
        return user;
    }

    public void setUser(UserForListAvailbleDoctor user) {
        this.user = user;
    }


}