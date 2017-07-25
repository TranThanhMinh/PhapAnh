package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.TrackingAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.DividerItemDecoration;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MRequestBody;
import anhpha.clientfirst.crm.model.MTracking;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.HaversineAlgorithm;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import anhpha.clientfirst.crm.utils.DynamicBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackingByUserActivity extends BaseAppCompatActivity implements DatePickerDialog.OnDateSetListener, Callback<MAPIResponse<List<MTracking>>>, View.OnClickListener {
    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.ibMap)
    ImageButton ibMap;
    @Bind(R.id.ibList)
    ImageButton ibList;
    MapView mapView;
    GoogleMap map;
    Preferences preferences;
    List<MTracking> mTrackings;
    List<MId> mIds = new ArrayList<>();
    int user_id = 0;

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day;
    AlertDialog.Builder builder;
    String from_date;
    String to_Date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_tracking);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_tracking);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        ibMap.setOnClickListener(this);
        ibList.setOnClickListener(this);

        user_id = getIntent().getIntExtra("user_id", 0);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        MRequestBody mRequestBody = new MRequestBody();
        mRequestBody.setFrom_date(Utils.getFromDate());
        mRequestBody.setTo_date(Utils.getToDate());
        GetRetrofit().create(ServiceAPI.class)
                .getTrackingUserByUser(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , user_id
                        , mRequestBody
                )
                .enqueue(this);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        box.showLoadingLayout();

        LogUtils.d(TAG, "getUserActivities ", "start");


        mapView = (MapView) findViewById(R.id.mapview);

        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        if(map != null) {
            map.setBuildingsEnabled(true);
            map.setIndoorEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.setMyLocationEnabled(true);
            // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
            MapsInitializer.initialize(this);
            // Updates the location and zoom of the MapView
        }
        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_calendar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.calendar:

                datePickerDialog = DatePickerDialog.newInstance(
                        TrackingByUserActivity.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorApp));
                datePickerDialog.setCancelText(getString(R.string.no));
                datePickerDialog.setOkText(getString(R.string.yes));
                datePickerDialog.setTitle(getString(R.string.choose_date));
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<List<MTracking>>> call, Response<MAPIResponse<List<MTracking>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        mTrackings = response.body().getResult();
        LoadData(mTrackings);

    }

    public void LoadData(List<MTracking> mTrackings) {

        TrackingAdapter activityAdapter = new TrackingAdapter(mContext, mTrackings);
        rvActivities.setAdapter(activityAdapter);
        activityAdapter.notifyDataSetChanged();
        if(map!=null) {
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_dms_55);
            BitmapDescriptor icon2 = BitmapDescriptorFactory.fromResource(R.mipmap.ic_dms_52);
            int start = 0;
            map.clear();
            for (MTracking mTracking : mTrackings) {
                if (start == 0) {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(mTracking.getLatitude_partner(), mTracking.getLongitude_partner()), 12);
                    map.animateCamera(cameraUpdate);
                    LatLng center = map.getCameraPosition().target;
                    Marker marker = map.addMarker(new MarkerOptions()
                            .position(new LatLng(mTracking.getLatitude_partner(), mTracking.getLongitude_partner()))
                            .title(getString(R.string.company)));
                    start = 1;
                    marker.setIcon(icon2);
                }
                String distance = Utils.convertDistance(HaversineAlgorithm.HaversineInM(
                        mTracking.getLatitude_partner()
                        , mTracking.getLongitude_partner()
                        , mTracking.getLatitude()
                        , mTracking.getLongitude()));

                if (!distance.equals("-")) {

                    Marker marker2 = map.addMarker(new MarkerOptions()
                            .position(new LatLng(mTracking.getLatitude(), mTracking.getLongitude()))
                            .title(mTracking.getUser_name())
                            .snippet(Utils.convertTime(mTracking.getCreate_date(), mContext) + " - " + distance));
                    marker2.setIcon(icon);

                }
            }
            mapView.invalidate();
        }
    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MTracking>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibMap:
                rvActivities.setVisibility(View.GONE);
                mapView.setVisibility(View.VISIBLE);
                break;
            case R.id.ibList:
                rvActivities.setVisibility(View.VISIBLE);
                mapView.setVisibility(View.GONE);
                break;
            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (resultCode == Constants.RESULT_USERS) {
            mIds = new ArrayList<>();
            mIds = (List<MId>) data.getSerializableExtra("mIds");

            List<MTracking> list = new ArrayList<>();
            for (MTracking mTracking : mTrackings) {
                for (MId id : mIds) {
                    if (mTracking.getUser_id() == id.getId()) {
                        list.add(mTracking);
                    }
                }
            }
            LoadData(list);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        from_date = ((monthOfYear + 1 ) + "/" + dayOfMonth + "/" + year + " 00:00");

        to_Date = ((monthOfYear + 1 ) + "/" + dayOfMonth + "/" + year + " 23:59");

        if(!from_date.isEmpty() && !to_Date.isEmpty()){
            MRequestBody mRequestBody = new MRequestBody();
            mRequestBody.setFrom_date(from_date);
            mRequestBody.setTo_date(to_Date);
            GetRetrofit().create(ServiceAPI.class)
                    .getTrackingUserByUser(preferences.getStringValue(Constants.TOKEN, "")
                            , preferences.getIntValue(Constants.USER_ID, 0)
                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                            ,user_id
                            ,mRequestBody
                    )
                    .enqueue(TrackingByUserActivity.this);
        }
    }
}
