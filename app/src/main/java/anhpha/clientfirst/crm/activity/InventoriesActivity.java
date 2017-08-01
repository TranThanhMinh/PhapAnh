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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.InventoryAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.DividerItemDecoration;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.model.MRequestBody;
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

public class InventoriesActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<List<MContract>>>, View.OnClickListener  {
    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.tvAmount)
    TextView tvAmount;
    List<MId> mIds = new ArrayList<>();
    List<MContract> MContracts;
    Preferences preferences;
    MOrder mOrder;
    boolean is_edit =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_inventories);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_inventory);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }

    @Override
    protected void onResume() {
        super.onResume();
        MRequestBody mRequestBody = new MRequestBody();
        mRequestBody.setUser_ids(mIds);
        GetRetrofit().create(ServiceAPI.class)
                .getInventoryContractClients(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , mRequestBody
                )
                .enqueue(this);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        box.showLoadingLayout();

        LogUtils.d(TAG, "getUserActivities ", "start");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user:
                startActivityForResult(new Intent(mContext, UsersActivity.class), Constants.RESULT_USERS);
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (resultCode == Constants.RESULT_USERS) {
            mIds = new ArrayList<>();
            mIds = (List<MId>) data.getSerializableExtra("mIds");
        }

    }
    @Override
    public void onResponse(Call<MAPIResponse<List<MContract>>> call, Response<MAPIResponse<List<MContract>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        MContracts = response.body().getResult();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        InventoryAdapter activityAdapter = new InventoryAdapter(mContext,MContracts);
        rvActivities.setAdapter(activityAdapter);
        activityAdapter.notifyDataSetChanged();
        double amount = 0;
        for(MContract p: MContracts){
            amount += Utils.getPriceContract(p,mContext).getAmount_price();
        }
        tvAmount.setText(Utils.formatCurrency(amount));

    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MContract>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {

    }

}
