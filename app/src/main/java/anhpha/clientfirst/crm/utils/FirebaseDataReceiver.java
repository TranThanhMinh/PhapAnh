package anhpha.clientfirst.crm.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.util.Iterator;
import java.util.Set;

import anhpha.clientfirst.crm.activity.LoginActivity;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MRemoteMessage;
import anhpha.clientfirst.crm.model.MUser;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Window7 on 4/11/2017.
 */
public class FirebaseDataReceiver extends WakefulBroadcastReceiver implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    Location mLastLocation = createNewLocation(0, 0);
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    boolean connect = false;
    String TAG = "FirebaseDataReceiver";
    String ts;
    private NotificationUtils notificationUtils;
    private Context context;

    public void onReceive(Context context, Intent intent) {
        this.context = context;

        Bundle bundle = intent.getExtras();
        MRemoteMessage mRemoteMessage = new MRemoteMessage();

        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String key = it.next();
               // Log.e(TAG, "[" + key + "=" + bundle.get(key) + "]");
                switch (key){
                    case "text" :
                        mRemoteMessage.setMessage(bundle.get(key).toString());
                    case "title" :
                        mRemoteMessage.setTitle(bundle.get(key).toString());
                    case "image_url" :
                        mRemoteMessage.setImageUrl(bundle.get(key).toString());
                    case "is_background" :
                        mRemoteMessage.setBackground(Boolean.parseBoolean(bundle.get(key).toString()));
                    case "timestamp" :
                        mRemoteMessage.setTimestamp(bundle.get(key).toString());
                        break;
                    default:
                        break;
                }
            }
            LogUtils.d(TAG, "",new Gson().toJson(mRemoteMessage));

            onMessageReceived(mRemoteMessage, intent);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        LogUtils.d(TAG, "" + location.getLongitude(), "" + location.getLatitude());
        Preferences preferences = new Preferences(context);
        GetRetrofit().create(ServiceAPI.class)
                .setTrackingDevice(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , location.getLatitude(),
                        location.getLongitude())
                .enqueue(new Callback<MAPIResponse<MUser>>() {
                    @Override
                    public void onResponse(Call<MAPIResponse<MUser>> call, Response<MAPIResponse<MUser>> response) {

                        LogUtils.api(TAG,call,response.body());
                        if (connect)
                            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, FirebaseDataReceiver.this);
                    }

                    @Override
                    public void onFailure(Call<MAPIResponse<MUser>> call, Throwable t) {
                        LogUtils.d(TAG, "getUserLogin ", t.toString());
                        if (connect)
                            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, FirebaseDataReceiver.this);
                    }
                });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private Location createNewLocation(double longitude, double latitude) {
        Location location = new Location("dummyprovider");
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        return location;
    }

    @Override
    public void onConnected(Bundle bundle) {
        connect = true;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(100000);
        mLocationRequest.setFastestInterval(100000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void onMessageReceived(MRemoteMessage remoteMessage, Intent intent) {


            if (remoteMessage.isBackground()) {
                buildGoogleApiClient();
                connect = false;

            } else {
                Intent resultIntent = new Intent(context, LoginActivity.class);
                Log.d(TAG, "I'm in!!!");
                // check for image attachment
                if (TextUtils.isEmpty(remoteMessage.getImageUrl()) && !remoteMessage.getImageUrl().equals("_")) {
                    showNotificationMessage(context, remoteMessage.getTitle(), remoteMessage.getMessage(), remoteMessage.getTimestamp(), resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(context, remoteMessage.getTitle(), remoteMessage.getMessage(), remoteMessage.getTimestamp(), resultIntent, remoteMessage.getImageUrl());
                }

        }
    }

    public Retrofit GetRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://api.fieldwork.vn")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}