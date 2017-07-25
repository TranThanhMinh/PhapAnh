package anhpha.clientfirst.crm.model;

import java.io.Serializable;

/**
 * Created by mc975 on 2/6/17.
 */
public class MContract implements Serializable {

    private int inventory_contract_client_id ;
    private int inventory_contract_client_detail_id ;
    private Double inventory_value ;
    private int contract_id ;
    private String barcode  ;
    private int contract_group_id;
    private int contract_price_type_id ;
    private int contract_price_id ;
    private String contract_name ;
    private int number_in_group;
    private Double price_number ;
    private Double price_group_number ;
    private Double price ;
    private Double price_group ;
    private Double number ;
    private Double number_group ;
    private boolean is_select;
    private boolean is_delete ;
    private String note;
    private boolean is_price_tax ;
    private int tax ;
    private String contract_unit_group_name;
    private String contract_unit_name ;
    private String price_name ;
    private int contract_unit_group_id ;
    private int contract_unit_id ;
    private int status_id;
    private Double amount_price;
    private MColor mColor;

    public MContract() {
        this.inventory_contract_client_id = 0;
        this.inventory_contract_client_detail_id = 0;
        this.inventory_value = 0.0;
        this.contract_id = 0;
        this.barcode = "";
        this.price_name = "Giá trị";
        this.contract_group_id = 0;
        this.contract_price_type_id = 0;
        this.contract_price_id = 0;
        this.contract_name = "";
        this.number_in_group = 0;
        this.price_number = 0.0;
        this.price_group_number = 0.0;
        this.price = 0.0;
        this.price_group =  0.0;
        this.number =  0.0;
        this.number_group =  0.0;
        this.is_select = false;
        this.is_delete = false;
        this.note = "";
        this.is_price_tax = false;
        this.tax =  0;
        this.contract_unit_group_name = "";
        this.contract_unit_name = "";
        this.contract_unit_group_id = 0;
        this.contract_unit_id = 0;
        this.status_id = 0;
        this.amount_price =  0.0;
    }

    public String getPrice_name() {
        return price_name;
    }

    public void setPrice_name(String price_name) {
        this.price_name = price_name;
    }

    public Double getPrice_number() {
        return price_number;
    }

    public void setPrice_number(Double price_number) {
        this.price_number = price_number;
    }

    public Double getPrice_group_number() {
        return price_group_number;
    }

    public void setPrice_group_number(Double price_group_number) {
        this.price_group_number = price_group_number;
    }

    public Double getNumber_group() {
        return number_group;
    }

    public void setNumber_group(Double number_group) {
        this.number_group = number_group;
    }

    public MColor getmColor() {
        return mColor;
    }

    public void setmColor(MColor mColor) {
        this.mColor = mColor;
    }

    public int getInventory_contract_client_id() {
        return inventory_contract_client_id;
    }

    public void setInventory_contract_client_id(int inventory_contract_client_id) {
        this.inventory_contract_client_id = inventory_contract_client_id;
    }

    public int getInventory_contract_client_detail_id() {
        return inventory_contract_client_detail_id;
    }

    public void setInventory_contract_client_detail_id(int inventory_contract_client_detail_id) {
        this.inventory_contract_client_detail_id = inventory_contract_client_detail_id;
    }

    public Double getInventory_value() {
        return inventory_value;
    }

    public void setInventory_value(Double inventory_value) {
        this.inventory_value = inventory_value;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getContract_group_id() {
        return contract_group_id;
    }

    public void setContract_group_id(int contract_group_id) {
        this.contract_group_id = contract_group_id;
    }

    public int getContract_price_type_id() {
        return contract_price_type_id;
    }

    public void setContract_price_type_id(int contract_price_type_id) {
        this.contract_price_type_id = contract_price_type_id;
    }

    public int getContract_price_id() {
        return contract_price_id;
    }

    public void setContract_price_id(int contract_price_id) {
        this.contract_price_id = contract_price_id;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    public int getNumber_in_group() {
        return number_in_group;
    }

    public void setNumber_in_group(int number_in_group) {
        this.number_in_group = number_in_group;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice_group() {
        return price_group;
    }

    public void setPrice_group(Double price_group) {
        this.price_group = price_group;
    }

    public Double getNumber() {
        return number;
    }

    public void setNumber(Double number) {
        this.number = number;
    }

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    public boolean is_delete() {
        return is_delete;
    }

    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean is_price_tax() {
        return is_price_tax;
    }

    public void setIs_price_tax(boolean is_price_tax) {
        this.is_price_tax = is_price_tax;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public String getContract_unit_group_name() {
        return contract_unit_group_name;
    }

    public void setContract_unit_group_name(String contract_unit_group_name) {
        this.contract_unit_group_name = contract_unit_group_name;
    }

    public String getContract_unit_name() {
        return contract_unit_name;
    }

    public void setContract_unit_name(String contract_unit_name) {
        this.contract_unit_name = contract_unit_name;
    }

    public int getContract_unit_group_id() {
        return contract_unit_group_id;
    }

    public void setContract_unit_group_id(int contract_unit_group_id) {
        this.contract_unit_group_id = contract_unit_group_id;
    }

    public int getContract_unit_id() {
        return contract_unit_id;
    }

    public void setContract_unit_id(int contract_unit_id) {
        this.contract_unit_id = contract_unit_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public Double getAmount_price() {
        return amount_price;
    }

    public void setAmount_price(Double amount_price) {
        this.amount_price = amount_price;
    }
}
