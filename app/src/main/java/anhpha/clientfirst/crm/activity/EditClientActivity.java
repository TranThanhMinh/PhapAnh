package anhpha.clientfirst.crm.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.PhotosAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.cropper.Crop;
import anhpha.clientfirst.crm.customs.CustomMapView;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MClientArea;
import anhpha.clientfirst.crm.model.MClientBusiness;
import anhpha.clientfirst.crm.model.MClientGroup;
import anhpha.clientfirst.crm.model.MClientType;
import anhpha.clientfirst.crm.model.MDistrict;
import anhpha.clientfirst.crm.model.MPhoto;
import anhpha.clientfirst.crm.model.MProvince;
import anhpha.clientfirst.crm.model.MUser;
import anhpha.clientfirst.crm.model.MWard;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditClientActivity extends BaseAppCompatActivity implements CompoundButton.OnCheckedChangeListener, Callback<MAPIResponse<MClient>>, View.OnClickListener, DatePickerDialog.OnDateSetListener {

    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.button1)
    RadioButton button1;
    @Bind(R.id.button2)
    RadioButton button2;
    @Bind(R.id.view1)
    LinearLayout view1;
    @Bind(R.id.view2)
    LinearLayout view2;
    @Bind(R.id.mapview)
    CustomMapView mapView;
    @Bind(R.id.ivCamera)
    ImageView ivCamera;

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
    EditText etAddressDelivery;
    @Bind(R.id.tvManager)
    TextView tvManager;


    @Bind(R.id.spGroup)
    Spinner spGroup;
    @Bind(R.id.spType)
    Spinner spType;
    @Bind(R.id.spArea)
    Spinner spArea;
    @Bind(R.id.spStatus)
    Spinner spStatus;

    //client
    @Bind(R.id.spinner1)
    Spinner spinner1;
    @Bind(R.id.spinner2)
    Spinner spinner2;
    @Bind(R.id.spinner3)
    Spinner spinner3;

    @Bind(R.id.spShareClient)
    Spinner spShareClient;
    @Bind(R.id.spBusinessType)
    Spinner spBusinessType;

    //person
    @Bind(R.id.spMarital)
    Spinner spMarital;
    @Bind(R.id.spGender)
    Spinner spGender;
    @Bind(R.id.etNumberDependent)
    EditText etNumberDependent;
    @Bind(R.id.etIncome)
    EditText etIncome;
    @Bind(R.id.etPassport)
    EditText etPassport;
    @Bind(R.id.tvChooseDate)
    TextView tvChooseDate;

    //company
    @Bind(R.id.etTaxCode)
    EditText etTaxCode;

    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    GoogleMap map;
    Preferences preferences;
    MClient mClient;

    private List<MProvince> mProvinces = new ArrayList<>();
    private List<MDistrict> mDistricts = new ArrayList<>();
    private List<MWard> mWards = new ArrayList<>();
    private List<MDistrict> list = new ArrayList<>();
    private List<MWard> list2 = new ArrayList<>();
    private List<MDistrict> list3 = new ArrayList<>();
    private List<MWard> list4 = new ArrayList<>();

    String province_code;
    String district_code;
    String ward_code;

    String province_code_client;
    String district_code_client;
    String ward_code_client;

    int iProvince = 49;
    int iDistrict = 0;
    int iWard = 0;

    int iProvinceDelivery = 49;
    int iDistrictDelivery = 0;
    int iWardDelivery = 0;
    int client_structure_id = 1;


    private List<MClientGroup> mClientGroups = new ArrayList<>();
    private List<MClientArea> mClientAreas = new ArrayList<>();
    private List<MClientBusiness> mClientBusinesses = new ArrayList<>();
    private List<MClientType> mClientTypes = new ArrayList<>();

    boolean is_map = false;

    protected List<MPhoto> photos = new ArrayList<>();
    protected PhotosAdapter photosAdapter;
    private String filePath = null;
    private Uri mImageCaptureUri;
    private boolean is_edit = false;
    MUser mUser = new MUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        box = new DynamicBox(this, R.layout.activity_client_edit);
        ButterKnife.bind(this);

        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION);

        preferences = new Preferences(mContext);
        try {
            mProvinces = Utils.getProvince(mContext);
            mDistricts = Utils.getDistrict(mContext);
            mWards = Utils.getWard(mContext);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        client_structure_id = getIntent().getIntExtra("Structure", 1);
        mClient = (MClient) getIntent().getSerializableExtra("mClient");

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_client_edit);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        button1.setChecked(true);
        button1.setOnCheckedChangeListener(this);
        button2.setOnCheckedChangeListener(this);
        ivCamera.setOnClickListener(this);

        mapView = (CustomMapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.setClickable(true);
        mapView.setFocusable(true);
        mapView.setDuplicateParentStateEnabled(false);
        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();

        if (map != null) {
            map.setBuildingsEnabled(true);
            map.setIndoorEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
            }
            // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
            MapsInitializer.initialize(this);
            // Updates the location and zoom of the MapView
            LatLng center = map.getCameraPosition().target;

            mapView.invalidate();
        }
        if(mClient!= null){
            is_edit = true;
            photos  = mClient.getPhotos();
            setViewClient();
            mUser.setUser_id(mClient.getUser_manager_id());
            mUser.setUser_name(mClient.getUser_manager_name());
            client_structure_id = mClient.getClient_structure_id();
            if(mClient.getClient_structure_id() == 1){
                findViewById(R.id.input_layout_9).setVisibility(View.GONE);
                findViewById(R.id.input_layout_6).setVisibility(View.GONE);
                findViewById(R.id.input_layout_3).setVisibility(View.GONE);
                tvChooseDate.setOnClickListener(this);

            }else{
                findViewById(R.id.input_layout_8).setVisibility(View.GONE);
                findViewById(R.id.input_layout_10).setVisibility(View.GONE);
                findViewById(R.id.input_layout_11).setVisibility(View.GONE);
                findViewById(R.id.layout_marital).setVisibility(View.GONE);
                findViewById(R.id.layout_birthday).setVisibility(View.GONE);
                findViewById(R.id.layout_gender).setVisibility(View.GONE);

            }
        }else
        {
            if(client_structure_id == 1){
                findViewById(R.id.input_layout_9).setVisibility(View.GONE);
                findViewById(R.id.input_layout_6).setVisibility(View.GONE);
                findViewById(R.id.input_layout_3).setVisibility(View.GONE);
                tvChooseDate.setOnClickListener(this);

            }else{
                findViewById(R.id.input_layout_8).setVisibility(View.GONE);
                findViewById(R.id.input_layout_10).setVisibility(View.GONE);
                findViewById(R.id.input_layout_11).setVisibility(View.GONE);
                findViewById(R.id.layout_marital).setVisibility(View.GONE);
                findViewById(R.id.layout_birthday).setVisibility(View.GONE);
                findViewById(R.id.layout_gender).setVisibility(View.GONE);

            }
            actionBar.setTitle(R.string.title_activity_client_add);
            mUser.setUser_id(preferences.getIntValue(Constants.USER_ID,0));
            mUser.setUser_name(preferences.getStringValue(Constants.USER_NAME,getResources().getString(R.string.user)));
            mClient= new MClient();
            mClient.setStatus_id(1);
            mClient.setUser_manager_id(mUser.getUser_id());
            mClient.setUser_manager_name(mUser.getUser_name());
            photos = new ArrayList<>();
            setViewClient();
        }
        rvActivities.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        llm.setSmoothScrollbarEnabled(true);
        rvActivities.setLayoutManager(llm);
        rvActivities.setAdapter(photosAdapter);

        photosAdapter = new PhotosAdapter(this, photos, new PhotosAdapter.IPhotoCallback() {
            @Override
            public void select(int position) {

            }
        });

        rvActivities.setAdapter(photosAdapter);

        GetRetrofit().create(ServiceAPI.class)
                .getClientAreas(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                )
                .enqueue(new Callback<MAPIResponse<List<MClientArea>>>() {
                    @Override
                    public void onResponse(Call<MAPIResponse<List<MClientArea>>> call, Response<MAPIResponse<List<MClientArea>>> response) {
                        TokenUtils.checkToken(mContext,response.body().getErrors());
                        mClientAreas = response.body().getResult();
                        ArrayAdapter adapter = new ArrayAdapter<>(mContext, R.layout.simple_spinner_item, mClientAreas);
                        spArea.setAdapter(adapter);
                        int i =0;
                        for(MClientArea mClientArea : mClientAreas){
                            if(mClient.getClient_area_id() == mClientArea.getClient_area_id()){
                                spArea.setSelection(i);
                                break;
                            } else if(mClient.getClient_area_id() == 0){
                                break;
                            }
                            i++;
                        }
                    }

                    @Override
                    public void onFailure(Call<MAPIResponse<List<MClientArea>>> call, Throwable t) {

                    }
                });

        GetRetrofit().create(ServiceAPI.class)
                .getClientBusiness(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                )
                .enqueue(new Callback<MAPIResponse<List<MClientBusiness>>>() {
                    @Override
                    public void onResponse(Call<MAPIResponse<List<MClientBusiness>>> call, Response<MAPIResponse<List<MClientBusiness>>> response) {
                        TokenUtils.checkToken(mContext,response.body().getErrors());
                        mClientBusinesses = response.body().getResult();
                        ArrayAdapter adapter = new ArrayAdapter<>(mContext, R.layout.simple_spinner_item, mClientBusinesses);
                        spBusinessType.setAdapter(adapter);
                        int i =0;
                        for(MClientBusiness mClientBusiness : mClientBusinesses){
                            if(mClient.getClient_business_id() == mClientBusiness.getClient_business_id()){
                                spBusinessType.setSelection(i);
                                break;
                            } else if(mClient.getClient_business_id() == 0){
                                break;
                            }
                            i++;
                        }
                    }

                    @Override
                    public void onFailure(Call<MAPIResponse<List<MClientBusiness>>> call, Throwable t) {

                    }
                });
        GetRetrofit().create(ServiceAPI.class)
                .getClientGroups(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                )
                .enqueue(new Callback<MAPIResponse<List<MClientGroup>>>() {
                    @Override
                    public void onResponse(Call<MAPIResponse<List<MClientGroup>>> call, Response<MAPIResponse<List<MClientGroup>>> response) {
                        TokenUtils.checkToken(mContext,response.body().getErrors());
                        mClientGroups = response.body().getResult();
                        ArrayAdapter adapter = new ArrayAdapter<>(mContext, R.layout.simple_spinner_item, mClientGroups);
                        spGroup.setAdapter(adapter);
                        int i =0;
                        for(MClientGroup mClientArea : mClientGroups){
                            if(mClient.getClient_group_id() == mClientArea.getClient_group_id()){
                                spGroup.setSelection(i);
                                break;
                            } else if(mClient.getClient_group_id()== 0){
                                break;
                            }
                            i++;
                        }
                    }

                    @Override
                    public void onFailure(Call<MAPIResponse<List<MClientGroup>>> call, Throwable t) {
                    }
                });
        GetRetrofit().create(ServiceAPI.class)
                .getClientTypes(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                )
                .enqueue(new Callback<MAPIResponse<List<MClientType>>>() {
                    @Override
                    public void onResponse(Call<MAPIResponse<List<MClientType>>> call, Response<MAPIResponse<List<MClientType>>> response) {
                        TokenUtils.checkToken(mContext,response.body().getErrors());
                        mClientTypes = response.body().getResult();
                        ArrayAdapter adapter = new ArrayAdapter<>(mContext, R.layout.simple_spinner_item, mClientTypes);
                        spType.setAdapter(adapter);
                        int i =0;
                        for(MClientType mClientArea : mClientTypes){
                            if(mClient.getClient_type_id() == mClientArea.getClient_type_id()){
                                spType.setSelection(i);
                                break;
                            } else if(mClient.getClient_type_id() == 0){
                                break;
                            }
                            i++;
                        }
                    }

                    @Override
                    public void onFailure(Call<MAPIResponse<List<MClientType>>> call, Throwable t) {

                    }
                });
        tvManager.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
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
        inflater.inflate(R.menu.menu_done, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:

                mClient.setAddress(etAddress.getText().toString());
                mClient.setContact(etContact.getText().toString());
                mClient.setClient_code(etCode.getText().toString());
                mClient.setDescription(etDescription.getText().toString());
                mClient.setEmail(etEmail.getText().toString());
                mClient.setPhone(etPhone.getText().toString());
                mClient.setWebsite(etWeb.getText().toString());
                mClient.setClient_name(etName.getText().toString());

                mClient.setCountry_code("84");
                mClient.setDistrict_code(district_code_client);
                mClient.setProvince_code(province_code_client);
                mClient.setWard_code(ward_code_client);

                if(map!=null){
                    LatLng center = map.getCameraPosition().target;
                    if(center.longitude !=0) {
                        mClient.setLongitude(center.longitude);
                        mClient.setLatitude(center.latitude);
                    }
                }

                mClient.setShare_client(spShareClient.getSelectedItemPosition() == -1?false:spShareClient.getSelectedItemPosition()==0? false:true);
                mClient.setClient_business_id(spBusinessType.getSelectedItemPosition() == -1?0:mClientBusinesses.get(spBusinessType.getSelectedItemPosition()).getClient_business_id());
                mClient.setClient_structure_id(client_structure_id);
                //person
                mClient.setMarital_status_id(spMarital.getSelectedItemPosition() == -1?0:spMarital.getSelectedItemPosition() + 1);
                mClient.setGender(spGender.getSelectedItemPosition() == -1?0:spGender.getSelectedItemPosition() + 1);

                mClient.setNum_dependents(Utils.tryParse(etNumberDependent.getText().toString()));
                mClient.setIncome(Utils.tryParseDouble(etIncome.getText().toString()));
                mClient.setPassport(etPassport.getText().toString());
                mClient.setBirthday(tvChooseDate.getText().toString().indexOf("/")>=0?tvChooseDate.getText().toString():"");

                //company
                mClient.setTax_code(etTaxCode.getText().toString());

                mClient.setUser_latitude(mLastLocation.getLatitude());
                mClient.setUser_longitude(mLastLocation.getLongitude());
                mClient.setUser_manager_id(mUser.getUser_id());

                mClient.setClient_area_id(spArea.getSelectedItemPosition() == -1?0:mClientAreas.get(spArea.getSelectedItemPosition()).getClient_area_id());
                mClient.setClient_group_id(spGroup.getSelectedItemPosition() == -1?0:mClientGroups.get(spGroup.getSelectedItemPosition()).getClient_group_id());
                mClient.setStatus_id(spStatus.getSelectedItemPosition() == -1 ? 1 : spStatus.getSelectedItemPosition()+1);
                mClient.setClient_type_id(mClientTypes.get(spType.getSelectedItemPosition()).getClient_type_id());
                if(Utils.isEmpty(mClient.getClient_name())) {
                    Utils.showError(coordinatorLayout, R.string.require_name_client);
                }else if(!Utils.isEmpty(mClient.getEmail()) && !android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches()){
                    Utils.showError(coordinatorLayout, R.string.invalidate_email);
                }else {
                    box.showLoadingLayout();
                    GetRetrofit().create(ServiceAPI.class)
                            .setClient(preferences.getStringValue(Constants.TOKEN, "")
                                    , preferences.getIntValue(Constants.USER_ID, 0)
                                    , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                    , mClient.getClient_id()
                                    , mClient
                            )
                            .enqueue(this);
                }
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.button1:
                view2.setVisibility(View.VISIBLE);
                view1.setVisibility(View.GONE);
                break;
            case R.id.button2:
                view2.setVisibility(View.GONE);
                view1.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    private void uploadImage(final List<MPhoto> photos){
        if(photos.size() == 0){
            box.hideAll();

            if(is_edit)
                Utils.showDialogSuccess(mContext, R.string.edit_client_done);
            else
                Utils.showDialogSuccess(mContext, R.string.add_client_done);
        }

        for (final MPhoto p : photos){
            if(p.local != null) {
                File file = new File(p.local);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part imagenPerfil = null;
                imagenPerfil = MultipartBody.Part.createFormData("image", file.getName(), requestBody);

                GetRetrofit().create(ServiceAPI.class)
                        .upLoadPhoto(preferences.getStringValue(Constants.TOKEN, "")
                                , preferences.getIntValue(Constants.USER_ID, 0)
                                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                , "photo"
                                , "a"
                                , "cl"
                                , mClient.getClient_id()
                                , imagenPerfil)
                        .enqueue(new Callback<MAPIResponse<MPhoto>>() {
                            @Override
                            public void onResponse(Call<MAPIResponse<MPhoto>> call, Response<MAPIResponse<MPhoto>> response) {
                                LogUtils.api(TAG, call, (response.body()));
                                photos.remove(p);
                                uploadImage(photos);
                            }

                            @Override
                            public void onFailure(Call<MAPIResponse<MPhoto>> call, Throwable t) {
                                LogUtils.d(TAG, "getUserActivities ", t.toString());
                                photos.remove(p);
                                uploadImage(photos);
                            }
                        });
                box.showLoadingLayout();
                LogUtils.d(TAG, "getUserActivities ", "start");
                break;
            }else {
                photos.remove(p);
                uploadImage(photos);
                break;
            }

        }

    }

    @Override
    public void onResponse(Call<MAPIResponse<MClient>> call, Response<MAPIResponse<MClient>> response) {
        LogUtils.api(TAG, call, (response.body()));
        TokenUtils.checkToken(mContext,response.body().getErrors());
        mClient = response.body().getResult();
        if(!response.body().isHasErrors()){
            uploadImage(photos);
        }else {
            box.hideAll();
            if(is_edit)
                Utils.showError(coordinatorLayout, R.string.edit_client_fail);
            else
                Utils.showError(coordinatorLayout, R.string.add_client_fail);

        }

    }

    @Override
    public void onFailure(Call<MAPIResponse<MClient>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
        Utils.showError(coordinatorLayout, R.string.add_client_fail);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvManager:
                startActivityForResult(new Intent(mContext, ChooseUsersActivity.class), Constants.RESULT_USERS);
                break;
            case R.id.tvChooseDate:
                Calendar calendar = Calendar.getInstance();

                int Year = calendar.get(Calendar.YEAR);
                int Month = calendar.get(Calendar.MONTH);
                int Day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                        EditClientActivity.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.setMaxDate(calendar);
                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorApp));
                datePickerDialog.setCancelText(getString(R.string.no));
                datePickerDialog.setOkText(getString(R.string.yes));
                datePickerDialog.setTitle(getString(R.string.choose_date));
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                break;
            case R.id.ivCamera:
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                CharSequence[] charSequences = new CharSequence[2];
                charSequences[0] = getString(R.string.camera);
                charSequences[1] = getString(R.string.library);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(getResources().getString(R.string.choose_image));
                builder.setCancelable(true);
                builder.setItems(charSequences, new  DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                openCamera();
                                break;
                            case 1:
                                openPictures();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.show();
                break;
            default:
                break;
        }
    }

    private void setViewClient() {
        if(mClient.getAddress() != null){
            etAddress.setText(mClient.getAddress());
        }if(mClient.getContact()!= null){
            etContact.setText(mClient.getContact());
        }if(mClient.getEmail()!= null){
            etEmail.setText(mClient.getEmail());
        }if(mClient.getClient_name()!= null){
            etName.setText(mClient.getClient_name());
        }if(mClient.getClient_code()!= null){
            etCode.setText(mClient.getClient_code());
        }if(mClient.getWebsite()!= null){
            etWeb.setText(mClient.getWebsite());
        }if(mClient.getPhone()!= null){
            etPhone.setText(mClient.getPhone());
        }
        if(mClient.getDescription()!= null){
            etDescription.setVisibility(View.VISIBLE);
            etDescription.setText(mClient.getDescription());
        }
        if(mClient.getUser_manager_name()!= null){
            tvManager.setVisibility(View.VISIBLE);
            tvManager.setText(mClient.getUser_manager_name());
        }
        if(mClient.getClient_delivery().size()>0)
        if(mClient.getClient_delivery().get(0).getAddress()!= null){
            etAddressDelivery.setVisibility(View.VISIBLE);
            etAddressDelivery.setText(mClient.getClient_delivery().get(0).getAddress());
        }
        List<String> string=new ArrayList<String>();

        string.add(getString(R.string.activity));
        string.add(getString(R.string.in_activity));
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
        spStatus.setAdapter(adapter);
        spStatus.setSelection(mClient.getStatus_id() - 1);


        string=new ArrayList<>();
        string.add(getString(R.string.male));
        string.add(getString(R.string.female));
        string.add(getString(R.string.other));

        adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
        spGender.setAdapter(adapter);
        spGender.setSelection(mClient.getGender() - 1);

        string=new ArrayList<>();
        string.add(getString(R.string.single));
        string.add(getString(R.string.engaged));
        string.add(getString(R.string.married));
        string.add(getString(R.string.separated));
        string.add(getString(R.string.divorced));
        string.add(getString(R.string.widow));
        string.add(getString(R.string.widower));

        adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
        spMarital.setAdapter(adapter);
        spMarital.setSelection(mClient.getMarital_status_id() - 1);

        string=new ArrayList<>();
        string.add(getString(R.string.no));
        string.add(getString(R.string.manager));

        adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
        spShareClient.setAdapter(adapter);
        spShareClient.setSelection(mClient.isShare_client()?1 :0);

        tvChooseDate.setText(mClient.getBirthday().isEmpty()?getString(R.string.choose_date):mClient.getBirthday().split(" ")[0]);
        etPassport.setText(mClient.getPassport());
        etTaxCode.setText(mClient.getTax_code());
        etNumberDependent.setText(mClient.getNum_dependents() + "");
        etIncome.setText(Utils.formatCurrency(mClient.getIncome()));
        etIncome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                etIncome.removeTextChangedListener(this);
                double prePay = Utils.tryParseDouble(editable.toString().replace(",", ""));
                etIncome.setText(Utils.formatCurrency(prePay));
                etIncome.setSelection(etIncome.getText().length());
                etIncome.addTextChangedListener(this);
            }
        });
        ArrayAdapter adapter4 = new ArrayAdapter<>(this, R.layout.simple_spinner_item, mProvinces);
        spinner1.setAdapter(adapter4);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                list3 = new ArrayList<>();
                province_code_client = mProvinces.get(i).getProvince_code();
                for (MDistrict d : mDistricts) {
                    if (d.getProvince_code().equals(province_code_client)) {
                        list3.add(d);
                    }
                }
                ArrayAdapter adapter3 = new ArrayAdapter<MDistrict>(mContext, R.layout.simple_spinner_item, list3);
                spinner2.setAdapter(adapter3);
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        list4 = new ArrayList<>();
                        district_code_client = list3.get(i).getDistrict_code();
                        for (MWard d : mWards) {
                            if (d.getDistrict_code().equals(district_code_client)) {
                                list4.add(d);
                            }
                        }
                        ArrayAdapter adapter4 = new ArrayAdapter<MWard>(mContext, R.layout.simple_spinner_item, list4);
                        spinner3.setAdapter(adapter4);
                        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                ward_code_client = list4.get(i).getWard_code();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(!Utils.isEmpty(Utils.trim(mClient.getProvince_code())) && !Utils.trim(mClient.getProvince_code()).equals("0")) {
            iProvince = 0;

            for (MProvince mProvince : mProvinces) {
                if (Utils.trim(mProvince.getProvince_code()).equals(Utils.trim(mClient.getProvince_code()))) {
                    iDistrict = 0;
                    list3 = new ArrayList<>();
                    province_code_client = Utils.trim(mProvince.getProvince_code());
                    for (MDistrict d : mDistricts) {
                        if (d.getProvince_code().equals(province_code_client)) {
                            list3.add(d);
                        }
                    }
                    for (MDistrict mDistrict : list3) {
                        if (Utils.trim(mDistrict.getDistrict_code()).equals(Utils.trim(mClient.getDistrict_code()))) {
                            list4 = new ArrayList<>();
                            district_code_client = Utils.trim(mClient.getDistrict_code());
                            for (MWard d : mWards) {
                                if (d.getDistrict_code().equals(district_code_client)) {
                                    list4.add(d);
                                }
                            }
                            for (MWard mWard : list4) {
                                if (Utils.trim(mWard.getWard_code()).equals(Utils.trim(mClient.getWard_code()))) {
                                    break;
                                } else if (Utils.isEmpty(mClient.getWard_code())) {
                                    break;
                                }
                                iWard++;
                            }
                            break;
                        } else if (Utils.isEmpty(mClient.getDistrict_code())) {
                            break;
                        }
                        iDistrict++;
                    }
                    break;
                } else if (Utils.isEmpty(mClient.getProvince_code())) {
                    break;
                }
                iProvince++;
            }
        }else{
            iProvince = 49;
        }
        spinner1.setSelection(iProvince);
        spinner1.postDelayed(new Runnable() {
            @Override
            public void run() {
                spinner2.setSelection(iDistrict);
                spinner2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        spinner3.setSelection(iWard);
                    }
                }, 200);
            }
        }, 200);



        mapView.setClickable(true);
        mapView.setFocusable(true);
        mapView.setDuplicateParentStateEnabled(false);
        if(map != null) {
            if (mClient.getLatitude() != 0 || mClient.getLongitude() != 0) {

                // Updates the location and zoom of the MapView
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(mClient.getLatitude(), mClient.getLongitude()), 15);
                map.animateCamera(cameraUpdate);
                LatLng center = map.getCameraPosition().target;
                Marker marker = map.addMarker(new MarkerOptions()
                        .position(new LatLng(mClient.getLatitude(), mClient.getLongitude()))
                        .title(mClient.getClient_name())
                        .snippet(mClient.getAddress()));

                mapView.invalidate();
            } else {
                // Updates the location and zoom of the MapView
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 15);
                map.animateCamera(cameraUpdate);
                mapView.invalidate();
                is_map = true;

            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        if(is_map && map != null){
            // Updates the location and zoom of the MapView
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 15);
            map.animateCamera(cameraUpdate);
            LatLng center = map.getCameraPosition().target;
            mClient.setLatitude(center.latitude);
            mClient.setLongitude(center.longitude);
            mapView.invalidate();
            is_map = false;
        }

    }
    private void openPictures() {
        Crop.pickImage(this);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));

        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

        try {
            intent.putExtra("return-data", true);

            startActivityForResult(intent, Constants.PICK_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            LogUtils.e(TAG, "openCamera", e);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            if (data != null) {

                filePath = data.getStringExtra("file").toString();
                MPhoto p = new MPhoto();
                p.local = filePath;
                photos.add(p);
                photosAdapter.setPhotoList(photos);
                photosAdapter.notifyDataSetChanged();

            }
        } else if (requestCode == Constants.PICK_FROM_CAMERA) {
            beginCrop(mImageCaptureUri);
        }
        // Check which request we're responding to
        if (resultCode == Constants.RESULT_USER) {
            mUser = (MUser) data.getSerializableExtra("mUser");
            tvManager.setText(mUser.getUser_name());
        }
    }

    private void beginCrop(Uri source) {
        Intent intent = new Intent(getApplication(), CropperImage.class);
        intent.putExtra("source", source.toString());
        startActivityForResult(intent, Crop.REQUEST_CROP);
    }
    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Year, (Month + 1), Day);
        Calendar now = Calendar.getInstance();

        tvChooseDate.setText((Month + 1) + "/" + Day + "/" + Year );

    }
}
