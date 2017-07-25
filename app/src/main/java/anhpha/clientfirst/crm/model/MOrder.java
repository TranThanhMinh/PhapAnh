package anhpha.clientfirst.crm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mc975 on 2/6/17.
 */
public class MOrder implements Serializable {

    public String user_name ;
    public String order_contract_no;
    public int order_contract_id ;
    public String note ;
    public int user_id ;
    public Double amount_non_discount ;
    public Double amount ;
    public Double amount_payment ;
    public Double discount_order_price ;
    public Double discount_order_percent ;
    public int payment_type_id ;
    public int inventory_contract_client_id ;
    public Double payment_status_id ;
    public int order_sheet_id;
    public Double tax_percent ;
    public Double surcharge_price ;
    public Double surcharge_percent;
    public Double discount_contract_price ;
    public Double discount_contract_percent ;
    public Double payment_client_money;
    public int order_status_id ;
    public int status_id ;
    public int partner_id ;
    public String partner_name ;
    public int client_id ;
    public String address ;
    public String order_contract_code ;
    public String order_contract_code_parent ;
    public String phone ;
    public String client_name ;
    public String create_date ;
    public List<MContract> order_detail_contracts;
    public List<MContract> contracts;
    public List<MProgramPromotion> promotions;
    public List<MDebt> debts;
    public String expected_completion_date ;
    public Double prepay ;
    public Double order_amount ;
    public int order_type_id;
    public Double latitude ;
    public Double longitude;
    public Double tax ;
    public boolean is_price_tax;

    public String getOrder_contract_code() {
        return order_contract_code;
    }

    public void setOrder_contract_code(String order_contract_code) {
        this.order_contract_code = order_contract_code;
    }

    public String getOrder_contract_code_parent() {
        return order_contract_code_parent;
    }

    public void setOrder_contract_code_parent(String order_contract_code_parent) {
        this.order_contract_code_parent = order_contract_code_parent;
    }

    public List<MContract> getContracts() {
        return contracts;
    }

    public void setContracts(List<MContract> contracts) {
        this.contracts = contracts;
    }

    public Double getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(Double order_amount) {
        this.order_amount = order_amount;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getOrder_contract_no() {
        return order_contract_no;
    }

    public void setOrder_contract_no(String order_contract_no) {
        this.order_contract_no = order_contract_no;
    }

    public int getInventory_contract_client_id() {
        return inventory_contract_client_id;
    }

    public void setInventory_contract_client_id(int inventory_contract_client_id) {
        this.inventory_contract_client_id = inventory_contract_client_id;
    }

    public int getOrder_contract_id() {
        return order_contract_id;
    }

    public void setOrder_contract_id(int order_contract_id) {
        this.order_contract_id = order_contract_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

   public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Double getAmount_non_discount() {
        return amount_non_discount;
    }

    public void setAmount_non_discount(Double amount_non_discount) {
        this.amount_non_discount = amount_non_discount;
    }

    public List<MDebt> getDebts() {
        return debts;
    }

    public void setDebts(List<MDebt> debts) {
        this.debts = debts;
    }

    public Double getAmount_payment() {
        return amount_payment;
    }

    public void setAmount_payment(Double amount_payment) {
        this.amount_payment = amount_payment;
    }

    public Double getDiscount_order_price() {
        return discount_order_price;
    }

    public void setDiscount_order_price(Double discount_order_price) {
        this.discount_order_price = discount_order_price;
    }

    public Double getDiscount_order_percent() {
        return discount_order_percent;
    }

    public void setDiscount_order_percent(Double discount_order_percent) {
        this.discount_order_percent = discount_order_percent;
    }

    public int getPayment_type_id() {
        return payment_type_id;
    }

    public void setPayment_type_id(int payment_type_id) {
        this.payment_type_id = payment_type_id;
    }

    public Double getPayment_status_id() {
        return payment_status_id;
    }

    public void setPayment_status_id(Double payment_status_id) {
        this.payment_status_id = payment_status_id;
    }

    public int getOrder_sheet_id() {
        return order_sheet_id;
    }

    public void setOrder_sheet_id(int order_sheet_id) {
        this.order_sheet_id = order_sheet_id;
    }

    public Double getTax_percent() {
        return tax_percent;
    }

    public void setTax_percent(Double tax_percent) {
        this.tax_percent = tax_percent;
    }

    public Double getSurcharge_price() {
        return surcharge_price;
    }

    public void setSurcharge_price(Double surcharge_price) {
        this.surcharge_price = surcharge_price;
    }

    public Double getSurcharge_percent() {
        return surcharge_percent;
    }

    public void setSurcharge_percent(Double surcharge_percent) {
        this.surcharge_percent = surcharge_percent;
    }

    public Double getDiscount_contract_price() {
        return discount_contract_price;
    }

    public void setDiscount_contract_price(Double discount_contract_price) {
        this.discount_contract_price = discount_contract_price;
    }

    public Double getDiscount_contract_percent() {
        return discount_contract_percent;
    }

    public void setDiscount_contract_percent(Double discount_contract_percent) {
        this.discount_contract_percent = discount_contract_percent;
    }

    public Double getPayment_client_money() {
        return payment_client_money;
    }

    public void setPayment_client_money(Double payment_client_money) {
        this.payment_client_money = payment_client_money;
    }

    public int getOrder_status_id() {
        return order_status_id;
    }

    public void setOrder_status_id(int order_status_id) {
        this.order_status_id = order_status_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(int partner_id) {
        this.partner_id = partner_id;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public List<MContract> getOrder_detail_contracts() {
        return order_detail_contracts;
    }

    public void setOrder_detail_contracts(List<MContract> order_detail_contracts) {
        this.order_detail_contracts = order_detail_contracts;
    }

    public List<MProgramPromotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<MProgramPromotion> promotions) {
        this.promotions = promotions;
    }

    public String getDelivery_date() {
        return expected_completion_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.expected_completion_date = delivery_date;
    }

    public Double getPrepay() {
        return prepay;
    }

    public void setPrepay(Double prepay) {
        this.prepay = prepay;
    }

    public int getOrder_type_id() {
        return order_type_id;
    }

    public void setOrder_type_id(int order_type_id) {
        this.order_type_id = order_type_id;
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

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public boolean is_price_tax() {
        return is_price_tax;
    }

    public void setIs_price_tax(boolean is_price_tax) {
        this.is_price_tax = is_price_tax;
    }

    public MOrder() {
        this.note = "";
        this.address = "";
        this.phone = "";
        this.client_name = "";
        this.create_date = "";
        this.contracts = new ArrayList<>();
        this.order_detail_contracts = new ArrayList<>();
        this.promotions =  new ArrayList<>();
        this.debts =  new ArrayList<>();
        this.expected_completion_date = "";
        this.is_price_tax = false;
        this.prepay = 0.0;
    }
}
