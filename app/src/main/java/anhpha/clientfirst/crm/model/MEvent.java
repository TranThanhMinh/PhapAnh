package anhpha.clientfirst.crm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Window7 on 5/7/2017.
 */
public class MEvent implements Serializable {
    private int event_id;
    private String event_name;
    private String event_content;
    private String date_begin;
    private String date_end;
    private String address;
    private int event_detail_status_id;
    private int event_type_id;
    private int num_client;
    private List<MPhoto> photos;
    private List<MEventDetail> clients;

    public MEvent() {
        this.photos = new ArrayList<>();
        this.clients = new ArrayList<>();
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_content() {
        return event_content;
    }

    public void setEvent_content(String event_content) {
        this.event_content = event_content;
    }

    public String getDate_begin() {
        return date_begin;
    }

    public void setDate_begin(String date_begin) {
        this.date_begin = date_begin;
    }

    public String getDate_end() {
        return date_end;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEvent_detail_status_id() {
        return event_detail_status_id;
    }

    public void setEvent_detail_status_id(int event_detail_status_id) {
        this.event_detail_status_id = event_detail_status_id;
    }

    public int getEvent_type_id() {
        return event_type_id;
    }

    public void setEvent_type_id(int event_type_id) {
        this.event_type_id = event_type_id;
    }

    public int getNum_client() {
        return num_client;
    }

    public void setNum_client(int num_client) {
        this.num_client = num_client;
    }

    public List<MPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MPhoto> photos) {
        this.photos = photos;
    }

    public List<MEventDetail> getClients() {
        return clients;
    }

    public void setClients(List<MEventDetail> clients) {
        this.clients = clients;
    }
}
