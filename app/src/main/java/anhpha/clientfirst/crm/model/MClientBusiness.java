package anhpha.clientfirst.crm.model;

/**
 * Created by Window7 on 5/8/2017.
 */
public class MClientBusiness {
    private int client_business_id;
    private String client_business_name;
    private  boolean is_select;

    @Override
    public String toString() {
        return  client_business_name;
    }

    public int getClient_business_id() {
        return client_business_id;
    }

    public void setClient_business_id(int client_business_id) {
        this.client_business_id = client_business_id;
    }

    public String getClient_business_name() {
        return client_business_name;
    }

    public void setClient_business_name(String client_business_name) {
        this.client_business_name = client_business_name;
    }

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }
}
