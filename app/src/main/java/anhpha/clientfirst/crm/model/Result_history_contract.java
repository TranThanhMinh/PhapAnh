package anhpha.clientfirst.crm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MinhTran on 7/11/2017.
 */

public class Result_history_contract {
    public List<History_contract> getHistory_contracts() {
        return history_contracts;
    }

    public void setHistory_contracts(List<History_contract> history_contracts) {
        this.history_contracts = history_contracts;
    }

    @SerializedName("Result")
    @Expose
    private List<History_contract> history_contracts = null;
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
