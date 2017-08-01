package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MinhTran on 7/12/2017.
 */

public class History_order {

    @SerializedName("order_status_detail_id")
    @Expose
    private Integer orderStatusDetailId;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("order_status_id")
    @Expose
    private Integer orderStatusId;
    @SerializedName("order_status_name")
    @Expose
    private String orderStatusName;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;

    public Integer getOrderStatusDetailId() {
        return orderStatusDetailId;
    }

    public void setOrderStatusDetailId(Integer orderStatusDetailId) {
        this.orderStatusDetailId = orderStatusDetailId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Integer orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
