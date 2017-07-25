package anhpha.clientfirst.crm.model;

/**
 * Created by mc975 on 2/8/17.
 */
public class MTracking {
    private int  distance ;
    private int  user_id;
    private String  user_name ;
    private double  latitude ;
    private double  longitude ;
    private double  latitude_partner ;
    private double  longitude_partner ;
    private String  create_date;


    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
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

    public double getLatitude_partner() {
        return latitude_partner;
    }

    public void setLatitude_partner(double latitude_partner) {
        this.latitude_partner = latitude_partner;
    }

    public double getLongitude_partner() {
        return longitude_partner;
    }

    public void setLongitude_partner(double longitude_partner) {
        this.longitude_partner = longitude_partner;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}
