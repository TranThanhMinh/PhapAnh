package anhpha.clientfirst.crm.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MCalendar;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MUser;
import anhpha.clientfirst.crm.model.MWorkUser;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.CalendarContentResolver;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<MWorkUser>>, View.OnClickListener ,AdapterView.OnItemSelectedListener , TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.tvClientName)
    TextView tvClientName;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.tvUser)
    TextView tvUser;
    @Bind(R.id.tvNameType)
    TextView tvNameType;
    @Bind(R.id.spinner)
    Spinner spinner;
    @Bind(R.id.spinner2)
    Spinner spinner2;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.include)
    Toolbar toolbar;
    MWorkUser mWorkUser = new MWorkUser();
    MClient mClient = new MClient();
    int type = 1;
    Preferences preferences;

    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    int Year, Month, Day,Hour,Minute ;
    List<MId> mIds = new ArrayList<>();
    boolean isHide = false;
    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_work);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_work);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Intent intent = getIntent();
        mWorkUser = (MWorkUser) intent.getSerializableExtra("mWorkUser");
        mClient = (MClient) intent.getSerializableExtra("mClient");
        checkPermission(Manifest.permission.WRITE_CALENDAR);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

        categories.add(getString(R.string.meeting));//1
        categories.add(getString(R.string.meet));//2
        categories.add(getString(R.string.call));//3
        categories.add(getString(R.string.email));//7
        categories.add(getString(R.string.get_order));//4
        categories.add(getString(R.string.event));//9
        categories.add(getString(R.string.happy_birtday));//10
        categories.add(getString(R.string.get_free));//11
        categories.add(getString(R.string.delivery_do));//12
        categories.add(getString(R.string.order_get));//13
        categories.add(getString(R.string.other));//6

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        List<String> string = new ArrayList<String>();
        string.add(getString(R.string.no_alter));
        string.add(getString(R.string.in_time_alter));
        string.add(getString(R.string.after5_alter));
        string.add(getString(R.string.after15_alter));
        string.add(getString(R.string.after30_alter));
        string.add(getString(R.string.after60_alter));
        string.add(getString(R.string.after24_alter));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, string);
        spinner2.setAdapter(adapter);
        spinner2.setSelection(5);
        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        if(mWorkUser == null){
            mWorkUser = new MWorkUser();
            setClient();
            tvDate.setOnClickListener(this);
            tvUser.setOnClickListener(this);
            isHide = true;
            if(mClient.getClient_id()== 0){
                tvClientName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivityForResult(new Intent(mContext, ChooseClientsActivity.class), Constants.RESULT_CLIENT);
                    }
                });
            }
        }
        else{
            spinner.setEnabled(false);
            spinner2.setEnabled(false);
            isEdit = true;
            tvDate.setOnClickListener(null);
            tvUser.setOnClickListener(null);
            etContent.setFocusable(false);
            GetRetrofit().create(ServiceAPI.class)
                    .getWorkUser(preferences.getStringValue(Constants.TOKEN, "")
                            , preferences.getIntValue(Constants.USER_ID, 0)
                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                            , mWorkUser.getWork_user_id()
                    )
                    .enqueue(this);
        }

    }
    private void setClient(){

        if(mWorkUser.getClients().size() > 0){
            mClient = mWorkUser.getClients().get(0);
        }
        if(mClient == null){
            mClient = new MClient();
        }else if(mClient.getClient_id() > 0){
            tvClientName.setText(mClient.getClient_name());
            tvAddress.setText(mClient.getAddress());
            if(mClient.getAddress()!=null && !mClient.getAddress().isEmpty()){
                tvAddress.setVisibility(View.VISIBLE);
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done_edit, menu);
        if (isHide)
        {
            for (int i = 0; i < menu.size(); i++) {
                if(menu.getItem(i).getItemId() == R.id.edit )
                    menu.getItem(i).setVisible(false);
                if(menu.getItem(i).getItemId() == R.id.done)
                    menu.getItem(i).setVisible(true);
            }
        }else{
            if(mWorkUser.getUser_id() != preferences.getIntValue(Constants.USER_ID,0)){
                for (int i = 0; i < menu.size(); i++) {
                    if (menu.getItem(i).getItemId() == R.id.edit)
                        menu.getItem(i).setVisible(false);
                    if (menu.getItem(i).getItemId() == R.id.done)
                        menu.getItem(i).setVisible(false);
                }
            }else {
                for (int i = 0; i < menu.size(); i++) {
                    if (menu.getItem(i).getItemId() == R.id.edit)
                        menu.getItem(i).setVisible(true);
                    if (menu.getItem(i).getItemId() == R.id.done)
                        menu.getItem(i).setVisible(false);
                }
            }
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                if(tvDate.getText().toString().contains("/")) {
                    mWorkUser.getClients().add(mClient);
                    mWorkUser.setUser_id(preferences.getIntValue(Constants.USER_ID, 0));
                    //mWorkUser.getUsers().add(new MUser(preferences.getIntValue(Constants.USER_ID, 0)));
                    if(mIds.size() > 0) {
                        mWorkUser.setUsers(new ArrayList<MUser>());
                        for (MId mId : mIds) {
                            mWorkUser.getUsers().add(new MUser(mId.getId()));
                        }
                    }
                    mWorkUser.setContent_work(etContent.getText().toString());
                    switch (type + 1){
                        case 1:
                            type = 1;
                            break;
                        case 2:
                            type = 2;
                            break;
                        case 3:
                            type = 3;
                            break;
                        case 4:
                            type = 7;
                            break;
                        case 5:
                            type = 4;
                            break;
                        case 6:
                            type = 9;
                            break;
                        case 7:
                            type = 10;
                            break;
                        case 8:
                            type = 11;
                            break;
                        case 9:
                            type = 12;
                            break;
                        case 10:
                            type = 13;
                            break;
                        case 11:
                            type = 6;
                            break;
                        default:
                            type = 1;
                            break;
                    }
                    mWorkUser.setWork_user_type_id(type);
                    mWorkUser.setWork_begin_date(tvDate.getText().toString());
                    mWorkUser.setWork_end_date(tvDate.getText().toString());
                    mWorkUser.setLatitude(mLastLocation.getLatitude());
                    mWorkUser.setReminded_ahead_type_id(spinner2.getSelectedItemPosition());
                    mWorkUser.setLongitude(mLastLocation.getLongitude());
                    GetRetrofit().create(ServiceAPI.class)
                            .setWorkUser(preferences.getStringValue(Constants.TOKEN, "")
                                    , preferences.getIntValue(Constants.USER_ID, 0)
                                    , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                    , mClient.getClient_id()
                                    , mWorkUser)
                            .enqueue(new Callback<MAPIResponse<MWorkUser>>() {
                                @Override
                                public void onResponse(Call<MAPIResponse<MWorkUser>> call, Response<MAPIResponse<MWorkUser>> response) {
                                    LogUtils.api(TAG, call, (response.body()));
                                    box.hideAll();
                                    TokenUtils.checkToken(mContext,response.body().getErrors());
                                    if(response.body().isHasErrors()){
                                        if(isEdit){
                                            Utils.showError(coordinatorLayout, R.string.work_edit_fail);
                                        }else {
                                            Utils.showError(coordinatorLayout, R.string.work_fail);
                                        }

                                    }else {
                                        if (isEdit) {
                                            Utils.showDialogSuccess(mContext, R.string.work_edit_done);
                                        } else {
                                            Utils.showDialogSuccess(mContext, R.string.work_done);
                                        }

                                        String type = "";

                                        switch (mWorkUser.getWork_user_type_id()) {
                                            case 1:
                                                type = (mContext.getString(R.string.meeting));
                                                break;
                                            case 2:
                                                type = (mContext.getString(R.string.meet));
                                                break;
                                            case 3:
                                                type = (mContext.getString(R.string.call));
                                                break;
                                            case 4:
                                                type = (mContext.getString(R.string.get_order));
                                                break;
                                            case 6:
                                                type = (mContext.getString(R.string.other));
                                                break;
                                            case 7:
                                                type = (mContext.getString(R.string.email));
                                                break;

                                            case 9:
                                                type = (mContext.getString(R.string.event));
                                                break;
                                            case 10:
                                                type = (mContext.getString(R.string.happy_birtday));
                                                break;
                                            case 11:
                                                type = (mContext.getString(R.string.get_free));
                                                break;
                                            case 12:
                                                type = (mContext.getString(R.string.delivery));
                                                break;
                                            case 13:
                                                type = (mContext.getString(R.string.order_get));
                                                break;
                                            default:
                                                break;
                                        }

                                        CalendarContentResolver calendarContentResolver = new CalendarContentResolver(mContext);
                                        List<MCalendar> mCalendars = calendarContentResolver.getCalendars();
                                        for(MCalendar c : mCalendars){
                                            if(Utils.tryParse(c.getLocation()) == mWorkUser.getWork_user_id()){
                                                mWorkUser.setWork_user_id(c.getEvent_id());
                                                break;
                                            }
                                        }
                                        long date = Utils.convertDateTime(mWorkUser.getWork_begin_date(),"").getTime();
                                        switch (spinner2.getSelectedItemPosition()){
                                            case 2:
                                                date = date -  1000 *60 *5; // For next 1hr
                                                break;
                                            case 3:
                                                date = date -  1000 *60* 15; // For next 1hr
                                                break;
                                            case 4:
                                                date = date -  1000 *60* 30; // For next 1hr
                                                break;
                                            case 5:
                                                date = date -  1000 * 60 * 60; // For next 1hr
                                                break;
                                            case 6:
                                                date = date -  1000 * 60 * 60*24; // For next 1hr
                                                break;
                                            default:
                                                break;

                                        }
                                        if(spinner2.getSelectedItemPosition()!=0) {
//                                            if (Utils.updateEvent(WorkActivity.this,
//                                                    type + (mClient.getClient_id() > 0 ? " > " + mClient.getClient_name() : ""),
//                                                    mWorkUser.getContent_work(),
//                                                    date
//                                                    , mWorkUser.getWork_user_id()
//                                            ) == 0) {
                                                Utils.addEvent(WorkActivity.this,
                                                        type + (mClient.getClient_id() > 0 ? " > " + mClient.getClient_name() : ""),
                                                        mWorkUser.getContent_work(),
                                                        date
                                                        , mWorkUser.getWork_user_id()
                                                );
                                           // }
                                        }else{
                                            Utils.deleteEvent( mWorkUser.getWork_user_id(),mContext);
                                        }

                                    }
                                }

                                @Override
                                public void onFailure(Call<MAPIResponse<MWorkUser>> call, Throwable t) {
                                    LogUtils.d(TAG, "getUserActivities ", t.toString());
                                    box.hideAll();
                                    if(isEdit){
                                        Utils.showError(coordinatorLayout, R.string.work_edit_fail);
                                    }else {
                                        Utils.showError(coordinatorLayout, R.string.work_fail);
                                    }
                                }
                            });
                }else{
                    Utils.showError(coordinatorLayout, R.string.require_date);
                }
                return true;
            case  R.id.edit:
                isHide = true;
                spinner.setEnabled(true);
                spinner2.setEnabled(true);
                invalidateOptionsMenu();
                tvDate.setOnClickListener(this);
                tvUser.setOnClickListener(this);
                etContent.setFocusableInTouchMode(true);
                etContent.setFocusable(true);
                tvClientName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivityForResult(new Intent(mContext, ChooseClientsActivity.class), Constants.RESULT_CLIENT);
                    }
                });
                setClient();
               return  true;
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<MWorkUser>> call, Response<MAPIResponse<MWorkUser>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        if(!response.body().isHasErrors() && mWorkUser.getWork_user_id() == 0 ){
            finish();
        }
        mWorkUser = response.body().getResult();
        tvDate.setText(mWorkUser.getWork_begin_date());
        etContent.setText(mWorkUser.getContent_work());
        if(mWorkUser.getClients().size()>0){
            mClient = new MClient();
            mClient.setClient_id(mWorkUser.getClients().get(0).getClient_id());
            mClient.setClient_name(mWorkUser.getClients().get(0).getClient_name());
            mClient.setAddress(mWorkUser.getClients().get(0).getAddress());
        }
        setClient();
        type = mWorkUser.getWork_user_type_id();
        switch (type){
            case 1:
                type = 1;
                break;
            case 2:
                type = 2;
                break;
            case 3:
                type = 3;
                break;
            case 4:
                type = 5;
                break;
            case 7:
                type = 4;
                break;
            case 9:
                type = 6;
                break;
            case 10:
                type = 7;
                break;
            case 11:
                type = 8;
                break;
            case 12:
                type = 9;
                break;
            case 13:
                type = 10;
                break;
            case 6:
                type = 11;
                break;
            default:
                type = 1;
                break;

        }

        spinner.setSelection(type - 1);
        spinner2.setSelection(mWorkUser.getReminded_ahead_type_id());

    }

    @Override
    public void onFailure(Call<MAPIResponse<MWorkUser>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tvDate:
                Calendar calendar = Calendar.getInstance();
                datePickerDialog = DatePickerDialog.newInstance(
                        WorkActivity.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorApp));
                datePickerDialog.setCancelText(getString(R.string.no));
                datePickerDialog.setOkText(getString(R.string.yes));
                datePickerDialog.setTitle(getString(R.string.choose_date));
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                break;
            case R.id.tvUser:
                startActivityForResult(new Intent(mContext, UsersActivity.class), Constants.RESULT_USERS);

                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        type = position;


    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {

        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                WorkActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );

        tpd.setThemeDark(false);
        tpd.setAccentColor(getResources().getColor(R.color.colorApp));
        tpd.setTitle(getString(R.string.choose_time));
        tpd.setCancelText(getString(R.string.no));
        tpd.setOkText(getString(R.string.yes));
        tpd.show(getFragmentManager(), "DatePickerDialog");
        this.Month  = Month;
        this.Year =  Year;
        this.Day =Day;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Constants.RESULT_CLIENT)
        {
            mClient = (MClient) data.getSerializableExtra("mClient");
            tvClientName.setText(mClient.getClient_name());
            tvAddress.setText(mClient.getAddress());
            if (mClient.getAddress() != null && !mClient.getAddress().isEmpty()) {
                tvAddress.setVisibility(View.VISIBLE);
            }
            tvClientName.setOnClickListener(this);
        }
        if (resultCode == Constants.RESULT_USERS) {
            mIds = new ArrayList<>();
            mIds = (List<MId>) data.getSerializableExtra("mIds");
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        this.Hour  = hourOfDay;
        this.Minute =  minute;
        tvDate.setText((Month + 1) + "/" + Day + "/" + Year + " " + hourOfDay+":"+ minute);
    }
}
