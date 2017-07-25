package anhpha.clientfirst.crm.model;

import java.io.Serializable;

/**
 * Created by mc975 on 2/15/17.
 */
public class MClientType implements Serializable {
    private int client_type_id;
    private String client_type_name;
    private  boolean is_select;

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    @Override
    public String toString() {
        return  client_type_name ;
    }
    public int getClient_type_id() {
        return client_type_id;
    }

    public void setClient_type_id(int client_type_id) {
        this.client_type_id = client_type_id;
    }

    public String getClient_type_name() {
        return client_type_name;
    }

    public void setClient_type_name(String client_type_name) {
        this.client_type_name = client_type_name;
    }
}
