package anhpha.clientfirst.crm.model;

/**
 * Created by mc975 on 2/8/17.
 */
public class MGroupContract {

    private int contract_group_id;
    private String contract_group_name;

    public String getContract_group_name() {
        return contract_group_name;
    }

    public void setContract_group_name(String contract_group_name) {
        this.contract_group_name = contract_group_name;
    }

    public int getContract_group_id() {
        return contract_group_id;
    }

    public void setContract_group_id(int contract_group_id) {
        this.contract_group_id = contract_group_id;
    }
}
