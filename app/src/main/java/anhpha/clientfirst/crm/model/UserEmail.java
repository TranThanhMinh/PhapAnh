package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 8/8/2017.
 */


public class UserEmail {
    @SerializedName("user_email_id")
    @Expose
    private Integer userEmailId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("content_email")
    @Expose
    private String contentEmail;
    @SerializedName("email_to")
    @Expose
    private Object emailTo;
    @SerializedName("email_from")
    @Expose
    private Object emailFrom;
    @SerializedName("order_product_id")
    @Expose
    private Integer orderProductId;
    @SerializedName("email_subject")
    @Expose
    private Object emailSubject;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("_client_id")
    @Expose
    private Integer _client_id;
    @SerializedName("client_name")
    @Expose
    private String clientName;
    @SerializedName("update_status")
    @Expose
    private Integer updateStatus;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("update_time")
    @Expose
    private Integer updateTime;
    @SerializedName("values_default")
    @Expose
    private List<Tracking_value_defaults> valuesDefault = null;

    public Integer getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(Integer userEmailId) {
        this.userEmailId = userEmailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getContentEmail() {
        return contentEmail;
    }

    public void setContentEmail(String contentEmail) {
        this.contentEmail = contentEmail;
    }

    public Object getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(Object emailTo) {
        this.emailTo = emailTo;
    }

    public Object getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(Object emailFrom) {
        this.emailFrom = emailFrom;
    }

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Object getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(Object emailSubject) {
        this.emailSubject = emailSubject;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getAClientId() {
        return _client_id;
    }

    public void setAClientId(Integer aClientId) {
        this._client_id = aClientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public List<Tracking_value_defaults> getValuesDefault() {
        return valuesDefault;
    }

    public void setValuesDefault(List<Tracking_value_defaults> valuesDefault) {
        this.valuesDefault = valuesDefault;
    }

}
