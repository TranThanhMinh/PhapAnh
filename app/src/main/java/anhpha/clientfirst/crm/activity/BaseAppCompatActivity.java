package anhpha.clientfirst.crm.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.Locale;

import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.utils.Config;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.NotificationUtils;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mc975 on 2/3/17.
 */
public class BaseAppCompatActivity extends AppCompatActivity implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private static final int REQUEST_READ_EXTERNAL_STORAGE =  1;
    private static final int REQUEST_FINE_LOCATION =  2;
    private static final int REQUEST_CALENDAR =  3;
    private static final int REQUEST_CALL =  4;
    private static final int REQUEST_READ_CONTACT =  5;
    public Context mContext;
    public String TAG ;
    public DynamicBox box;
    Location mLastLocation = createNewLocation(0,0);
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    boolean connect = false;
    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        LogUtils.d(TAG,""+ location.getLongitude(),""+ location.getLatitude());
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        connect = false;
    }

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configLanguage();
        super.onCreate(savedInstanceState);
        hideSoftKeyboard(this);
        mContext = this;
        TAG = mContext.getClass().getSimpleName();
       // wifiIpAddress(mContext);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    Log.i(TAG, "FCM Registration Token: " +  FirebaseInstanceId.getInstance().getToken());

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                }
            }
        };

    }
    protected String wifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endianif needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString = "";
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
            final WifiManager manager = (WifiManager) super.getSystemService(WIFI_SERVICE);
            final DhcpInfo dhcp = manager.getDhcpInfo();
            ipAddressString += android.text.format.Formatter.formatIpAddress(dhcp.gateway);

        } catch (UnknownHostException ex) {
            ipAddressString = null;
        }
        LogUtils.d(TAG,"ipAddress: ",ipAddressString);
        return ipAddressString;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideSoftKeyboard(this);
        finish();
    }
    private Location createNewLocation(double longitude, double latitude) {
        Location location = new Location("dummyprovider");
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        return location;
    }
    public Retrofit GetRetrofit(){
        return new Retrofit.Builder()
                .baseUrl("http://api.msaleman.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity
                    .getCurrentFocus().getWindowToken(), 0);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        configLanguage();
        connect = true;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(100000);
        mLocationRequest.setFastestInterval(100000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        try {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onConnectionSuspended(int i) {
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(connect)
             LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
    @Override
    protected void onResume() {
        configLanguage();
        super.onResume();
        buildGoogleApiClient();
        connect = false;

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }



    @Override
    public void recreate() {
        super.recreate();

        configLanguage();
    }
    @Override
    protected void onStart() {
        super.onStart();
        configLanguage();
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
    private void configLanguage() {
        Configuration config = new Configuration();
        int sw = new Preferences(getApplicationContext()).getIntValue(Constants.LANGUAGE,0);
        switch (sw) {
            case Constants.LANGUAGE_VI:
                config.locale = Locale.ROOT;
                break;
            case Constants.LANGUAGE_EN:
                config.locale = Locale.ENGLISH;
                break;
            default:
                config.locale = Locale.ROOT;
                break;
        }
        getResources().updateConfiguration(config, null);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // your code here, you can use newConfig.locale if you need to check the language
        // or just re-set all the labels to desired string resource
        configLanguage();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void checkPermission(String permission) {

        switch (permission){
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

//                    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                        LogUtils.d(TAG, "NO PERMISSION SHOW MESSAGE","");
//                    } else {
                        // No explanation needed, we can request the permission. The result will be captured in onRequestPermissionsResult
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                        LogUtils.d(TAG, "NO PERMISSION REQUEST PERMISSION","");
                    //}

                }
                break;

            case Manifest.permission.ACCESS_FINE_LOCATION:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

//                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
//                        LogUtils.d(TAG, "NO PERMISSION SHOW MESSAGE","");
//                    } else {
                        // No explanation needed, we can request the permission. The result will be captured in onRequestPermissionsResult
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
                        LogUtils.d(TAG, "NO PERMISSION REQUEST PERMISSION","");
                    //}

                }
                break;
            case Manifest.permission.READ_CALENDAR:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CALENDAR)
                        != PackageManager.PERMISSION_GRANTED) {

//                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR)) {
//                        LogUtils.d(TAG, "NO PERMISSION SHOW MESSAGE","");
//                    } else {
                        // No explanation needed, we can request the permission. The result will be captured in onRequestPermissionsResult
                        requestPermissions(new String[]{Manifest.permission.READ_CALENDAR}, REQUEST_CALENDAR);
                        LogUtils.d(TAG, "NO PERMISSION REQUEST PERMISSION","");
//                    }

                }
                break;
            case Manifest.permission.WRITE_CALENDAR:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_CALENDAR)
                        != PackageManager.PERMISSION_GRANTED) {

//                    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CALENDAR)) {
//                        LogUtils.d(TAG, "NO PERMISSION SHOW MESSAGE","");
//                    } else {
                        // No explanation needed, we can request the permission. The result will be captured in onRequestPermissionsResult
                        requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR}, REQUEST_CALENDAR);
                        LogUtils.d(TAG, "NO PERMISSION REQUEST PERMISSION","");
//                    }

                }
                break;
            case Manifest.permission.READ_CONTACTS:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {

//                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
//                        LogUtils.d(TAG, "NO PERMISSION SHOW MESSAGE","");
//                    } else {
                        // No explanation needed, we can request the permission. The result will be captured in onRequestPermissionsResult
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACT);
                        LogUtils.d(TAG, "NO PERMISSION REQUEST PERMISSION","");
