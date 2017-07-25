package anhpha.clientfirst.crm.model;

/**
 * Created by Window7 on 5/10/2017.
 */
public class MDebt {
    private int order_contract_debt_transaction_id ;
    private String date_plan_debt;
    private String date_debt ;
    private double value_plan_debt ;
    private double value_debt ;
    private int debt_status_id;
    private int user_id ;
    private String note;
    private int order_contract_id ;

    public int getOrder_contract_debt_transaction_id() {
        return order_contract_debt_transaction_id;
    }

    public void setOrder_contract_debt_transaction_id(int order_contract_debt_transaction_id) {
        this.order_contract_debt_transaction_id = order_contract_debt_transaction_id;
    }

    public String getDate_plan_debt() {
        return date_plan_debt;
    }

    public void setDate_plan_debt(String date_plan_debt) {
        this.date_plan_debt = date_plan_debt;
    }

    public String getDate_debt() {
        return date_debt;
    }

    public void setDate_debt(String date_debt) {
        this.date_debt = date_debt;
    }

    public double getValue_plan_debt() {
        return value_plan_debt;
    }

    public void setValue_plan_debt(double value_plan_debt) {
        this.value_plan_debt = value_plan_debt;
    }

    public double getValue_debt() {
        return value_debt;
    }

    public void setValue_debt(double value_debt) {
        this.value_debt = value_debt;
    }

    public double getDebt_status_id() {
        return debt_status_id;
    }

    public void setDebt_status_id(int debt_status_id) {
        this.debt_status_id = debt_status_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getOrder_contract_id() {
        return order_contract_id;
    }

    public void setOrder_contract_id(int order_contract_id) {
        this.order_contract_id = order_contract_id;
    }
}
