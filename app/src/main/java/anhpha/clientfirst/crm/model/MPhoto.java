package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mc975 on 2/7/17.
 */
public class MPhoto implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("url")
    public String url;

    public String local;

    public String name;

    public MPhoto() {
        id = 0;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
