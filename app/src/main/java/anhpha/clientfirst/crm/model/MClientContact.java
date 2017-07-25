package anhpha.clientfirst.crm.model;

/**
 * Created by Window7 on 3/21/2017.
 */
public class MClientContact {
    public int client_id ;
    public String client_name ;
    public String phone;
    public String email;
    public String address;
    public String is_add ;
    public String is_has ;
    public int user_manager_id;
    public boolean is_select;

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIs_add() {
        return is_add;
    }

    public void setIs_add(String is_add) {
        this.is_add = is_add;
    }

    public String getIs_has() {
        return is_has;
    }

    public void setIs_has(String is_has) {
        this.is_has = is_has;
    }

    public int getUser_manager_id() {
        return user_manager_id;
    }

    public void setUser_manager_id(int user_manager_id) {
        this.user_manager_id = user_manager_id;
    }
}
