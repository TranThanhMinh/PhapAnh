package anhpha.clientfirst.crm.model;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.activity.MOrderStatus;

/**
 * Created by Window7 on 4/25/2017.
 */
public class MUserDefault {

    private boolean permission_tracking_manager;
    private boolean permission_cancel_order;
    private boolean permission_edit_client;
    private List<MOrderStatus> order_status;

    public MUserDefault() {
        this.order_status = new ArrayList<>();
    }

    public List<MOrderStatus> getOrder_status() {
        return order_status;
    }

    public void setOrder_status(List<MOrderStatus> order_status) {
        this.order_status = order_status;
    }

    public boolean isPermission_tracking_manager() {
        return permission_tracking_manager;
    }

    public void setPermission_tracking_manager(boolean permission_tracking_manager) {
        this.permission_tracking_manager = permission_tracking_manager;
    }

    public boolean isPermission_cancel_order() {
        return permission_cancel_order;
    }

    public void setPermission_cancel_order(boolean permission_cancel_order) {
        this.permission_cancel_order = permission_cancel_order;
    }

    public boolean isPermission_edit_client() {
        return permission_edit_client;
    }

    public void setPermission_edit_client(boolean permission_edit_client) {
        this.permission_edit_client = permission_edit_client;
    }
}
