package anhpha.clientfirst.crm.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mc975 on 2/9/17.
 */
public class MCheckin  implements Serializable{

    private int  user_meeting_id ;
    private int  client_id ;
    private int  client_delivery_id ;
    private String  content_meeting ;
    private double  latitude;
    private double  longitude ;
    private int  user_id ;
    private List<MPhoto> photos;

    public int getUser_checkin_id() {
        return user_meeting_id;
    }

    public void setUser_checkin_id(int user_checkin_id) {
        this.user_meeting_id = user_checkin_id;
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

    public String getContent_checkin() {
        return content_meeting;
    }

    public void setContent_checkin(String content_checkin) {
        this.content_meeting = content_checkin;
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

    public List<MPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MPhoto> photos) {
        this.photos = photos;
    }
}
