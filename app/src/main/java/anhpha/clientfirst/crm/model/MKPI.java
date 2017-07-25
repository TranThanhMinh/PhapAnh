package anhpha.clientfirst.crm.model;

/**
 * Created by mc975 on 2/8/17.
 */
public class MKPI {
    private int kpi_user_id;
    private String kpi_user_name;
    private String kpi_user_type_name;
    private int kpi_user_type_id;
    private double kpi_target_value;
    private double kpi_value;

    public int getKpi_user_id() {
        return kpi_user_id;
    }

    public void setKpi_user_id(int kpi_user_id) {
        this.kpi_user_id = kpi_user_id;
    }

    public String getKpi_user_name() {
        return kpi_user_name;
    }

    public void setKpi_user_name(String kpi_user_name) {
        this.kpi_user_name = kpi_user_name;
    }

    public String getKpi_user_type_name() {
        return kpi_user_type_name;
    }

    public void setKpi_user_type_name(String kpi_user_type_name) {
        this.kpi_user_type_name = kpi_user_type_name;
    }

    public int getKpi_user_type_id() {
        return kpi_user_type_id;
    }

    public void setKpi_user_type_id(int kpi_user_type_id) {
        this.kpi_user_type_id = kpi_user_type_id;
    }

    public double getKpi_target_value() {
        return kpi_target_value;
    }

    public void setKpi_target_value(double kpi_target_value) {
        this.kpi_target_value = kpi_target_value;
    }

    public double getKpi_value() {
        return kpi_value;
    }

    public void setKpi_value(double kpi_value) {
        this.kpi_value = kpi_value;
    }
}
