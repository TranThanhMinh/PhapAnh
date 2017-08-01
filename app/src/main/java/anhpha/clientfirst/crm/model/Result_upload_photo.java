package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MinhTran on 7/11/2017.
 */

public class Result_upload_photo {

    @SerializedName("Result")
    @Expose
    private MResult_client result;
    @SerializedName("Errors")
    @Expose
    private Errors errors;
    @SerializedName("Info")
    @Expose
    private Object info;
    @SerializedName("HasErrors")
    @Expose
    private Boolean hasErrors;

    public MResult_client getResult() {
        return result;
    }

    public void setResult(MResult_client result) {
        this.result = result;
    }

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
