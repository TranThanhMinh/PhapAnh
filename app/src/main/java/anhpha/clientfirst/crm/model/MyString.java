package anhpha.clientfirst.crm.model;

/**
 * Created by mc975 on 2/27/17.
 */
public class MyString {

    String delegate; // The actual string you delegate to from your class

    public MyString(String delegate) {
        this.delegate = delegate; // Assign the string that backs your class
    }

    int length() {
        return delegate.length(); // Delegate the method call to the string
    }
    String trim(){
        if (delegate == null){
            return "";
        }
        return delegate.trim();
    }
    public String toString() {
        return delegate;
    }
    boolean isEmpty(){
        if(delegate == null){
            return  true;
        }else if(delegate.isEmpty()){
            return true;
        }
        return true;
    }
    // other methods that delegate to the string field
}
