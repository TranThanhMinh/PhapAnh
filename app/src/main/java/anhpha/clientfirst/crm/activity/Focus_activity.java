package anhpha.clientfirst.crm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_Focus;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.Focus;
import anhpha.clientfirst.crm.model.Result;
import anhpha.clientfirst.crm.model.Result_focus;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.LogUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MinhTran on 7/24/2017.
 */

public class Focus_activity extends BaseAppCompatActivity implements adapter_Focus.Event {
    private RecyclerView lvFocus;
    private Toolbar toolbar;
    private boolean isHideMenu;
    private Retrofit retrofit;
    private Preferences preferences;
    private List<Focus> lv_focus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        lvFocus = (RecyclerView) findViewById(R.id.lvFocus);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        preferences = new Preferences(mContext);
        isHideMenu = false;
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.srtFocus);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvFocus.setHasFixedSize(true);
        lvFocus.setLayoutManager(manager);
        retrofit = getConnect();
        getFocus();
    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public void getFocus() {
        ServiceAPI focus = retrofit.create(ServiceAPI.class);
        Call<Result_focus> result_focus = focus.get_clients_focus(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), 0, preferences.getStringValue(Constants.TOKEN, ""));
        result_focus.enqueue(new Callback<Result_focus>() {
            @Override
            public void onResponse(Call<Result_focus> call, Response<Result_focus> response) {
                LogUtils.api("",call,response);
                if (response.body() != null) {
                    lv_focus = response.body().getFocus();
                    adapter_Focus adapter_focus = new adapter_Focus(Focus_activity.this, lv_focus,Focus_activity.this);
                    lvFocus.setAdapter(adapter_focus);
                } else Toast.makeText(mContext, "No data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Result_focus> call, Throwable t) {
                LogUtils.api("",call,t.toString());
                Toast.makeText(mContext, "No data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void funcFocus_status(int i,int ii) {
        List<Focus> lvFocus = new ArrayList<>();
        Focus focus = new Focus();
        focus.setClientFocusId(lv_focus.get(i).getClientFocusId());
        focus.setFocusStatusId(ii);
        lvFocus.add(focus);
        ServiceAPI apiFocus = retrofit.create(ServiceAPI.class);
        Call<Result> result_focus = apiFocus.set_clients_focus_status(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), preferences.getStringValue(Constants.TOKEN, ""), lvFocus);
        result_focus.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                LogUtils.api("",call,response);
                if (response.body().getHasErrors() == false) {
                    getFocus();
                } else {

                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                LogUtils.api("",call,t.toString());
                Toast.makeText(mContext, "No data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_focus, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actioSort) {

        }
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void Click(int i,int ii) {
        funcFocus_status(i,ii);
    }
}
