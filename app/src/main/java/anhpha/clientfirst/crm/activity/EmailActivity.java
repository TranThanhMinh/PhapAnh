package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MEmail;
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

public class EmailActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<MEmail>>, View.OnClickListener  {

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

    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_email);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_email);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Intent intent = getIntent();
        mEmail = (MEmail) intent.getSerializableExtra("mEmail");
        mClient = (MClient) intent.getSerializableExtra("mClient");
        tvClientName.setText(mClient.getClient_name());
        tvAddress.setText(mClient.getAddress());
        if(mClient.getAddress()!=null && !mClient.getAddress().isEmpty()){
            tvAddress.setVisibility(View.VISIBLE);
        }

        if(mEmail == null)
            mEmail = new MEmail();
        if(mEmail.getEmail_user_id() > 0){
            etContent.setText(mEmail.getContent_email());
            etContent.setFocusable(false);
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);
        if(mEmail.getEmail_user_id()>0){
            for (int i = 0; i < menu.size(); i++) {
                if(menu.getItem(i).getItemId() == R.id.done)
                    menu.getItem(i).setVisible(false);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                mEmail.setClient_id(mClient.getClient_id());
                mEmail.setUser_id(preferences.getIntValue(Constants.USER_ID,0));
                mEmail.setContent_email(etContent.getText().toString());
                mEmail.setLatitude(mLastLocation.getLatitude());
                mEmail.setLongitude(mLastLocation.getLongitude());
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

    @Override
    public void onResponse(Call<MAPIResponse<MEmail>> call, Response<MAPIResponse<MEmail>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        if(response.body().isHasErrors()){
            Utils.showError(coordinatorLayout, R.string.email_fail);
        }else {
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
