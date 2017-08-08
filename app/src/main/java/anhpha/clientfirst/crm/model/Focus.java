package anhpha.clientfirst.crm.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * Created by MinhTran on 7/24/2017.
 */

public class Focus  implements Comparable<Focus> {

    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    @SerializedName("client_name")
    @Expose
    private String clientName;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("client_type_id")
    @Expose
    private Integer clientTypeId;
    @SerializedName("client_group_id")
    @Expose
    private Integer clientGroupId;
    @SerializedName("client_area_id")
    @Expose
    private Integer clientAreaId;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("user_manager_id")
    @Expose
    private Integer userManagerId;
    @SerializedName("client_focus_id")
    @Expose
    private Integer client_focus_id;
    @SerializedName("focus_status_id")
    @Expose
    private Integer focus_status_id;
    @SerializedName("focus_type_id")
    @Expose
    private Integer focusTypeId;
    @SerializedName("focus_target_id")
    @Expose
    private Integer focusTargetId;

    public Integer getClient_structure_id() {
        return client_structure_id;
    }

    public void setClient_structure_id(Integer client_structure_id) {
        this.client_structure_id = client_structure_id;
    }

    private Integer client_structure_id;

    @SerializedName("focus_status_name")
    @Expose
    private String focusStatusName;
    @SerializedName("focus_type_name")
    @Expose
    private String focusTypeName;
    @SerializedName("focus_target_name")
    @Expose
    private String focusTargetName;
    @SerializedName("begin_date")
    @Expose
    private String beginDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("number_order")
    @Expose
    private Integer numberOrder;
    @SerializedName("number_meeting")
    @Expose
    private Integer numberMeeting;
    @SerializedName("number_call")
    @Expose
    private Integer numberCall;
    @SerializedName("number_date")
    @Expose
    private Integer numberDate;
    @SerializedName("number_email")
    @Expose
    private Integer numberEmail;
    @SerializedName("number_event")
    @Expose
    private Integer numberEvent;
    @SerializedName("labels")
    @Expose
    private List<MClientLabel> labels = null;

    public boolean isCheck() {
        return Check;
    }

    public void setCheck(boolean check) {
        Check = check;
    }

    private boolean Check =false;
    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(Integer clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    public Integer getClientGroupId() {
        return clientGroupId;
    }

    public void setClientGroupId(Integer clientGroupId) {
        this.clientGroupId = clientGroupId;
    }

    public Integer getClientAreaId() {
        return clientAreaId;
    }

    public void setClientAreaId(Integer clientAreaId) {
        this.clientAreaId = clientAreaId;
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

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getUserManagerId() {
        return userManagerId;
    }

    public void setUserManagerId(Integer userManagerId) {
        this.userManagerId = userManagerId;
    }

    public Integer getClientFocusId() {
        return client_focus_id;
    }

    public void setClientFocusId(Integer clientFocusId) {
        this.client_focus_id = clientFocusId;
    }

    public Integer getFocusStatusId() {
        return focus_status_id;
    }

    public void setFocusStatusId(Integer focusStatusId) {
        this.focus_status_id = focusStatusId;
    }

    public Integer getFocusTypeId() {
        return focusTypeId;
    }

    public void setFocusTypeId(Integer focusTypeId) {
        this.focusTypeId = focusTypeId;
    }

    public Integer getFocusTargetId() {
        return focusTargetId;
    }

    public void setFocusTargetId(Integer focusTargetId) {
        this.focusTargetId = focusTargetId;
    }

    public String getFocusStatusName() {
        return focusStatusName;
    }

    public void setFocusStatusName(String focusStatusName) {
        this.focusStatusName = focusStatusName;
    }

    public String getFocusTypeName() {
        return focusTypeName;
    }

    public void setFocusTypeName(String focusTypeName) {
        this.focusTypeName = focusTypeName;
    }

    public String getFocusTargetName() {
        return focusTargetName;
    }

    public void setFocusTargetName(String focusTargetName) {
        this.focusTargetName = focusTargetName;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getNumberOrder() {
        return numberOrder;
    }

    public void setNumberOrder(Integer numberOrder) {
        this.numberOrder = numberOrder;
    }

    public Integer getNumberMeeting() {
        return numberMeeting;
    }

    public void setNumberMeeting(Integer numberMeeting) {
        this.numberMeeting = numberMeeting;
    }

    public Integer getNumberCall() {
        return numberCall;
    }

    public void setNumberCall(Integer numberCall) {
        this.numberCall = numberCall;
    }

    public Integer getNumberDate() {
        return numberDate;
    }

    public void setNumberDate(Integer numberDate) {
        this.numberDate = numberDate;
    }

    public Integer getNumberEmail() {
        return numberEmail;
    }

    public void setNumberEmail(Integer numberEmail) {
        this.numberEmail = numberEmail;
    }

    public Integer getNumberEvent() {
        return numberEvent;
    }

    public void setNumberEvent(Integer numberEvent) {
        this.numberEvent = numberEvent;
    }

    public List<MClientLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<MClientLabel> labels) {
        this.labels = labels;
    }


    @Override
    public int compareTo(@NonNull Focus focus) {
        if (numberDate.intValue() > focus.numberDate.intValue()) {
            return 1;
        }
        else if (numberDate.intValue() <  focus.numberDate.floatValue()) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
