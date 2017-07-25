package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.ColorAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.DividerItemDecoration;
import anhpha.clientfirst.crm.customs.RecyclerTouchListener;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MColor;
import anhpha.clientfirst.crm.model.MLabel;
import anhpha.clientfirst.crm.model.MRequestBody;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import anhpha.clientfirst.crm.utils.DynamicBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LabelActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<List<MColor>>>, View.OnClickListener  {

    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.etName)
    EditText etName;
    @Bind(R.id.btDelete)
    Button btDelete;
    @Bind(R.id.include)
    Toolbar toolbar;
    List<MColor> mColors  = new ArrayList<>();
    MLabel mLabel = new MLabel();
    MClient mClient = new MClient();
    ColorAdapter activityAdapter;
    Preferences preferences;
    int color_id;
    String hex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_label);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar  actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_label);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Intent intent = getIntent();
        mLabel = (MLabel) intent.getSerializableExtra("mLabel");
        mClient = (MClient) intent.getSerializableExtra("mClient");
        if(mLabel == null)
            mLabel = new MLabel();
        if(mLabel.getClient_label_id() > 0){
            etName.setText(mLabel.getClient_label_name());
        }
        btDelete.setOnClickListener(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvActivities.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvActivities, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                for (MColor c : mColors) {
                    c.setIs_select(false);
                }
                mColors.get(position).setIs_select(true);
                color_id = mColors.get(position).getColor_id();
                hex = mColors.get(position).getHex();
                activityAdapter.notifyDataSetChanged();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        activityAdapter = new ColorAdapter(mContext,mColors);
        rvActivities.setAdapter(activityAdapter);

        MRequestBody mRequestBody = new MRequestBody();
        mRequestBody.setFrom_date(Utils.getFromDate());
        mRequestBody.setTo_date(Utils.getToDate());
        GetRetrofit().create(ServiceAPI.class)
                .getColors(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                )
                .enqueue(this);
        box.showLoadingLayout();

        LogUtils.d(TAG, "getUserActivities ", "start");
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
                mLabel.setColor_id(color_id);
                mLabel.setHex(hex);
                mLabel.setClient_label_name(etName.getText().toString());
                GetRetrofit().create(ServiceAPI.class)
                        .setClientLabel(preferences.getStringValue(Constants.TOKEN, "")
                                , preferences.getIntValue(Constants.USER_ID, 0)
                                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                , 1
                                , mLabel)
                        .enqueue(new Callback<MAPIResponse<MLabel>>() {
                            @Override
                            public void onResponse(Call<MAPIResponse<MLabel>> call, Response<MAPIResponse<MLabel>> response) {
                                LogUtils.api(TAG, call, (response.body()));
                                box.hideAll();
                                TokenUtils.checkToken(mContext,response.body().getErrors());
                                finish();
                            }
                            @Override
                            public void onFailure(Call<MAPIResponse<MLabel>> call, Throwable t) {
                                LogUtils.d(TAG, "getUserActivities ", t.toString());
                                box.hideAll();
                            }
                        });
                box.showLoadingLayout();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<List<MColor>>> call, Response<MAPIResponse<List<MColor>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        mColors = response.body().getResult();
        for (MColor c : mColors){
            if(c.getColor_id() == mLabel.getColor_id()){
                c.setIs_select(true);
                break;
            }
        }
        activityAdapter.setActivityItemList(mColors);
        activityAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MColor>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
        GetRetrofit().create(ServiceAPI.class)
                .setClientLabel(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , 3
                        , mLabel)
                .enqueue(new Callback<MAPIResponse<MLabel>>() {
                    @Override
                    public void onResponse(Call<MAPIResponse<MLabel>> call, Response<MAPIResponse<MLabel>> response) {
                        LogUtils.api(TAG, call, (response.body()));
                        box.hideAll();
                        TokenUtils.checkToken(mContext,response.body().getErrors());
                        finish();
                    }

                    @Override
                    public void onFailure(Call<MAPIResponse<MLabel>> call, Throwable t) {
                        LogUtils.d(TAG, "getUserActivities ", t.toString());
                        box.hideAll();
                    }
                });
        box.showLoadingLayout();
    }
}
