package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by MinhTran on 7/24/2017.
 */

public class AddFocus implements Serializable {


    private Integer client_id;

    private Integer user_id;


    private Integer partner_id;


    private Integer focus_type_id;

    private Integer focus_target_id;



    private String begin_date;

    public Integer getClient_id() {
        return client_id;
    }

    public void setClient_id(Integer client_id) {
        this.client_id = client_id;
    }

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

    public Integer getFocus_type_id() {
        return focus_type_id;
    }

    public void setFocus_type_id(Integer focus_type_id) {
        this.focus_type_id = focus_type_id;
    }

    public Integer getFocus_target_id() {
        return focus_target_id;
    }

    public void setFocus_target_id(Integer focus_target_id) {
        this.focus_target_id = focus_target_id;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public Integer getNumber_date() {
        return number_date;
    }

    public void setNumber_date(Integer number_date) {
        this.number_date = number_date;
    }

    private String end_date;

    private Integer   number_date;


}
