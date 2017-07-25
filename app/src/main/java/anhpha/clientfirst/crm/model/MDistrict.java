package anhpha.clientfirst.crm.model;

/**
 * Created by mc975 on 2/15/17.
 */
public class MDistrict {
   private int district_id;
   private String district_code;
   private String district_name;
   private String district_description;
   private int status_id;
   private String create_date;
   private int province_id;
   private String modify_date;
   private int sort_order;
   private String type;
   private String location;
   private String province_code;

    public MDistrict() {
        this.district_id = 0;
        this.district_code = "";
        this.district_name = "";
        this.district_description = "";
        this.status_id = 0;
        this.create_date = "";
        this.province_id = 0;
        this.modify_date = "";
        this.sort_order = 0;
        this.type = "";
        this.location = "";
        this.province_code = "";
    }

    @Override
    public String toString() {
        return district_name;
    }
    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getDistrict_description() {
        return district_description;
    }

    public void setDistrict_description(String district_description) {
        this.district_description = district_description;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public String getModify_date() {
        return modify_date;
    }

    public void setModify_date(String modify_date) {
        this.modify_date = modify_date;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProvince_code() {
        return province_code;
    }

    public void setProvince_code(String province_code) {
        this.province_code = province_code;
    }
}
