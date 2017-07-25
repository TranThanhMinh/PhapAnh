package anhpha.clientfirst.crm.model;

/**
 * Created by Window7 on 5/8/2017.
 */
public class MEventDetail {
    private int event_id;
    private String client_name;
    private String note;
    private String address;
    private int client_id;
    private int user_id;
    private int event_detail_status_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getEvent_detail_status_id() {
        return event_detail_status_id;
    }

    public void setEvent_detail_status_id(int event_detail_status_id) {
        this.event_detail_status_id = event_detail_status_id;
    }
}
