package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.ChooseClientAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.DividerItemDecoration;
import anhpha.clientfirst.crm.customs.RecyclerTouchListener;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
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

public class ChooseClientsActivity extends BaseAppCompatActivity implements  RecyclerTouchListener.ClickListener,Callback<MAPIResponse<List<MClient>>>, TextWatcher, View.OnClickListener  {

    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.ibClose)
    ImageButton ibClose;
    @Bind(R.id.layout_find)
    LinearLayout layout_find;
    @Bind(R.id.etText)
    EditText etText;
    ChooseClientAdapter activityAdapter;
    List<MClient> mClients = new ArrayList<>();
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_choose_clients);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_client);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        ibClose.setOnClickListener(this);
        etText.addTextChangedListener(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvActivities.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvActivities, this));

        activityAdapter = new ChooseClientAdapter(mContext,mClients);
        rvActivities.setAdapter(activityAdapter);
        box.showLoadingLayout();
        LogUtils.d(TAG, "getUserActivities ", "start");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_find, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRetrofit().create(ServiceAPI.class)
                .getClients(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        ,"1"
                )
                .enqueue(this);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.find:
                layout_find.setVisibility(View.VISIBLE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<List<MClient>>> call, Response<MAPIResponse<List<MClient>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        mClients = response.body().getResult();
        activityAdapter.setActivityItemList(mClients);
        activityAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MClient>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibClose:
            layout_find.setVisibility(View.GONE);
                etText.setText("");
            break;
            default:
            break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        List<MClient> list = new ArrayList<>();
        String x = Utils.deAccent(editable.toString().toLowerCase());
        for (MClient mClient: mClients){
            String a = Utils.deAccent(mClient.getClient_name().toLowerCase());
            if(a.indexOf(x)>=0){
                list.add(mClient);
            }
        }
        activityAdapter.setActivityItemList(list);
        activityAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view, int position) {
        setResult(Constants.RESULT_CLIENT, new Intent().putExtra("mClient",activityAdapter.get(position)));
        finish();
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
