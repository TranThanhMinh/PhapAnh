package anhpha.clientfirst.crm.model;

import java.io.Serializable;

/**
 * Created by mc975 on 2/9/17.
 */
public class MEmail implements Serializable {
    private int email_user_id;
    private int  client_id;
    private int client_delivery_id;
    private String content_email;
    private double latitude;
    private double longitude;
    private int user_id;
    private String user_name;


    public int getEmail_user_id() {
        return email_user_id;
    }

    public void setEmail_user_id(int email_user_id) {
        this.email_user_id = email_user_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getClient_delivery_id() {
        return client_delivery_id;
    }

    public void setClient_delivery_id(int client_delivery_id) {
        this.client_delivery_id = client_delivery_id;
    }

    public String getContent_email() {
        return content_email;
    }

    public void setContent_email(String content_email) {
        this.content_email = content_email;
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
}
