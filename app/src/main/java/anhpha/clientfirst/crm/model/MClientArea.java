package anhpha.clientfirst.crm.model;

import java.io.Serializable;

/**
 * Created by mc975 on 2/15/17.
 */
public class MClientArea implements Serializable {
    private int client_area_id;
    private String client_area_name;
    private  boolean is_select;

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }
    @Override
    public String toString() {
        return  client_area_name ;
    }

    public int getClient_area_id() {
        return client_area_id;
    }

    public void setClient_area_id(int client_area_id) {
        this.client_area_id = client_area_id;
    }

    public String getClient_area_name() {
        return client_area_name;
    }

    public void setClient_area_name(String client_area_name) {
        this.client_area_name = client_area_name;
    }
}
