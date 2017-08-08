package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.TrackingValueDefautAdapter;
import anhpha.clientfirst.crm.adapter.ValueDefautAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MEmail;
import anhpha.clientfirst.crm.model.Tracking_value_defaults;
import anhpha.clientfirst.crm.model.UserEmail;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmailActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<MEmail>>, View.OnClickListener {

    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.tvClientName)
    TextView tvClientName;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    MEmail mEmail = new MEmail();
    MClient mClient = new MClient();
    Retrofit retrofit;
    private RecyclerView lvTracking;
    Preferences preferences;
    private List<Tracking_value_defaults> listTracking = new ArrayList<>();
    private List<Tracking_value_defaults> listTracking_userEmail = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_email);
        lvTracking = (RecyclerView) findViewById(R.id.lvTracking);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_email);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvTracking.setHasFixedSize(true);
        lvTracking.setLayoutManager(manager);
        Intent intent = getIntent();
        mEmail = (MEmail) intent.getSerializableExtra("mEmail");
        mClient = (MClient) intent.getSerializableExtra("mClient");
        tvClientName.setText(mClient.getClient_name());
        tvAddress.setText(mClient.getAddress());
        if (mClient.getAddress() != null && !mClient.getAddress().isEmpty()) {
            tvAddress.setVisibility(View.VISIBLE);
        }
        retrofit = getConnect();

        if (mEmail == null) {
            mEmail = new MEmail();
            getTracking_value_default();
        }
        if (mEmail.getEmail_user_id() > 0) {
            etContent.setText(mEmail.getContent_email());
            etContent.setFocusable(false);
            getUserEmail();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);
        if (mEmail.getEmail_user_id() > 0) {
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.done)
                    menu.getItem(i).setVisible(false);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                List<Tracking_value_defaults> list = new ArrayList<>();
                for (Tracking_value_defaults tracking : listTracking) {
                    if (tracking.isTracking()) {
                        Tracking_value_defaults track = new Tracking_value_defaults();
                        track.setTracking_value_default_id(tracking.getTracking_value_default_id());
                        track.setCreate_date(tracking.getCreate_date());
                        track.setTracking_value_default_content(tracking.getTracking_value_default_content());
                        track.setStatus_id(tracking.getStatus_id());
                        track.setTracking_user_type_id(tracking.getTracking_user_type_id());
                        track.setUser_id(tracking.getUser_id());
                        track.setPartner_id(tracking.getPartner_id());
                        Log.d("tracking", tracking.getTracking_value_default_content());
                        list.add(track);
                        tracking.setTracking(false);
                    }
                }
                mEmail.setClient_id(mClient.getClient_id());
                mEmail.setUser_id(preferences.getIntValue(Constants.USER_ID, 0));
                mEmail.setContent_email(etContent.getText().toString());
                mEmail.setLatitude(mLastLocation.getLatitude());
                mEmail.setLongitude(mLastLocation.getLongitude());
                mEmail.setValues_default(list);
                GetRetrofit().create(ServiceAPI.class)
                        .setUserEmail(preferences.getStringValue(Constants.TOKEN, "")
                                , preferences.getIntValue(Constants.USER_ID, 0)
                                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                , mClient.getClient_id()
                                , mEmail)
                        .enqueue(this);
                box.showLoadingLayout();
                LogUtils.d(TAG, "getUserActivities ", "start");
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_user)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public void getTracking_value_default() {
        ServiceAPI apiTracking = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<List<Tracking_value_defaults>>> tracking_value_defaults = apiTracking.getTracking_value_defaults(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), 7, preferences.getStringValue(Constants.TOKEN, ""));
        tracking_value_defaults.enqueue(new Callback<MAPIResponse<List<Tracking_value_defaults>>>() {
            @Override
            public void onResponse(Call<MAPIResponse<List<Tracking_value_defaults>>> call, Response<MAPIResponse<List<Tracking_value_defaults>>> response) {
                listTracking = response.body().getResult();
                LogUtils.api("", call, response);
                TrackingValueDefautAdapter adapte = new TrackingValueDefautAdapter(EmailActivity.this, listTracking);
                lvTracking.setAdapter(adapte);
            }

            @Override
            public void onFailure(Call<MAPIResponse<List<Tracking_value_defaults>>> call, Throwable t) {
                LogUtils.api("", call, t.toString());
            }
        });
    }

    public void getUserEmail() {
        ServiceAPI apiTracking = retrofit.create(ServiceAPI.class);
        Call<MAPIResponse<UserEmail>> user_email = apiTracking.get_user_email(preferences.getIntValue(Constants.USER_ID, 0),mEmail.getEmail_user_id(), preferences.getStringValue(Constants.TOKEN, ""), preferences.getIntValue(Constants.PARTNER_ID, 0));
        user_email.enqueue(new Callback<MAPIResponse<UserEmail>>() {
            @Override
            public void onResponse(Call<MAPIResponse<UserEmail>> call, Response<MAPIResponse<UserEmail>> response) {
                LogUtils.api("", call, response);
                listTracking_userEmail =response.body().getResult().getValuesDefault();
                ValueDefautAdapter adapte = new ValueDefautAdapter(EmailActivity.this, listTracking_userEmail);
                lvTracking.setAdapter(adapte);
            }

            @Override
            public void onFailure(Call<MAPIResponse<UserEmail>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResponse(Call<MAPIResponse<MEmail>> call, Response<MAPIResponse<MEmail>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext, response.body().getErrors());
        if (response.body().isHasErrors()) {
            Utils.showError(coordinatorLayout, R.string.email_fail);
        } else {
            Utils.showDialogSuccess(mContext, R.string.email_done);
        }

    }

    @Override
    public void onFailure(Call<MAPIResponse<MEmail>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
        Utils.showError(coordinatorLayout, R.string.email_fail);

    }

    @Override
    public void onClick(View view) {
    }
}
