package anhpha.clientfirst.crm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Window7 on 3/16/2017.
 */
public class MClientRequest {
    private List<MId> groups;
    private List<MId> labels;
    private List<MId> status;
    private List<MId> areas;
    private List<MId> types;
    private List<MId> user_ids;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MClientRequest() {
        this.groups = new ArrayList<>();
        this.labels =  new ArrayList<>();
        this.status =  new ArrayList<>();
        this.areas =  new ArrayList<>();
        this.types =  new ArrayList<>();
        this.value =  "";
        this.user_ids =  new ArrayList<>();
    }

    public List<MId> getGroups() {
        return groups;
    }

    public void setGroups(List<MId> groups) {
        this.groups = groups;
    }

    public List<MId> getLabels() {
        return labels;
    }

    public void setLabels(List<MId> labels) {
        this.labels = labels;
    }

    public List<MId> getStatus() {
        return status;
    }

    public void setStatus(List<MId> status) {
        this.status = status;
    }

    public List<MId> getAreas() {
        return areas;
    }

    public void setAreas(List<MId> areas) {
        this.areas = areas;
    }

    public List<MId> getTypes() {
        return types;
    }

    public void setTypes(List<MId> types) {
        this.types = types;
    }

    public List<MId> getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(List<MId> user_ids) {
        this.user_ids = user_ids;
    }
}
