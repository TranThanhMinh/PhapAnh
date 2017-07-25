package anhpha.clientfirst.crm.model;

import java.io.Serializable;

/**
 * Created by mc975 on 2/3/17.
 */
public class MUser implements Serializable {
    private int user_id;
    private  String user_name;
    private  String token;
    private  String email;
    private  String partner_name;
    private  String root_name;
    private  int partner_id;
    private  int user_parent_id;
    private  int leve;
    private  boolean is_select;
    private  boolean is_expanded;

    public int getParent_id() {
        return user_parent_id;
    }

    public void setParent_id(int parent_id) {
        this.user_parent_id = parent_id;
    }

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    public boolean is_expanded() {
        return is_expanded;
    }

    public void setIs_expanded(boolean is_expanded) {
        this.is_expanded = is_expanded;
    }

    public String getRoot_name() {
        return root_name;
    }

    public void setRoot_name(String root_name) {
        this.root_name = root_name;
    }

    public int getLeve() {
        return leve;
    }

    public void setLeve(int leve) {
        this.leve = leve;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public int getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(int partner_id) {
        this.partner_id = partner_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public MUser(int user_id) {
        this.user_id = user_id;
    }
    public MUser() {
    }
}
