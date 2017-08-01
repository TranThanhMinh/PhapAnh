package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.EventAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
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

public class EventsClientActivity extends BaseAppCompatActivity implements  View.OnClickListener  {

    @Bind(R.id.rv2)
    ListView rv2;
    @Bind(R.id.tvClientName)
    TextView tvClientName;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    MClient mClient = new MClient();
    List<MEvent> mEvents;
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_events_client);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_event);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Intent intent = getIntent();
        mClient = (MClient) intent.getSerializableExtra("mClient");
        tvClientName.setText(mClient.getClient_name());
        tvAddress.setText(mClient.getAddress());
        if(mClient.getAddress()!=null && !mClient.getAddress().isEmpty()){
            tvAddress.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(mContext,EventClientActivity.class).putExtra("mEvent",mEvents.get(position)).putExtra("mClient",mClient));

            }
        });

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
                                Utils.setListViewHeightBasedOnChildren(rv2);
                            }
                        }, 500);
                        Utils.setListViewHeightBasedOnChildren(rv2);
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
            case R.id.done:

                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View view) {
    }
}
