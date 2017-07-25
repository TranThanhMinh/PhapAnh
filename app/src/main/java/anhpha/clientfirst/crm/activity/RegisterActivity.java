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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MDistrict;
import anhpha.clientfirst.crm.model.MPartner;
import anhpha.clientfirst.crm.model.MProvince;
import anhpha.clientfirst.crm.model.MWard;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.StringUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import anhpha.clientfirst.crm.utils.DynamicBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<MPartner>>, View.OnClickListener {


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    //delivery
    @Bind(R.id.spnProvince)
    Spinner spProvince;
    @Bind(R.id.spnDistrict)
    Spinner spDistrict;
    @Bind(R.id.spnWard)
    Spinner spWard;

    List<MProvince> mProvinces = new ArrayList<>();
    List<MDistrict> mDistricts = new ArrayList<>();
    List<MWard> mWards = new ArrayList<>();

    String province_code;
    String district_code;
    String ward_code;

    String province_name;
    String district_name;
    String ward_name;

    int iProvince =49;
    int iDistrict =0;
    int iWard =0;

    List<MDistrict> list = new ArrayList<>();
    List<MWard> list2= new ArrayList<>();

    @Bind(R.id.etName)
    EditText etName;
    @Bind(R.id.etContact)
    EditText etContact;
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etPasswordConfirm)
    EditText etRePassword;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etAddress)
    EditText etAddress;

    DynamicBox box;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    Preferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        requestWindowFeature(Window.FEATURE_PROGRESS);

        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_register);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_register);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        findViewById(R.id.cbxPassword).setOnClickListener(this);
        findViewById(R.id.cbxRePassword).setOnClickListener(this);
        prefs = new Preferences(mContext);
        try {
            mProvinces = Utils.getProvince(mContext);
            mDistricts = Utils.getDistrict(mContext);
            mWards = Utils.getWard(mContext);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ArrayAdapter adapter2 = new ArrayAdapter<>(this, R.layout.simple_spinner_item, mProvinces);

        spProvince.setAdapter(adapter2);
        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                list = new ArrayList<>();
                province_code = mProvinces.get(i).getProvince_code();
                province_name = mProvinces.get(i).getProvince_name();
                for (MDistrict d : mDistricts) {
                    if (d.getProvince_code().equals(province_code)) {
                        list.add(d);
                    }
                }
                ArrayAdapter adapter3 = new ArrayAdapter<MDistrict>(mContext, R.layout.simple_spinner_item, list);
                spDistrict.setAdapter(adapter3);
                spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        list2 = new ArrayList<>();
                        district_code = list.get(i).getDistrict_code();
                        district_name = list.get(i).getDistrict_name();
                        for (MWard d : mWards) {
                            if (d.getDistrict_code().equals(district_code)) {
                                list2.add(d);
                            }
                        }
                        ArrayAdapter adapter4 = new ArrayAdapter<MWard>(mContext, R.layout.simple_spinner_item, list2);
                        spWard.setAdapter(adapter4);
                        spWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                ward_code = list2.get(i).getWard_code();
                                ward_name = list2.get(i).getWard_name();

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
        spProvince.setSelection(iProvince);
        spDistrict.setSelection(iDistrict);
        spWard.setSelection(iWard);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.cbxPassword:
                showHide(((CheckBox) view).isChecked());
                break;
            case R.id.cbxRePassword:
                showHideRe(((CheckBox) view).isChecked());
                break;
        }
    }

    void showHide(boolean checked) {
        if (checked) {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    void showHideRe(boolean checked) {
        if (checked) {
            etRePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etRePassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
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
                login();
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

    void login() {

        String password = etPassword.getText().toString();
        String re_pass = etRePassword.getText().toString();

        if (StringUtils.isEmpty(password)) {
            Utils.showError(coordinatorLayout, R.string.require_password);
            return;
        }
        if (StringUtils.isEmpty(re_pass)) {
            Utils.showError(coordinatorLayout, R.string.require_re_password);
            return;
        }
        if (password.length() < 6) {
            Utils.showError(coordinatorLayout, R.string.require_password_length);
            return;
        }

        if (!re_pass.equals(password)) {
            Utils.showError(coordinatorLayout, R.string.re_password_not_match);
            return;
        }

        MPartner mPartner = new MPartner();
        mPartner.setAddress(etAddress.getText().toString());
        mPartner.setContact_name(etContact.getText().toString());
        mPartner.setDistrict_name(district_name);
        mPartner.setEmail(etEmail.getText().toString());
        mPartner.setMobile(etPhone.getText().toString());
        mPartner.setPassword(etPassword.getText().toString());
        mPartner.setProvince_name(province_name);
        mPartner.setWard_name(ward_name);
        mPartner.setPartner_name(etName.getText().toString());


        GetRetrofit().create(ServiceAPI.class)
                .setPartner(mPartner)
                .enqueue(this);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        box.showLoadingLayout();

        LogUtils.d(TAG, "getUserLogin ", "start");

    }

    @Override
    public void onResponse(Call<MAPIResponse<MPartner>> call, Response<MAPIResponse<MPartner>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        if (!response.body().isHasErrors()) {
            Utils.showDialogSuccess(mContext, R.string.register_done);
        } else {
            Utils.showError(coordinatorLayout, R.string.register_faild);
        }

    }

    @Override
    public void onFailure(Call<MAPIResponse<MPartner>> call, Throwable t) {
        LogUtils.d(TAG, "getUserLogin ", t.toString());
        box.hideAll();
        Utils.showError(coordinatorLayout, R.string.change_password_fail);
    }
}
