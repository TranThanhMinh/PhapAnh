package anhpha.clientfirst.crm.model;

import java.io.Serializable;

/**
 * Created by mc975 on 2/9/17.
 */
public class MLabel implements Serializable {
    private int  client_label_id ;
    private int  client_id ;
    private int  client_label_detail_id;
    private String  client_label_name ;
    private String  client_label_code ;
    private int  color_id ;
    private String  color_name ;
    private String  hex ;
    private boolean  is_has ;

    public int getClient_label_id() {
        return client_label_id;
    }

    public void setClient_label_id(int client_label_id) {
        this.client_label_id = client_label_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getClient_label_detail_id() {
        return client_label_detail_id;
    }

    public void setClient_label_detail_id(int client_label_detail_id) {
        this.client_label_detail_id = client_label_detail_id;
    }

    public String getClient_label_name() {
        return client_label_name;
    }

    public void setClient_label_name(String client_label_name) {
        this.client_label_name = client_label_name;
    }

    public String getClient_label_code() {
        return client_label_code;
    }

    public void setClient_label_code(String client_label_code) {
        this.client_label_code = client_label_code;
    }

    public int getColor_id() {
        return color_id;
    }

    public void setColor_id(int color_id) {
        this.color_id = color_id;
    }

    public String getColor_name() {
        return color_name;
    }

    public void setColor_name(String color_name) {
        this.color_name = color_name;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public boolean getIs_has() {
        return is_has;
    }

    public void setIs_has(boolean is_has) {
        this.is_has = is_has;
    }

    public MLabel() {
        this.client_label_id = 0;
        this.color_id = 0;
    }
}