//                    }

                }
                break;
            case Manifest.permission.CALL_PHONE:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

//                    if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
//                        LogUtils.d(TAG, "NO PERMISSION SHOW MESSAGE","");
//                    } else {
                        // No explanation needed, we can request the permission. The result will be captured in onRequestPermissionsResult
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        LogUtils.d(TAG, "NO PERMISSION REQUEST PERMISSION","");
//                    }

                }
                break;
                default:
                    break;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LogUtils.d(TAG, "REQUEST PERMISSION GRANTED :)","");
                } else {
//                    shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE);
//                    showMessageOKCancel("Ứng dụng muốn truy thẻ nhớ của bạn", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            LogUtils.d(TAG, "OK PRESSED","");
//                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
//                        }
//                    });
//                    LogUtils.d(TAG, "REQUEST PERMISSION DENIED :( ","");
                }
                return;
            }
            case REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LogUtils.d(TAG, "REQUEST PERMISSION GRANTED :)","");

                } else {
//                    shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
//                    showMessageOKCancel("Ứng dụng muốn truy cập GPS của bạn", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            LogUtils.d(TAG, "OK PRESSED","");
//                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
//                        }
//                    });
//                    LogUtils.d(TAG, "REQUEST PERMISSION DENIED :( ","");
                }
                return;
            }
            case REQUEST_CALENDAR: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LogUtils.d(TAG, "REQUEST PERMISSION GRANTED :)","");

                } else {
//                    shouldShowRequestPermissionRationale(Manifest.permission_group.CALENDAR);
//                    showMessageOKCancel("Ứng dụng muốn truy cập lịch của bạn", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            LogUtils.d(TAG, "OK PRESSED","");
//                            requestPermissions(new String[]{Manifest.permission_group.CALENDAR}, REQUEST_CALENDAR);
//                        }
//                    });
//                    LogUtils.d(TAG, "REQUEST PERMISSION DENIED :( ","");
                }
                return;
            }
            case REQUEST_CALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LogUtils.d(TAG, "REQUEST PERMISSION GRANTED :)","");

                } else {
//                    shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE);
//                    showMessageOKCancel("Ứng dụng muốn truy cập điện thoại của bạn", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            LogUtils.d(TAG, "OK PRESSED","");
//                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
//                        }
//                    });
//                    LogUtils.d(TAG, "REQUEST PERMISSION DENIED :( ","");
                }
                return;
            }
            case REQUEST_READ_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LogUtils.d(TAG, "REQUEST PERMISSION GRANTED :)","");

                } else {
//                    shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS);
//                    showMessageOKCancel("Ứng dụng muốn truy cập danh bạ của bạn", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            LogUtils.d(TAG, "OK PRESSED","");
//                            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACT);
//                        }
//                    });
//                    LogUtils.d(TAG, "REQUEST PERMISSION DENIED :( ","");
                }
                return;
            }
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(mContext)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}


