package anhpha.clientfirst.crm.model;

import java.io.Serializable;

/**
 * Created by mc975 on 2/15/17.
 */
public class MClientGroup implements Serializable {
    private int client_group_id;
    private String client_group_name;
    private  boolean is_select;

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }
    @Override
    public String toString() {
        return  client_group_name ;
    }
    public int getClient_group_id() {
        return client_group_id;
    }

    public void setClient_group_id(int client_group_id) {
        this.client_group_id = client_group_id;
    }

    public String getClient_group_name() {
        return client_group_name;
    }

    public void setClient_group_name(String client_group_name) {
        this.client_group_name = client_group_name;
    }
}
