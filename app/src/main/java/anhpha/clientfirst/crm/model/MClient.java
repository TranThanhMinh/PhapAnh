package anhpha.clientfirst.crm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mc975 on 2/7/17.
 */
public class MClient implements Serializable {
    private int client_id;
    private String  client_name;
    private int point_value;
    private String leve;
    private String phone;
    private String   token;
    private String  facebook;
    private String   twitter;
    private int gender;
    private String  email;
    private String birthday;
    private String key;
    private String passport;
    private double distance;
    private String address;
    private List<MPhoto> photos;
    private String contact;
    private String website;
    private String province_code;
    private String district_code;
    private String country_code;
    private String ward_code;
    private String client_code;
    private int  client_type_id;
    private int  client_group_id;
    private int  client_area_id;
    private String client_type_name;
    private String client_group_name;
    private String client_area_name;
    private int  route_id;
    private double latitude;
    private double longitude;
    private double user_latitude;
    private double user_longitude;
    private String description;
    private String manager;
    private String tax_code;
    private int status_id;
    private int user_manager_id;
    private String user_manager_name;
    private boolean is_select;
    private boolean is_activity;
    private boolean is_permission_edit;

    public int client_business_id;
    public String client_business_name ;
    public int num_dependents ;
    public double income ;
    public int marital_status_id ;
    public boolean share_client ;
    public int client_structure_id ;

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

    public int getNum_dependents() {
        return num_dependents;
    }

    public void setNum_dependents(int num_dependents) {
        this.num_dependents = num_dependents;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public int getMarital_status_id() {
        return marital_status_id;
    }

    public void setMarital_status_id(int marital_status_id) {
        this.marital_status_id = marital_status_id;
    }

    public boolean isShare_client() {
        return share_client;
    }

    public void setShare_client(boolean share_client) {
        this.share_client = share_client;
    }

    public int getClient_structure_id() {
        return client_structure_id;
    }

    public void setClient_structure_id(int client_structure_id) {
        this.client_structure_id = client_structure_id;
    }

    public boolean is_activity() {
        return is_activity;
    }

    public void setIs_activity(boolean is_activity) {
        this.is_activity = is_activity;
    }

    public String getUser_manager_name() {
        return user_manager_name;
    }

    public void setUser_manager_name(String user_manager_name) {
        this.user_manager_name = user_manager_name;
    }

    public boolean is_permission_edit() {
        return is_permission_edit;
    }

    public void setIs_permission_edit(boolean is_permission_edit) {
        this.is_permission_edit = is_permission_edit;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    private List<MClientDelivery> client_delivery;
    private List<MClientLabel> labels;

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public int getPoint_value() {
        return point_value;
    }

    public void setPoint_value(int point_value) {
        this.point_value = point_value;
    }

    public String getLeve() {
        return leve;
    }

    public void setLeve(String leve) {
        this.leve = leve;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<MPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MPhoto> photos) {
        this.photos = photos;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getWard_code() {
        return ward_code;
    }

    public void setWard_code(String ward_code) {
        this.ward_code = ward_code;
    }

    public String getClient_code() {
        return client_code;
    }

    public void setClient_code(String client_code) {
        this.client_code = client_code;
    }

    public int getClient_type_id() {
        return client_type_id;
    }

    public void setClient_type_id(int client_type_id) {
        this.client_type_id = client_type_id;
    }

    public int getClient_group_id() {
        return client_group_id;
    }

    public void setClient_group_id(int client_group_id) {
        this.client_group_id = client_group_id;
    }

    public int getClient_area_id() {
        return client_area_id;
    }

    public void setClient_area_id(int client_area_id) {
        this.client_area_id = client_area_id;
    }

    public String getClient_type_name() {
        return client_type_name;
    }

    public void setClient_type_name(String client_type_name) {
        this.client_type_name = client_type_name;
    }

    public String getClient_group_name() {
        return client_group_name;
    }

    public void setClient_group_name(String client_group_name) {
        this.client_group_name = client_group_name;
    }

    public String getClient_area_name() {
        return client_area_name;
    }

    public void setClient_area_name(String client_area_name) {
        this.client_area_name = client_area_name;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
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

    public double getUser_latitude() {
        return user_latitude;
    }

    public void setUser_latitude(double user_latitude) {
        this.user_latitude = user_latitude;
    }

    public double getUser_longitude() {
        return user_longitude;
    }

    public void setUser_longitude(double user_longitude) {
        this.user_longitude = user_longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getTax_code() {
        return tax_code;
    }

    public void setTax_code(String tax_code) {
        this.tax_code = tax_code;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getUser_manager_id() {
        return user_manager_id;
    }

    public void setUser_manager_id(int user_manager_id) {
        this.user_manager_id = user_manager_id;
    }

    public List<MClientDelivery> getClient_delivery() {
        return client_delivery;
    }

    public void setClient_delivery(List<MClientDelivery> client_delivery) {
        this.client_delivery = client_delivery;
    }

    public List<MClientLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<MClientLabel> labels) {
        this.labels = labels;
    }

    public MClient() {
        this.labels = new ArrayList<>();
        this.client_delivery = new ArrayList<>();
        this.photos = new ArrayList<>();
        this.birthday = "";
        this.is_select = false;
    }
}
