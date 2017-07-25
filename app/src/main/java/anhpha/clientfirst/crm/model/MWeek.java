package anhpha.clientfirst.crm.model;

/**
 * Created by Window7 on 3/30/2017.
 */
public class MWeek {
    public int week_num ;
    public int week_day_type_id ;
    public boolean is_date ;
    public String week_day_type_code ;
    public String date ;


    public int getWeek_num() {
        return week_num;
    }

    public void setWeek_num(int week_num) {
        this.week_num = week_num;
    }

    public int getWeek_day_type_id() {
        return week_day_type_id;
    }

    public void setWeek_day_type_id(int week_day_type_id) {
        this.week_day_type_id = week_day_type_id;
    }

    public boolean is_date() {
        return is_date;
    }

    public void setIs_date(boolean is_date) {
        this.is_date = is_date;
    }

    public String getWeek_day_type_code() {
        return week_day_type_code;
    }

    public void setWeek_day_type_code(String week_day_type_code) {
        this.week_day_type_code = week_day_type_code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
