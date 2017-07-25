package anhpha.clientfirst.crm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mc975 on 2/7/17.
 */
public class MWorkUser implements Serializable {

    private int  work_user_id;
    private String  content_work;
    private int  user_id ;
    private String  user_name;
    private String  create_date ;
    private String  work_begin_date;
    private String  work_end_date ;
    private int  work_user_type_id ;
    private int  reminded_ahead_type_id ;
    private int  status_id ;
    private int  status_date_id ;
    private List<MClient>  clients ;
    private List<MUser> users;
    private double  latitude ;
    private double  longitude;
    private Date date;

    public int getReminded_ahead_type_id() {
        return reminded_ahead_type_id;
    }

    public void setReminded_ahead_type_id(int reminded_ahead_type_id) {
        this.reminded_ahead_type_id = reminded_ahead_type_id;
    }

    public MWorkUser() {
        this.clients = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus_date_id() {
        return status_date_id;
    }

    public void setStatus_date_id(int status_date_id) {
        this.status_date_id = status_date_id;
    }

    public int getWork_user_id() {
        return work_user_id;
    }

    public void setWork_user_id(int work_user_id) {
        this.work_user_id = work_user_id;
    }

    public String getContent_work() {
        return content_work;
    }

    public void setContent_work(String content_work) {
        this.content_work = content_work;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getWork_begin_date() {
        return work_begin_date;
    }

    public void setWork_begin_date(String work_begin_date) {
        this.work_begin_date = work_begin_date;
    }

    public String getWork_end_date() {
        return work_end_date;
    }

    public void setWork_end_date(String work_end_date) {
        this.work_end_date = work_end_date;
    }

    public int getWork_user_type_id() {
        return work_user_type_id;
    }

    public void setWork_user_type_id(int work_user_type_id) {
        this.work_user_type_id = work_user_type_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public List<MClient> getClients() {
        return clients;
    }

    public void setClients(List<MClient> clients) {
        this.clients = clients;
    }

    public List<MUser> getUsers() {
        return users;
    }

    public void setUsers(List<MUser> users) {
        this.users = users;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
