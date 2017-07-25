package anhpha.clientfirst.crm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mc975 on 2/6/17.
 */
public class MProgramPromotion implements Serializable {
        public int promotion_id ;
        public String promotion_name ;
        public String promotion_content;
        public int promotion_type_id ;
        public int promotion_by_type_id ;
        public int promotion_by_quantity_id;
        public double order_amount;
        public Double value ;
        public boolean is_select ;
        public List<MContractPromotion> contracts;

    public int getPromotion_id() {
        return promotion_id;
    }

    public void setPromotion_id(int promotion_id) {
        this.promotion_id = promotion_id;
    }

    public String getPromotion_name() {
        return promotion_name;
    }

    public void setPromotion_name(String promotion_name) {
        this.promotion_name = promotion_name;
    }

    public String getPromotion_content() {
        return promotion_content;
    }

    public void setPromotion_content(String promotion_content) {
        this.promotion_content = promotion_content;
    }

    public int getPromotion_type_id() {
        return promotion_type_id;
    }

    public void setPromotion_type_id(int promotion_type_id) {
        this.promotion_type_id = promotion_type_id;
    }

    public int getPromotion_by_type_id() {
        return promotion_by_type_id;
    }

    public void setPromotion_by_type_id(int promotion_by_type_id) {
        this.promotion_by_type_id = promotion_by_type_id;
    }

    public int getPromotion_by_quantity_id() {
        return promotion_by_quantity_id;
    }

    public void setPromotion_by_quantity_id(int promotion_by_quantity_id) {
        this.promotion_by_quantity_id = promotion_by_quantity_id;
    }

    public double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(double order_amount) {
        this.order_amount = order_amount;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    public List<MContractPromotion> getContracts() {
        return contracts;
    }

    public void setContracts(List<MContractPromotion> contracts) {
        this.contracts = contracts;
    }

    public MProgramPromotion() {
        this.contracts = new ArrayList<>();
    }
}
