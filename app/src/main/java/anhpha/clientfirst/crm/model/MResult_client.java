package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MinhTran on 7/11/2017.
 */

public class MResult_client {

    public List<MResult_client> getClient() {
        return client;
    }
    public void setClient(List<MResult_client> client) {
        this.client = client;
    }
    @SerializedName("Result")
    @Expose
    private List<MResult_client> client = null;
    @SerializedName("Errors")
    @Expose
    private Errors errors;
    @SerializedName("Info")
    @Expose
    private Object info;
    @SerializedName("HasErrors")
    @Expose
    private Boolean hasErrors;



    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public Boolean getHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(Boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

}
