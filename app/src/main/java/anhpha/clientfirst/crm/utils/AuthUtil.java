package anhpha.clientfirst.crm.utils;

import android.util.Base64;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nguyen on 5/28/2015.
 */
public class AuthUtil {

    public static String getBasicAuth(String username, String password) {
        Map<String, String> requestParams = new HashMap<>();
        String creds = String.format("%s:%s", username, password);
        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT).replace("\n","");
        //requestParams.put("Authorization", auth);
        return auth;
    }
}
