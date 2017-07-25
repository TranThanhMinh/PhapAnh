package anhpha.clientfirst.crm.model;

/**
 * Created by mc975 on 2/3/17.
 */
public class MAPIResponse<T> {
    private T Result;
    private MErrors Errors;
    private boolean HasErrors;

    public T getResult() {
        return Result;
    }

    public void setResult(T result) {
        Result = result;
    }

    public MErrors getErrors() {
        return Errors;
    }

    public void setErrors(MErrors errors) {
        Errors = errors;
    }

    public boolean isHasErrors() {
        return HasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        HasErrors = hasErrors;
    }
}
