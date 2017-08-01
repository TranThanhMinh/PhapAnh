package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.CommunicationAdapter;
import anhpha.clientfirst.crm.adapter.EventAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MCommunication;
import anhpha.clientfirst.crm.model.MEvent;
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

public class CommunicationsActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<List<MCommunication>>>, View.OnClickListener  {
    @Bind(R.id.rvActivities)
    ListView rvActivities;
    @Bind(R.id.rv2)
    ListView rv2;
    @Bind(R.id.include)
    Toolbar toolbar;

    List<MCommunication> mCommunications;
    List<MEvent> mEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_communications);
        ButterKnife.bind(this);
        Preferences preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_communication);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvActivities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext,CommunicationActivity.class).putExtra("mCommunication",mCommunications.get(position)));

            }
        });
        rv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext,EventViewActivity.class).putExtra("mEvent",mEvents.get(position)));

            }
        });


        GetRetrofit().create(ServiceAPI.class)
                .getCommunication(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                )
                .enqueue(this);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        box.showLoadingLayout();
        GetRetrofit().create(ServiceAPI.class)
                .getEvents(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                )
                .enqueue(new Callback<MAPIResponse<List<MEvent>>>() {
                    @Override
                    public void onResponse(Call<MAPIResponse<List<MEvent>>> call, Response<MAPIResponse<List<MEvent>>> response) {
                        LogUtils.api(TAG, call, (response.body()));
                        box.hideAll();
                        TokenUtils.checkToken(mContext,response.body().getErrors());
                        mEvents = response.body().getResult();

                        EventAdapter activityAdapter = new EventAdapter(mEvents,mContext);
                        rv2.setAdapter(activityAdapter);
                        rv2.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Utils.setListViewHeightBasedOnChildren(rvActivities);
                            }
                        }, 500);
                        Utils.setListViewHeightBasedOnChildren(rvActivities);
                        activityAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<MAPIResponse<List<MEvent>>> call, Throwable t) {
                        LogUtils.d(TAG, "getUserActivities ", t.toString());
                        box.hideAll();
                    }
                });
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        LogUtils.d(TAG, "getUserActivities ", "start");
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

    @Override
    public void onResponse(Call<MAPIResponse<List<MCommunication>>> call, Response<MAPIResponse<List<MCommunication>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        mCommunications = response.body().getResult();

        CommunicationAdapter activityAdapter = new CommunicationAdapter(mCommunications,mContext);
        rvActivities.setAdapter(activityAdapter);
        rvActivities.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.setListViewHeightBasedOnChildren(rvActivities);
            }
        }, 500);
        Utils.setListViewHeightBasedOnChildren(rvActivities);
        activityAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MCommunication>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
    }
}
