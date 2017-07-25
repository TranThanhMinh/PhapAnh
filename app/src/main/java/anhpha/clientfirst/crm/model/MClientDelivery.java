package anhpha.clientfirst.crm.model;

import java.io.Serializable;

/**
 * Created by mc975 on 2/7/17.
 */
public class MClientDelivery implements Serializable {
    private  String  address;
    private  String  province_code ;
    private  String  district_code ;
    private  String  ward_code ;
    private  double  latitude;
    private  double  longitude;
    private  int  client_delivery_id;


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public String getWard_code() {
        return ward_code;
    }

    public void setWard_code(String ward_code) {
        this.ward_code = ward_code;
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

    public int getClient_delivery_id() {
        return client_delivery_id;
    }

    public void setClient_delivery_id(int client_delivery_id) {
        this.client_delivery_id = client_delivery_id;
    }
}
