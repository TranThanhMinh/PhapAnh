package anhpha.clientfirst.crm.model;

/**
 * Created by Window7 on 3/7/2017.
 */
public class MCalendar {
    private int calendar_id ;
    private int event_id ;
    private String calendar_title ;
    private String calendar_content ;
    private String calendar_code ;
    private String calendar_date ;
    private String location ;

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCalendar_id() {
        return calendar_id;
    }

    public void setCalendar_id(int calendar_id) {
        this.calendar_id = calendar_id;
    }

    public String getCalendar_title() {
        return calendar_title;
    }

    public void setCalendar_title(String calendar_title) {
        this.calendar_title = calendar_title;
    }

    public String getCalendar_content() {
        return calendar_content;
    }

    public void setCalendar_content(String calendar_content) {
        this.calendar_content = calendar_content;
    }

    public String getCalendar_code() {
        return calendar_code;
    }

    public void setCalendar_code(String calendar_code) {
        this.calendar_code = calendar_code;
    }

    public String getCalendar_date() {
        return calendar_date;
    }

    public void setCalendar_date(String calendar_date) {
        this.calendar_date = calendar_date;
    }
}
