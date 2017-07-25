package anhpha.clientfirst.crm.model;

/**
 * Created by mc975 on 2/3/17.
 */
public class MErrors {
    int ID;
    String Code;
    String Message;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
