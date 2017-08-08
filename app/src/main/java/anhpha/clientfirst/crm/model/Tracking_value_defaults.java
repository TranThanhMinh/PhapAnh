package anhpha.clientfirst.crm.model;

/**
 * Created by Administrator on 8/8/2017.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Tracking_value_defaults {
    public boolean isTracking() {
        return isTracking;
    }

    public void setTracking(boolean tracking) {
        isTracking = tracking;
    }

    private boolean isTracking = false;
    @SerializedName("user_id")
    @Expose
    private Integer user_id;
    @SerializedName("partner_id")
    @Expose
    private Integer partner_id;
    @SerializedName("create_date")
    @Expose
    private String create_date;
    @SerializedName("tracking_value_default_content")
    @Expose
    private String tracking_value_default_content;
    @SerializedName("tracking_value_default_id")
    @Expose
    private Integer tracking_value_default_id;
    @SerializedName("status_id")
    @Expose
    private Integer status_id;
    @SerializedName("tracking_user_type_id")
    @Expose
    private Integer tracking_user_type_id;
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(Integer partner_id) {
        this.partner_id = partner_id;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getTracking_value_default_content() {
        return tracking_value_default_content;
    }

    public void setTracking_value_default_content(String tracking_value_default_content) {
        this.tracking_value_default_content = tracking_value_default_content;
    }

    public Integer getTracking_value_default_id() {
        return tracking_value_default_id;
    }

    public void setTracking_value_default_id(Integer tracking_value_default_id) {
        this.tracking_value_default_id = tracking_value_default_id;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public Integer getTracking_user_type_id() {
        return tracking_user_type_id;
    }

    public void setTracking_user_type_id(Integer tracking_user_type_id) {
        this.tracking_user_type_id = tracking_user_type_id;
    }




}
