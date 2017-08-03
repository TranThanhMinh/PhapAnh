package anhpha.clientfirst.crm.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.ActivityAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.CustomMapView;
import anhpha.clientfirst.crm.customs.CustomRecyclerView;
import anhpha.clientfirst.crm.customs.RecyclerTouchListener;
import anhpha.clientfirst.crm.fab.FloatingActionButton;
import anhpha.clientfirst.crm.fab.FloatingActionMenu;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MActivity;
import anhpha.clientfirst.crm.model.MActivityItem;
import anhpha.clientfirst.crm.model.MCall;
import anhpha.clientfirst.crm.model.MCheckin;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MClientLabel;
import anhpha.clientfirst.crm.model.MEmail;
import anhpha.clientfirst.crm.model.MEvent;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.model.MRequestBody;
import anhpha.clientfirst.crm.model.MWorkUser;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientActivity extends BaseAppCompatActivity implements RecyclerTouchListener.ClickListener, CompoundButton.OnCheckedChangeListener, Callback<MAPIResponse<MActivity>>, View.OnClickListener {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.textView7)
    TextView textView7;
    @Bind(R.id.imageButton1)
    ImageButton imageButton1;
    @Bind(R.id.imageButton2)
    ImageButton imageButton2;
    @Bind(R.id.imageButton3)
    ImageButton imageButton3;
    @Bind(R.id.imageButton4)
    ImageButton imageButton4;
    @Bind(R.id.imageButton5)
    ImageButton imageButton5;
    @Bind(R.id.imageButton6)
    ImageButton imageButton6;
    @Bind(R.id.imageButton7)
    ImageButton imageButton7;
    @Bind(R.id.line1)
    LinearLayout line1;
    @Bind(R.id.line2)
    LinearLayout line2;
    @Bind(R.id.line3)
    LinearLayout line3;
    @Bind(R.id.line4)
    LinearLayout line4;
    @Bind(R.id.line5)
    LinearLayout line5;
    @Bind(R.id.line6)
    LinearLayout line6;
    @Bind(R.id.line7)
    LinearLayout line7;

    @Bind(R.id.menu_item1)
    FloatingActionButton menu_item1;
    @Bind(R.id.menu_item2)
    FloatingActionButton menu_item2;
    @Bind(R.id.menu_item3)
    FloatingActionButton menu_item3;
    @Bind(R.id.menu_item4)
    FloatingActionButton menu_item4;
    @Bind(R.id.menu_item5)
    FloatingActionButton menu_item5;
    @Bind(R.id.menu_item6)
    FloatingActionButton menu_item6;
    @Bind(R.id.menu_item7)
    FloatingActionButton menu_item7;
    @Bind(R.id.menu_item8)
    FloatingActionButton menu_item8;
    @Bind(R.id.menu)
    FloatingActionMenu menu;

    @Bind(R.id.tvAmount)
    TextView tvAmount;
    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.rvActivities)
    CustomRecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.button1)
    RadioButton button1;
    @Bind(R.id.button2)
    RadioButton button2;
    @Bind(R.id.view)
    RelativeLayout view;
    @Bind(R.id.tvClientName)
    TextView tvClientName;
    @Bind(R.id.tvAddress)
    TextView tvAddress;


    @Bind(R.id.etName)
    EditText etName;
    @Bind(R.id.etCode)
    EditText etCode;
    @Bind(R.id.etAddress)
    EditText etAddress;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etWeb)
    EditText etWeb;
    @Bind(R.id.etContact)
    EditText etContact;
    @Bind(R.id.etDescription)
    EditText etDescription;
    @Bind(R.id.spGroup)
    Spinner spGroup;
    @Bind(R.id.spType)
    Spinner spType;
    @Bind(R.id.spArea)
    Spinner spArea;
    @Bind(R.id.spStatus)
    Spinner spStatus;

    @Bind(R.id.view_label)
    GridLayout view_label;

    CustomMapView mapView;
    GoogleMap map;


    boolean isHide = true;
    boolean isLoadClient = false;
    MActivity mActivity = new MActivity();
    MActivityItem mActivityItem = new MActivityItem();
    MClient mClient = new MClient();
    List<MId> mIds = new ArrayList<>();
    List<MActivityItem> mActivityItems = new ArrayList<>();
    List<MActivityItem> mActivityItems1 = new ArrayList<>();
    String toDate;
    String fromDate;
    int type = 0;
    Preferences preferences;
    ActivityAdapter activityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_client);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mClient = (MClient) getIntent().getSerializableExtra("mClient");
        mActivityItem = (MActivityItem) getIntent().getSerializableExtra("mActivityItem");
        mapView = (CustomMapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        button2.setChecked(true);
        button1.setOnCheckedChangeListener(this);
        button2.setOnCheckedChangeListener(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        // LinearLayoutManager mLayoutManager = new anhpha.fieldwork.dms.llm.LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvActivities, this));

        activityAdapter = new ActivityAdapter(getActivityItems(mActivityItems));
        if (mClient != null) {
            tvClientName.setText(mClient.getClient_name());
            tvAddress.setText(mClient.getAddress());
            if (mClient.getAddress() != null && !mClient.getAddress().isEmpty()) {
                tvAddress.setVisibility(View.VISIBLE);
            }
            findViewById(R.id.ibArrow).setVisibility(View.GONE);
        } else {
            mClient = new MClient();
        }

        if (mActivityItem != null) {
            mClient.setClient_id(mActivityItem.getClient_id());
            mClient.setClient_name(mActivityItem.getClient_name());
            mClient.setAddress(mActivityItem.getAddress());

            tvClientName.setText(mClient.getClient_name());
            tvAddress.setText(mClient.getAddress());
            if (mClient.getAddress() != null && !mClient.getAddress().isEmpty()) {
                tvAddress.setVisibility(View.VISIBLE);
            }
            findViewById(R.id.ibArrow).setVisibility(View.GONE);
            type = mActivityItem.getActivity_type();

            switch (type) {

                case Constants.ACTIVITY_TYPE_ORDER:
                    imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_11));
                    break;
                case Constants.ACTIVITY_TYPE_CLIENT:
                    imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_3));
                    break;
                case Constants.ACTIVITY_TYPE_CHECKIN:
                    imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_97));
                    break;
                case Constants.ACTIVITY_TYPE_CALL:
                    imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_6));
                    break;
                case Constants.ACTIVITY_TYPE_WORK:
                    imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_7));
                    break;
                case Constants.ACTIVITY_TYPE_EVENT:
                    imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_15));
                    break;
                case Constants.ACTIVITY_TYPE_EMAIL:
                    imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_1));
                    break;
                default:
                    break;
            }
        }

        fromDate  =  "01/01/1992";
        toDate = "01/01/2099";

        line1.setOnClickListener(this);
        line2.setOnClickListener(this);
        line3.setOnClickListener(this);
        line4.setOnClickListener(this);
        line5.setOnClickListener(this);
        line6.setOnClickListener(this);
        line7.setOnClickListener(this);

        menu_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, OrderActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
        menu_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, WorkActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
        menu_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, CheckinActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
        menu_item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, CallActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
        menu_item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, EmailActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
        menu_item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, EventsClientActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
        menu_item7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, LabelsActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
        menu_item8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, HistoryFocusActivity.class).putExtra("mClient", mClient));
                menu.close(true);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_calenda, menu);
        if (isHide) {
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.edit)
                    menu.getItem(i).setVisible(false);
                if (menu.getItem(i).getItemId() == R.id.calendar)
                    menu.getItem(i).setVisible(true);
            }
        } else {
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.edit && mClient.is_permission_edit())
                    menu.getItem(i).setVisible(true);
                if (menu.getItem(i).getItemId() == R.id.calendar)
                    menu.getItem(i).setVisible(false);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:
                startActivityForResult(new Intent(mContext, CalendarClientActivity.class), Constants.RESULT_CALENDAR);
                return true;

            case R.id.edit:
                Intent intent = new Intent(mContext, EditClientActivity.class);
                intent.putExtra("mClient", mClient);
                startActivity(intent);
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<MActivity>> call, Response<MAPIResponse<MActivity>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        mActivity = response.body().getResult();
        mActivityItems = mActivity.getActivies();

        activityAdapter = new ActivityAdapter(getActivityItems(mActivityItems));
        rvActivities.setAdapter(activityAdapter);
        activityAdapter.notifyDataSetChanged();

        tvAmount.setText(Utils.formatCurrency(mActivity.getSales_amount()));

        textView1.setText(mActivity.getOrder_count() + "");
        textView2.setText(mActivity.getAdd_client_count() + "");
        textView3.setText(mActivity.getCheckin_count() + "");
        textView4.setText(mActivity.getCall_count() + "");
        textView5.setText(mActivity.getWork_count() + "");
        textView7.setText(mActivity.getEmail_count() + "");
        textView6.setText(mActivity.getEvent_count() + "");

        view_label.removeAllViews();
        int width = (Utils.getWidth(mContext) / 3);
        int height = (int) (width / 3.5);
        for (MClientLabel mClientLabel : mClient.getLabels()) {
            Button valueTV = new Button(mContext);
            if(mClientLabel.getHex().isEmpty())
                valueTV.setBackgroundColor(Color.GRAY);
            else
                valueTV.setBackgroundColor(Color.parseColor(mClientLabel.getHex()));
            valueTV.setId((int) System.currentTimeMillis() + new Random().nextInt(255));
            valueTV.setTextColor(Color.WHITE);
            valueTV.setMaxLines(1);
            valueTV.setTransformationMethod(null);
            valueTV.setText(mClientLabel.getClient_label_name());
            valueTV.setLayoutParams(new android.app.ActionBar.LayoutParams(width, height));
            view_label.addView(valueTV);
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.button1:
                if(b== true) {
                    Log.d(compoundButton.getId()+""+b,"abab");
                    if (!mClient.isShare_client() && mClient.getUser_manager_id() != preferences.getIntValue(Constants.USER_ID, 0)) {
                        Utils.showError(coordinatorLayout, R.string.client_not_share);
                        rvActivities.setVisibility(View.VISIBLE);
                        view.setVisibility(View.GONE);
                        isHide = true;
                        invalidateOptionsMenu();
                    } else {
                        rvActivities.setVisibility(View.GONE);
                        view.setVisibility(View.VISIBLE);
                        isHide = false;
                        invalidateOptionsMenu();
                        setViewClient();
                    }
                }
                break;
            case R.id.button2:
                if(b == true) {
                    rvActivities.setVisibility(View.VISIBLE);
                    view.setVisibility(View.GONE);
                    isHide = true;
                    invalidateOptionsMenu();
                }else {
                    if (!mClient.isShare_client() && mClient.getUser_manager_id() != preferences.getIntValue(Constants.USER_ID, 0)) {
                        Utils.showError(coordinatorLayout, R.string.client_not_share);
                        rvActivities.setVisibility(View.VISIBLE);
                        view.setVisibility(View.GONE);
                        isHide = true;
                        invalidateOptionsMenu();
                    } else {
                        rvActivities.setVisibility(View.GONE);
                        view.setVisibility(View.VISIBLE);
                        isHide = false;
                        invalidateOptionsMenu();
                        setViewClient();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void setViewClient() {
        if (!mClient.getAddress().isEmpty()) {
            etAddress.setVisibility(View.VISIBLE);
            etAddress.post(new Runnable() {
                               public void run() {
                                   etAddress.setText(mClient.getAddress());
                                   etAddress.setHint("");
                               }
                           });
        }
        if (!mClient.getContact().isEmpty()) {
            etContact.setVisibility(View.VISIBLE);
            etContact.post(new Runnable() {
                public void run() {
                    etContact.setText(mClient.getContact());
                    etContact.setHint("");
                }
            });
        }
        if (!mClient.getEmail().isEmpty()) {
            etEmail.setVisibility(View.VISIBLE);
            etEmail.post(new Runnable() {
                public void run() {
                    etEmail.setText(mClient.getEmail());
                    etEmail.setHint("");
                }
            });

        }
        if (!mClient.getClient_name().isEmpty()) {
            etName.setVisibility(View.VISIBLE);
            etName.post(new Runnable() {
                public void run() {
                    etName.setText(mClient.getClient_name());
                    etName.setHint("");
                }
            });

        }
        if (!mClient.getClient_code().isEmpty()) {
            etCode.setVisibility(View.VISIBLE);
            etCode.post(new Runnable() {
                public void run() {
                    etCode.setText(mClient.getClient_code());
                    etCode.setHint("");
                }
            });
        }
        if (!mClient.getWebsite().isEmpty()) {
            etWeb.setVisibility(View.VISIBLE);
            etWeb.post(new Runnable() {
                public void run() {
                    etWeb.setText(mClient.getWebsite());
                    etWeb.setHint("");
                }
            });
        }
        if (!mClient.getPhone().isEmpty()) {
            etPhone.setVisibility(View.VISIBLE);
            etPhone.post(new Runnable() {
                public void run() {
                    etPhone.setText(mClient.getPhone());
                    etPhone.setHint("");
                }
            });

        }
        if (!mClient.getDescription().isEmpty()) {
            etDescription.setVisibility(View.VISIBLE);
            etDescription.post(new Runnable() {
                public void run() {
                    etDescription.setText(mClient.getDescription());
                    etDescription.setHint(getString(R.string.description));
                }
            });

        }
        etDescription.post(new Runnable() {
            public void run() {
                etDescription.setHint("");
            }
        });

        List<String> string = new ArrayList<String>();
        string.add(mClient.getClient_group_name());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, string);
        spGroup.setAdapter(adapter);

        string = new ArrayList<>();
        string.add(mClient.getClient_type_name());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, string);
        spType.setAdapter(adapter);

        string = new ArrayList<>();
        string.add(mClient.getStatus_id() == 1 ? getString(R.string.activity) : getString(R.string.in_activity));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, string);
        spStatus.setAdapter(adapter);

        string = new ArrayList<>();
        string.add(mClient.getClient_area_name());
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, string);
        spArea.setAdapter(adapter);

        etEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + etEmail.getText().toString()));
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {

                }

            }
        });
        etPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.CALL_PHONE);
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mClient.getPhone()));
                    startActivity(intent);
                }catch (Exception e){}
            }
        });
        etWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = mClient.getWebsite();
                if (!url.startsWith("http://") && !url.startsWith("https://"))
                    url = "http://" + url;
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });


        mapView.setClickable(true);
        mapView.setFocusable(true);
        mapView.setDuplicateParentStateEnabled(false);

        if (mClient.getLatitude() != 0 || mClient.getLongitude() != 0) {
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
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(mClient.getLatitude(), mClient.getLongitude()), 15);
                map.animateCamera(cameraUpdate);
                LatLng center = map.getCameraPosition().target;
                Marker marker = map.addMarker(new MarkerOptions()
                        .position(new LatLng(mClient.getLatitude(), mClient.getLongitude()))
                        .title(mClient.getClient_name())
                        .snippet(mClient.getAddress()));

                mapView.invalidate();
            }
        } else {
            mapView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(Call<MAPIResponse<MActivity>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
        imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_16));
        imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_17));
        imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_96));
        imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_18));
        imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_66));
        imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_23));
        imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_20));
        switch (view.getId()) {
            case R.id.line1:
                if (type != Constants.ACTIVITY_TYPE_ORDER) type = Constants.ACTIVITY_TYPE_ORDER;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_ORDER)
                    imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_16));
                else imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_11));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line2:
                if (type != Constants.ACTIVITY_TYPE_CLIENT) type = Constants.ACTIVITY_TYPE_CLIENT;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_CLIENT)
                    imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_17));
                else imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_3));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line3:
                if (type != Constants.ACTIVITY_TYPE_CHECKIN) type = Constants.ACTIVITY_TYPE_CHECKIN;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_CHECKIN)
                    imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_96));
                else imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_97));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line4:
                if (type != Constants.ACTIVITY_TYPE_CALL) type = Constants.ACTIVITY_TYPE_CALL;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_CALL)
                    imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_18));
                else imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_6));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line5:
                if (type != Constants.ACTIVITY_TYPE_WORK) type = Constants.ACTIVITY_TYPE_WORK;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_WORK)
                    imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_66));
                else imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_7));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line6:
                if (type != Constants.ACTIVITY_TYPE_EVENT)
                    type = Constants.ACTIVITY_TYPE_EVENT;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_EVENT)
                    imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_23));
                else imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_15));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line7:
                if (type != Constants.ACTIVITY_TYPE_EMAIL) type = Constants.ACTIVITY_TYPE_EMAIL;
                else type = 0;
                if (type != Constants.ACTIVITY_TYPE_EMAIL)
                    imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_20));
                else imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_1));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    public List<MActivityItem> getActivityItems(List<MActivityItem> mActivityItems) {
        mActivityItems1 = new ArrayList<>();
        switch (type) {
            case 0:
                mActivityItems1.addAll(mActivityItems);
                break;
            case 1:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 1)
                        mActivityItems1.add(i);
                }
                break;
            case 2:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 2)
                        mActivityItems1.add(i);
                }
                break;
            case 3:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 3)
                        mActivityItems1.add(i);
                }
                break;
            case 9:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 9)
                        mActivityItems1.add(i);
                }
                break;
            case 5:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 5)
                        mActivityItems1.add(i);
                }
                break;
            case 6:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 6)
                        mActivityItems1.add(i);
                }
                break;
            case 7:
                for (MActivityItem i : mActivityItems) {
                    if (i.getActivity_type() == 7)
                        mActivityItems1.add(i);
                }
                break;
            default:
                break;
        }
        return mActivityItems1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (resultCode == Constants.RESULT_USERS) {
            mIds = new ArrayList<>();
            mIds = (List<MId>) data.getSerializableExtra("mIds");

        }
        if (resultCode == Constants.RESULT_CALENDAR) {
            toDate = data.getStringExtra("toDate");
            fromDate = data.getStringExtra("fromDate");
            tvDate.setText(data.getStringExtra("tvDate"));

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRetrofit().create(ServiceAPI.class)
                .getClient(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , mClient.getClient_id()
                )
                .enqueue(new Callback<MAPIResponse<MClient>>() {
                    @Override
                    public void onResponse(Call<MAPIResponse<MClient>> call, Response<MAPIResponse<MClient>> response) {
                        LogUtils.api(TAG, call, (response.body()));
                        box.hideAll();
                        TokenUtils.checkToken(mContext,response.body().getErrors());
                        mClient = response.body().getResult();
                       // setViewClient();
                    }

                    @Override
                    public void onFailure(Call<MAPIResponse<MClient>> call, Throwable t) {
                        LogUtils.d(TAG, "getUserActivities ", t.toString());
                        box.hideAll();
                    }
                });
        box.showLoadingLayout();


        MRequestBody mRequestBody = new MRequestBody();
        mRequestBody.setFrom_date(fromDate);
        mRequestBody.setTo_date(toDate);
        mRequestBody.setUser_ids(mIds);

        GetRetrofit().create(ServiceAPI.class)
                .getUserActivities(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , mClient.getClient_id()
                        , mRequestBody
                )
                .enqueue(this);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        box.showLoadingLayout();

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
    public void onClick(View view, int position) {
        MActivityItem activityItem = activityAdapter.getItem(position);
        switch (activityItem.getActivity_type()){
            case Constants.ACTIVITY_TYPE_ORDER:
                MOrder mOrder = new MOrder();
                mOrder.setClient_id(mClient.getClient_id());
                mOrder.setClient_name(mClient.getClient_name());
                mOrder.setAddress(mClient.getAddress());
                mOrder.setOrder_sheet_id(-1);
                mOrder.setOrder_contract_id(activityItem.getOrder_contract_id());
                mContext.startActivity(new Intent(mContext, OrderViewActivity.class).putExtra("mClient", mClient).putExtra("mOrder", mOrder));
                 break;
            case Constants.ACTIVITY_TYPE_WORK:
                MWorkUser mWorkUser = new MWorkUser();
                mWorkUser.setWork_user_id(activityItem.getWork_user_id());
                    mContext.startActivity(new Intent(mContext, WorkActivity.class).putExtra("mClient", mClient).putExtra("mWorkUser", mWorkUser));
                break;
            case Constants.ACTIVITY_TYPE_CHECKIN:
                MCheckin mCheckin = new MCheckin();
                mCheckin.setUser_checkin_id(activityItem.getMeeting_id());
                mCheckin.setContent_checkin(activityItem.getActivity_content());
                mContext.startActivity(new Intent(mContext, CheckinActivity.class).putExtra("mClient", mClient).putExtra("mCheckin", mCheckin));
                break;
            case Constants.ACTIVITY_TYPE_CALL:
                MCall mCall = new MCall();
                mCall.setCall_user_id(activityItem.getUser_call_id());
                mCall.setContent_call(activityItem.getActivity_content());
                    mContext.startActivity(new Intent(mContext, CallActivity.class).putExtra("mClient", mClient).putExtra("mCall", mCall));
                break;
            case Constants.ACTIVITY_TYPE_EMAIL:
                MEmail mEmail = new MEmail();
                mEmail.setEmail_user_id(1);
                mEmail.setContent_email(activityItem.getActivity_content());
                    mContext.startActivity(new Intent(mContext, EmailActivity.class).putExtra("mClient", mClient).putExtra("mEmail", mEmail));
                break;
            case Constants.ACTIVITY_TYPE_EVENT:
                MEvent mEvent = new MEvent();
                mEvent.setEvent_id(activityItem.getEvent_id());
                mContext.startActivity(new Intent(mContext, EventClientActivity.class).putExtra("mClient", mClient).putExtra("mEvent", mEvent).putExtra("ShowMenu",false));
                break;
            default:
                break;

        }
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
