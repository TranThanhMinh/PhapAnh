package anhpha.clientfirst.crm.adapter;

import java.io.Serializable;

/**
 * Created by mc975 on 2/20/17.
 */
public class MStatus implements Serializable {
    private int id;
    private String name;
    private boolean is_select;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    public MStatus(int id, String name, boolean is_select) {
        this.id = id;
        this.name = name;
        this.is_select = is_select;
    }
}
