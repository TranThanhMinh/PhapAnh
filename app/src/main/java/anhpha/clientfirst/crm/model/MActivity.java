package anhpha.clientfirst.crm.model;

import java.util.List;

/**
 * Created by mc975 on 2/6/17.
 */
public class MActivity {

    private String from_date;
    private String to_date;
    private Double sales_amount;
    private int order_count;
    private int add_client_count;
    private int meeting_count;
    private int work_count;
    private int call_count;
    private int email_count;
    private int event_count;
    private List<MActivityItem> activies;


    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public Double getSales_amount() {
        return sales_amount;
    }

    public void setSales_amount(Double sales_amount) {
        this.sales_amount = sales_amount;
    }

    public int getOrder_count() {
        return order_count;
    }

    public void setOrder_count(int order_count) {
        this.order_count = order_count;
    }

    public int getAdd_client_count() {
        return add_client_count;
    }

    public void setAdd_client_count(int add_client_count) {
        this.add_client_count = add_client_count;
    }

    public int getCheckin_count() {
        return meeting_count;
    }

    public void setCheckin_count(int checkin_count) {
        this.meeting_count = checkin_count;
    }

    public int getWork_count() {
        return work_count;
    }

    public void setWork_count(int work_count) {
        this.work_count = work_count;
    }

    public int getCall_count() {
        return call_count;
    }

    public void setCall_count(int call_count) {
        this.call_count = call_count;
    }

    public int getEmail_count() {
        return email_count;
    }

    public void setEmail_count(int email_count) {
        this.email_count = email_count;
    }

    public int getEvent_count() {
        return event_count;
    }

    public void setEvent_count(int event_count) {
        this.event_count = event_count;
    }

    public List<MActivityItem> getActivies() {
        return activies;
    }

    public void setActivies(List<MActivityItem> activies) {
        this.activies = activies;
    }
}
