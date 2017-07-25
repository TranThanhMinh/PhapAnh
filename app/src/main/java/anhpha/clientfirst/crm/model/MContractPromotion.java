package anhpha.clientfirst.crm.model;

import java.io.Serializable;

/**
 * Created by mc975 on 2/6/17.
 */
public class MContractPromotion implements Serializable {

    public int promotion_id ;
    public int promotion_detail_id ;
    public int contract_id ;
    public int number ;
    public int contract_promotion_id ;
    public int number_promotion;
    public int unit_id ;
    public boolean is_select ;
    public String contract_name;
    public String contract_promotion_name ;
    public int promotion_type_id ;

    public int getPromotion_id() {
        return promotion_id;
    }

    public void setPromotion_id(int promotion_id) {
        this.promotion_id = promotion_id;
    }

    public int getPromotion_detail_id() {
        return promotion_detail_id;
    }

    public void setPromotion_detail_id(int promotion_detail_id) {
        this.promotion_detail_id = promotion_detail_id;
    }
    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getContract_promotion_id() {
        return contract_promotion_id;
    }

    public void setContract_promotion_id(int contract_promotion_id) {
        this.contract_promotion_id = contract_promotion_id;
    }
    public int getNumber_promotion() {
        return number_promotion;
    }

    public void setNumber_promotion(int number_promotion) {
        this.number_promotion = number_promotion;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    public String getContract_promotion_name() {
        return contract_promotion_name;
    }

    public void setContract_promotion_name(String contract_promotion_name) {
        this.contract_promotion_name = contract_promotion_name;
    }

    public int getPromotion_type_id() {
        return promotion_type_id;
    }

    public void setPromotion_type_id(int promotion_type_id) {
        this.promotion_type_id = promotion_type_id;
    }


}
