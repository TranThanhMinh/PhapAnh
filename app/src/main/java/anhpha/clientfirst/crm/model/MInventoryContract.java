package anhpha.clientfirst.crm.model;

/**
 * Created by mc975 on 2/23/17.
 */
public class MInventoryContract {
    private int inventory_contract_client_id;
    private int inventory_contract_client_detail_id;
    private int contract_id ;
    private String contract_name ;
    private double inventory_value;
    private int contract_unit_id ;
    private String contract_unit_name;
    private String contract__group_unit_name ;
    private int contract_unit_group_id ;
    private int user_id ;
    private double price ;
    private double price_group ;
    private int contract_price_id ;
    private int status_id ;
    private int number_in_group ;
    private String note ;

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

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    public double getInventory_value() {
        return inventory_value;
    }

    public void setInventory_value(double inventory_value) {
        this.inventory_value = inventory_value;
    }

    public int getContract_unit_id() {
        return contract_unit_id;
    }

    public void setContract_unit_id(int contract_unit_id) {
        this.contract_unit_id = contract_unit_id;
    }

    public String getContract_unit_name() {
        return contract_unit_name;
    }

    public void setContract_unit_name(String contract_unit_name) {
        this.contract_unit_name = contract_unit_name;
    }

    public String getContract__group_unit_name() {
        return contract__group_unit_name;
    }

    public void setContract__group_unit_name(String contract__group_unit_name) {
        this.contract__group_unit_name = contract__group_unit_name;
    }

    public int getContract_unit_group_id() {
        return contract_unit_group_id;
    }

    public void setContract_unit_group_id(int contract_unit_group_id) {
        this.contract_unit_group_id = contract_unit_group_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice_group() {
        return price_group;
    }

    public void setPrice_group(double price_group) {
        this.price_group = price_group;
    }

    public int getContract_price_id() {
        return contract_price_id;
    }

    public void setContract_price_id(int contract_price_id) {
        this.contract_price_id = contract_price_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getNumber_in_group() {
        return number_in_group;
    }

    public void setNumber_in_group(int number_in_group) {
        this.number_in_group = number_in_group;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
