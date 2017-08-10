package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_History_orders;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.History_order;
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.model.MResult_order_history;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MinhTran on 7/12/2017.
 */

public class History_orders_activity extends BaseAppCompatActivity implements View.OnClickListener,adapter_History_orders.clickEdit{
    private RecyclerView lvHistory_order;
    private Retrofit retrofit;
    private adapter_History_orders adapter_history_orders;
    private ImageView imBack;
    private Toolbar toolbar;
    private  Preferences preferences;
    private MOrder mOrder = new MOrder();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_histor_orders);
        box = new DynamicBox(this, R.layout.activity_histor_orders);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        mOrder = (MOrder) getIntent().getSerializableExtra("mOrder");
        preferences = new Preferences(mContext);
        if (toolbar != null) {

            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(R.string.title_activity_activity_user);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

            actionBar.setTitle(R.string.srtHistory_order);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        lvHistory_order = (RecyclerView) findViewById(R.id.lvHistory_order);
        //imBack = (ImageView) findViewById(R.id.imBack);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL
                , false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lvHistory_order.setHasFixedSize(true);
        lvHistory_order.setLayoutManager(layoutManager);
        retrofit = func_Connect();
       // imBack.setOnClickListener(this);
    }
    public Retrofit func_Connect(){
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl(Url.URL_exchange)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
    public void getHistory_order(){
        ServiceAPI history_orders = retrofit.create(ServiceAPI.class);
        Call<MResult_order_history> call = history_orders.getHistory_order(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), preferences.getStringValue(Constants.TOKEN, ""),mOrder.getOrder_contract_id());
        call.enqueue(new Callback<MResult_order_history>() {
            @Override
            public void onResponse(Call<MResult_order_history> call, Response<MResult_order_history> response) {
                //TokenUtils.checkToken(mContext,response.body().getErrors());
                LogUtils.api("",call,response);
                box.hideAll();
                List<History_order> list = response.body().getOrder_history();
                adapter_history_orders = new adapter_History_orders(History_orders_activity.this,list,History_orders_activity.this);
                lvHistory_order.setAdapter(adapter_history_orders);
            }
            @Override
            public void onFailure(Call<MResult_order_history> call, Throwable t) {
                LogUtils.api("",call,t.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        box.showLoadingLayout();
        getHistory_order();
    }

    @Override
    public void onClick(View view) {
      if(view == imBack){
          finish();
      }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();
        if (id == R.id.actionHistory_contract) {
            Intent intent = new Intent(History_orders_activity.this,History_contract_activity.class);
            Bundle b = new Bundle();
            b.putInt("object_id",mOrder.getOrder_contract_id());
            intent.putExtras(b);
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
//            Configuration croutonConfiguration = new Configuration.Builder().setDuration(2500).build();
//            Style style = new Style.Builder()
//                    .setBackgroundColorValue(Color.RED)
//                    .setGravity(Gravity.CENTER_HORIZONTAL)
//                    .setConfiguration(croutonConfiguration)
//                    .setHeight(112)
//                    .setTextSize(12)
//                    .setTextColorValue(Color.WHITE).build();
//
//            Crouton.showText(this, R.string.srtDone, style);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void click(String note, String status, int statusId) {
        Intent intent = new Intent(this, Edit_status_contract_activity.class);
        Bundle b = new Bundle();
        b.putString("note", note);
        b.putString("status", status);
        b.putInt("StatusId",statusId);
        b.putInt("object_id",mOrder.getOrder_contract_id());
        b.putInt("client_id",mOrder.getClient_id());
        b.putString("client_name",mOrder.getClient_name());
        b.putString("address",mOrder.getAddress());
        intent.putExtras(b);
        startActivity(intent);
    }
}
