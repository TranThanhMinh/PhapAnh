package anhpha.clientfirst.crm.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_History_contract;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.History_contract;
import anhpha.clientfirst.crm.model.Result_history_contract;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MinhTran on 7/12/2017.
 */

public class History_contract_activity extends BaseAppCompatActivity implements View.OnClickListener {
    private RecyclerView lvHistory_contract;
    private Retrofit retrofit;
    private adapter_History_contract adapter_history_contract;
    private ImageView imBack;
    private Toolbar toolbar;
    private Preferences preferences;
    private Bundle b;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_contract);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        preferences = new Preferences(mContext);
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.srtHistory_order);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        b=getIntent().getExtras();
      //  imBack = (ImageView) findViewById(R.id.imBack);
        lvHistory_contract = (RecyclerView) findViewById(R.id.lvHistory_contract);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lvHistory_contract.setHasFixedSize(true);
        lvHistory_contract.setLayoutManager(layoutManager);
        retrofit = func_Connect();
        getHistory_contract();
       // imBack.setOnClickListener(this);
    }

    public Retrofit func_Connect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_exchange)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public void getHistory_contract() {
        ServiceAPI history_contract = retrofit.create(ServiceAPI.class);
        Call<Result_history_contract> call = history_contract.getHistory_contract(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), preferences.getStringValue(Constants.TOKEN, ""),b.getInt("object_id"));
        call.enqueue(new Callback<Result_history_contract>() {
            @Override
            public void onResponse(Call<Result_history_contract> call, Response<Result_history_contract> response) {
                List<History_contract> list = response.body().getHistory_contracts();
                adapter_history_contract = new adapter_History_contract(History_contract_activity.this, list);
                lvHistory_contract.setAdapter(adapter_history_contract);
            }

            @Override
            public void onFailure(Call<Result_history_contract> call, Throwable t) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        if (view == imBack) {
            finish();
        }
    }
    protected void canel(){
    finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            canel();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
