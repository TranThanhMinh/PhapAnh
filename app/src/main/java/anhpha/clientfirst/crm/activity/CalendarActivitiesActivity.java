package anhpha.clientfirst.crm.activity;

import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.ClientAdapter;
import anhpha.clientfirst.crm.adapter.MStatus;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.DividerItemDecoration;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MClientArea;
import anhpha.clientfirst.crm.model.MClientGroup;
import anhpha.clientfirst.crm.model.MClientRequest;
import anhpha.clientfirst.crm.model.MClientType;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MLabel;
import anhpha.clientfirst.crm.model.MUser;
import anhpha.clientfirst.crm.model.MWeek;
import anhpha.clientfirst.crm.model.MWeekWork;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.HaversineAlgorithm;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarActivitiesActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<MWeekWork>>, View.OnClickListener {
    @Bind(R.id.imageButton1)
    ImageButton imageButton1;
    @Bind(R.id.imageButton2)
    ImageButton imageButton2;
    @Bind(R.id.etText)
    EditText etText;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.ibClose)
    ImageButton ibClose;
    @Bind(R.id.ibDone)
    ImageButton ibDone;
    @Bind(R.id.layout_find)
    LinearLayout layout_find;
    @Bind(R.id.imageButton3)
    ImageButton imageButton3;
    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    ClientAdapter activityAdapter;
    List<MClient> mClients = new ArrayList<>();
    List<MId> mIds = new ArrayList<>();
    List<MClientType> mTypes = new ArrayList<>();
    List<MClientGroup> mGroups = new ArrayList<>();
    List<MLabel> mLabels = new ArrayList<>();
    List<MStatus> mStatus = new ArrayList<>();
    List<MClientArea> mAreas = new ArrayList<>();
    boolean is_filter = false;
    LinearLayoutManager mLayoutManager;
    Preferences preferences;
    boolean is_distance;
    private boolean mHasRequestedMore = false;
    MapView mapView;
    GoogleMap map;
    boolean loading = true;
    int object_id ;
    int type_id;
    MWeekWork mWeekWork = new MWeekWork();
    private MClientRequest mClientRequest = new MClientRequest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_calendar_activities);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);
        object_id = preferences.getIntValue(Constants.USER_ID,0);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_calendar_activities);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        ibClose.setOnClickListener(this);
        ibDone.setOnClickListener(this);
        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        activityAdapter = new ClientAdapter(mContext, mClients, is_distance);
        rvActivities.setAdapter(activityAdapter);

        box.showLoadingLayout();


        mapView = (MapView) findViewById(R.id.mapview);

        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        if(map!= null) {
            map.setBuildingsEnabled(true);
            map.setIndoorEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.setMyLocationEnabled(true);
            // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
            MapsInitializer.initialize(this);
            // Updates the location and zoom of the MapView
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(TAG, "getUserActivities ", "start");
        loadClient();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_calendar_user, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user:
                startActivityForResult(new Intent(mContext, ChooseUsersActivity.class), Constants.RESULT_USERS);
                return true;

            case R.id.calendar:
                CharSequence[] charSequences = new CharSequence[mWeekWork.getWeeks().size()];
                int i = 0;
                for(MWeek w  :mWeekWork.getWeeks() ){
                    charSequences[i] = w.getWeek_day_type_code() + " " + w.getDate().split(" ")[0];
                            i++;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(getResources().getString(R.string.calendar));
                builder.setCancelable(true);
                builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        type_id = i+1;
                        loadClient();
                    }
                });
                builder.show();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<MWeekWork>> call, Response<MAPIResponse<MWeekWork>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        //swipeRefreshLayout.setRefreshing(false);
        TokenUtils.checkToken(mContext,response.body().getErrors());
        loading = true;
        //swipeRefreshLayout.setRefreshing(false);
        activityAdapter.setIs_distance(is_distance);
        mWeekWork = response.body().getResult();

        for (MClient mTracking : mWeekWork.getClients()) {

            mTracking.setDistance(HaversineAlgorithm.HaversineInM(
                    mLastLocation.getLatitude()
                    , mLastLocation.getLongitude()
                    , mTracking.getLatitude()
                    , mTracking.getLongitude()));
        }


            mClients = response.body().getResult().getClients();
            activityAdapter.setActivityItemList(mClients);
            activityAdapter.setIs_distance(is_distance);
            rvActivities.setAdapter(activityAdapter);
        for(MWeek w : mWeekWork.getWeeks()){
            if(w.is_date){
                title.setText(w.getWeek_day_type_code() + " " + w.getDate().split(" ")[0]);
                break;
            }
        }
        LoadData(mClients);

    }
    public  void LoadData( List<MClient> mTrackings){

        if(map!= null) {
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_dms_55);
            BitmapDescriptor icon2 = BitmapDescriptorFactory.fromResource(R.mipmap.ic_dms_52);
            map.clear();
            for (MClient mTracking : mTrackings) {

                String distance = Utils.convertDistance(HaversineAlgorithm.HaversineInM(
                        mLastLocation.getLatitude()
                        , mLastLocation.getLongitude()
                        , mTracking.getLatitude()
                        , mTracking.getLongitude()));

                if (!distance.equals("-")) {

                    Marker marker2 = map.addMarker(new MarkerOptions()
                            .position(new LatLng(mTracking.getLatitude(), mTracking.getLongitude()))
                            .title(mTracking.getClient_name())
                            .snippet(distance));
                    marker2.setIcon(icon);

                }
            }
            mapView.invalidate();
        }
    }


    @Override
    public void onFailure(Call<MAPIResponse<MWeekWork>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton1:
                is_distance = false;
                loadClient();
                rvActivities.setVisibility(View.VISIBLE);
                mapView.setVisibility(View.GONE);
                break;
            case R.id.imageButton2:
                is_distance = true;
                loadClient();
                rvActivities.setVisibility(View.VISIBLE);
                mapView.setVisibility(View.GONE);
                break;
            case R.id.imageButton3:
                rvActivities.setVisibility(View.GONE);
                mapView.setVisibility(View.VISIBLE);
                break;
            case R.id.ibClose:
                layout_find.setVisibility(View.GONE);
                etText.setText("");
                loadClient();
                break;
            case R.id.ibDone:
                mClientRequest = new MClientRequest();
               loadClient();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (resultCode == Constants.RESULT_USER) {
            try {
                object_id = ((MUser) data.getSerializableExtra("mUser")).getUser_id();
                loadClient();
            }catch (Exception e){

            }
        }
    }



    private void loadClient() {
        mClientRequest.setValue(etText.getText().toString());
        GetRetrofit().create(ServiceAPI.class)
                .getCalendarActivity(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , object_id
                        ,type_id

                )
                .enqueue(this);
    }

}
