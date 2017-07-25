package anhpha.clientfirst.crm.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MUser;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.AuthUtil;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.StringUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import anhpha.clientfirst.crm.utils.DynamicBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangPassActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<MUser>>, View.OnClickListener {

    //is variable
    private GoogleCloudMessaging gcm;
    private String regId;

    @Bind(R.id.etOldPassword)
    EditText etOldPassword;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etRePassword)
    EditText etRePassword;
    @Bind(R.id.include)
    Toolbar toolbar;

    DynamicBox box;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    Preferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_PROGRESS);

        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_change_pass);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_change_pass);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        findViewById(R.id.btnLogin).setOnClickListener(this);

        findViewById(R.id.cbxPassword).setOnClickListener(this);
        findViewById(R.id.cbxOldPassword).setOnClickListener(this);
        findViewById(R.id.cbxRePassword).setOnClickListener(this);
        prefs = new Preferences(mContext);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnLogin:
                login();
                break;

            case R.id.tvForgotPassword:
                //forgotPassword();
                break;
            case R.id.cbxOldPassword:
                showHideOld(((CheckBox) view).isChecked());
                break;
            case R.id.cbxPassword:
                showHide(((CheckBox) view).isChecked());
                break;
            case R.id.cbxRePassword:
                showHideRe(((CheckBox) view).isChecked());
                break;
        }
    }

    private void showHide(boolean checked) {
        if (checked) {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
    private void showHideOld(boolean checked) {
        if (checked) {
            etOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
    private void showHideRe(boolean checked) {
        if (checked) {
            etRePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etRePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_null, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:

                return true;

            case R.id.user:

                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void login() {

        String old_pass = etOldPassword.getText().toString();
        String password = etPassword.getText().toString();
        String re_pass = etRePassword.getText().toString();
        int user_id = prefs.getIntValue(Constants.USER_ID, 0);
        String token = prefs.getStringValue(Constants.TOKEN, "");
        String email = prefs.getStringValue(Constants.USER_EMAIL, "");
        String auth = AuthUtil.getBasicAuth(email + "", password);

        if (StringUtils.isEmpty(old_pass)) {
            Utils.showError(coordinatorLayout, R.string.require_old_password);
            return;
        }

        if (StringUtils.isEmpty(password)) {
            Utils.showError(coordinatorLayout, R.string.require_password);
            return;
        }
        if (StringUtils.isEmpty(re_pass)) {
            Utils.showError(coordinatorLayout, R.string.require_re_password);
            return;
        }
        if(password.length() < 6){
            Utils.showError(coordinatorLayout, R.string.require_password_length);
            return;
        }

        if(!re_pass.equals(password)){
            Utils.showError(coordinatorLayout, R.string.re_password_not_match);
            return;
        }

        GetRetrofit().create(ServiceAPI.class)
                .setUserPassword(auth, old_pass, user_id,token)
                .enqueue(this);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        box.showLoadingLayout();

        LogUtils.d(TAG, "getUserLogin ", "start");

    }

    @Override
    public void onResponse(Call<MAPIResponse<MUser>> call, Response<MAPIResponse<MUser>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        if(!response.body().isHasErrors()) {
            Utils.showDialogSuccess(mContext,R.string.change_password_done);
        }else{
            Utils.showError(coordinatorLayout, R.string.change_password_fail);
        }

    }

    @Override
    public void onFailure(Call<MAPIResponse<MUser>> call, Throwable t) {
        LogUtils.d(TAG, "getUserLogin ", t.toString());
        box.hideAll();
        Utils.showError(coordinatorLayout, R.string.change_password_fail);
    }
}
