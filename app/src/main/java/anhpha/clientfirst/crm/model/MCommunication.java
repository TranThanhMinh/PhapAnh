package anhpha.clientfirst.crm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mc975 on 2/7/17.
 */
public class MCommunication implements Serializable {
    private int  communication_id ;
    private String    communication_name ;
    private String    communication_content ;
    private String    from_date ;
    private String    to_date ;
    private int    communication_type_id ;
    private List<MPhoto> photos;

    public String getBegin_date() {
        return from_date;
    }

    public void setBegin_date(String begin_date) {
        this.from_date = begin_date;
    }

    public String getEnd_date() {
        return to_date;
    }

    public void setEnd_date(String end_date) {
        this.to_date = end_date;
    }

    public MCommunication() {
        this.photos = new ArrayList<>();
    }

    public int getCommunication_id() {
        return communication_id;
    }

    public void setCommunication_id(int communication_id) {
        this.communication_id = communication_id;
    }

    public String getCommunication_name() {
        return communication_name;
    }

    public void setCommunication_name(String communication_name) {
        this.communication_name = communication_name;
    }

    public String getCommunication_content() {
        return communication_content;
    }

    public void setCommunication_content(String communication_content) {
        this.communication_content = communication_content;
    }

    public int getCommunication_type_id() {
        return communication_type_id;
    }

    public void setCommunication_type_id(int communication_type_id) {
        this.communication_type_id = communication_type_id;
    }

    public List<MPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MPhoto> photos) {
        this.photos = photos;
    }
}
