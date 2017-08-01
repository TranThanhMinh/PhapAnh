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

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.ChooseClientTypeAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.DividerItemDecoration;
import anhpha.clientfirst.crm.customs.RecyclerTouchListener;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClientType;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseClientTypeActivity extends BaseAppCompatActivity implements  RecyclerTouchListener.ClickListener,Callback<MAPIResponse<List<MClientType>>>, View.OnClickListener  {

    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    ChooseClientTypeAdapter activityAdapter;
    List<MClientType> mClientTypes = new ArrayList<>();
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_choose_clients);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.type_client);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvActivities.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvActivities, this));

        mClientTypes = (List<MClientType>) getIntent().getSerializableExtra("mClientTypes");

        if(mClientTypes.isEmpty()){
            mClientTypes = new ArrayList<>();
            GetRetrofit().create(ServiceAPI.class)
                    .getClientTypes(preferences.getStringValue(Constants.TOKEN, "")
                            , preferences.getIntValue(Constants.USER_ID, 0)
                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                    )
                    .enqueue(this);
            setProgressBarIndeterminateVisibility(true);
            setProgressBarVisibility(true);
            box.showLoadingLayout();
            LogUtils.d(TAG, "getUserActivities ", "start");
        }

        activityAdapter = new ChooseClientTypeAdapter(mContext,mClientTypes);
        rvActivities.setAdapter(activityAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                rvActivities.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.d(TAG, "getUserActivities ", new Gson().toJson(mClientTypes));
                        setResult(Constants.RESULT_TYPE, new Intent().putExtra("mClientTypes", (Serializable) mClientTypes));
                        finish();
                    }
                },500);

                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<List<MClientType>>> call, Response<MAPIResponse<List<MClientType>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        mClientTypes = response.body().getResult();
        activityAdapter.setActivityItemList(mClientTypes);
        activityAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MClientType>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onClick(View view, int position) {
        mClientTypes.get(position).setIs_select(!mClientTypes.get(position).is_select());
        activityAdapter.notifyDataSetChanged();

        LogUtils.d(TAG, "getUserActivities ", new Gson().toJson(mClientTypes));
    }

    @Override
    public void onLongClick(View view, int position) {

    }


}
