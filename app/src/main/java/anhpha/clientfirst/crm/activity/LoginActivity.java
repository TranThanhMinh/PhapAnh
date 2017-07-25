package anhpha.clientfirst.crm.activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MUser;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.AuthUtil;
import anhpha.clientfirst.crm.utils.Config;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.StringUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import anhpha.clientfirst.crm.utils.DynamicBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<MUser>>, View.OnClickListener {

    //is variable
    private GoogleCloudMessaging gcm;
    private String regId;

    @Bind(R.id.etPhoneEmail)
    EditText etPhoneEmail;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.ivLogoText)
    ImageView ivLogoText;
    DynamicBox box;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    String token = "";
    Preferences prefs;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_PROGRESS);

        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_login);
        ButterKnife.bind(this);
        Picasso.with(getApplicationContext()).load(R.mipmap.ic_crm_69).resize(500,100).centerInside().error(R.drawable.no_img_big).skipMemoryCache().into(ivLogoText);

        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.btRegister).setOnClickListener(this);
        findViewById(R.id.tvForgotPassword).setOnClickListener(this);
        findViewById(R.id.cbxPassword).setOnClickListener(this);
        prefs = new Preferences(mContext);
        if(prefs.getIntValue(Constants.USER_ID, 0) > 0){
            box.showLoadingLayout();
            etPhoneEmail.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 500);
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnLogin:
                login();
                break;
            case R.id.btRegister:
                startActivity(new Intent(mContext,RegisterActivity.class));
               // Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://salesrep.fieldworkdms.com/request-demo"));
               // startActivity(browserIntent);
                break;
            case R.id.tvForgotPassword:
                startActivity(new Intent(mContext, ChangPassByEmailActivity.class));
                break;

            case R.id.cbxPassword:
                showHide(((CheckBox) view).isChecked());
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

    private void login() {

        String username = etPhoneEmail.getText().toString();
        String password = etPassword.getText().toString();
        String auth = AuthUtil.getBasicAuth(username, password);

        if (StringUtils.isEmpty(username)) {
            Utils.showError(coordinatorLayout, R.string.require_email);

            return;
        }

        if (StringUtils.isEmpty(password)) {
            Utils.showError(coordinatorLayout, R.string.require_password);
            return;
        }
        try {
            token = FirebaseInstanceId.getInstance().getToken().toString();
        }catch (Exception e){}
        Log.i(TAG, "FCM Registration Token: " + token);

        GetRetrofit().create(ServiceAPI.class)
                .getUserLogin(auth,token,1)
                .enqueue(this);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        box.showLoadingLayout();

        LogUtils.d(TAG, "getUserLogin ", "start");

    }

    @Override
    public void onResponse(Call<MAPIResponse<MUser>> call, Response<MAPIResponse<MUser>> response) {
        LogUtils.api(TAG,call,response.body());
        MUser mUser = response.body().getResult();
        if(!response.body().isHasErrors() && mUser.getUser_id() > 0) {
            prefs.putBooleanValue(Constants.USER_LOGGED_IN, true);
            prefs.putIntValue(Constants.USER_ID, mUser.getUser_id());
            prefs.putStringValue(Constants.USER_NAME, mUser.getUser_name());
            prefs.putStringValue(Constants.USER_EMAIL, mUser.getEmail());
            prefs.putIntValue(Constants.PARTNER_ID, mUser.getPartner_id());
            prefs.putStringValue(Constants.PARTNER_NAME, mUser.getPartner_name());
            prefs.putStringValue(Constants.TOKEN, mUser.getToken());
           // prefs.putStringValue(Constants.TOKEN, "thuonghuyen");

            Intent intent = new Intent(mContext,MainActivity.class);
            startActivity(intent);
            box.hideAll();
            finish();

        }else{
            box.hideAll();
            Utils.showError(coordinatorLayout, R.string.login_fail);
        }
    }

    @Override
    public void onFailure(Call<MAPIResponse<MUser>> call, Throwable t) {
        LogUtils.d(TAG, "getUserLogin ", t.toString());
        box.hideAll();
        Utils.showError(coordinatorLayout, R.string.login_fail);
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        token = FirebaseInstanceId.getInstance().getToken();
        //Utils.showDialogSuccess(mContext,("Firebase Reg Id: " + regId) );

    }

}
