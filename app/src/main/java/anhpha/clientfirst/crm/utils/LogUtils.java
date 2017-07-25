package anhpha.clientfirst.crm.utils;

import com.google.gson.Gson;

import java.io.IOException;

import anhpha.clientfirst.crm.BuildConfig;
import okhttp3.Request;
import okio.Buffer;
import retrofit2.Call;

/**
 * Created by Nguyen on 6/16/2015.
 */
public class LogUtils {

    private static final boolean isPrintLog = BuildConfig.DEBUG;

    public static void o(String className, String methodName, Object src) {
        if (isPrintLog) {
            android.util.Log.i("Activity: " + className, methodName + ": " + new Gson().toJson(src));
        }
    }

    public static void i(String className, String methodName, String message) {
        if (isPrintLog) {
            android.util.Log.i("Activity: " + className, methodName + ": " + message);
        }
    }

    public static void e(String className, String methodName, Exception ex) {
        if (isPrintLog) {
            android.util.Log.e("Activity: " + className, methodName, ex);
        }
    }

    public static void w(String className, String methodName, String message) {
        if (isPrintLog) {
            android.util.Log.w("Activity: " + className, methodName + ": " + message);
        }
    }

    public static void d(String className, String methodName, String message) {
        if (isPrintLog) {
            int maxLogSize = 1000;
            for(int i = 0; i <= message.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i+1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                android.util.Log.d("Activity " + className, methodName + ": " + message.substring(start, end));
            }

        }
    }
    public static void api(String className,Call call,Object src) {
        if (isPrintLog) {
            String message = call.request().url() +"\n" + call.request().headers().toString() +  bodyToString(call.request()) +"\n"  + new Gson().toJson(src);

            int maxLogSize = 1000;
            for(int i = 0; i <= message.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i+1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                android.util.Log.w("API " + className, message.substring(start, end));
            }
        }
    }
    private static String bodyToString(final Request request){

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            if(copy.body() == null)
                return "NO BODY";
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
