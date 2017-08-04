package anhpha.clientfirst.crm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.MStatus;
import anhpha.clientfirst.crm.adapter.adapter_Focus;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.Focus;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MClientArea;
import anhpha.clientfirst.crm.model.MClientGroup;
import anhpha.clientfirst.crm.model.MClientRequest;
import anhpha.clientfirst.crm.model.MClientType;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MLabel;
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
    List<MClient> mClients = new ArrayList<>();
    List<MId> mIds = new ArrayList<>();
    List<MClientType> mTypes = new ArrayList<>();
    List<MClientGroup> mGroups = new ArrayList<>();
    List<MLabel> mLabels = new ArrayList<>();
    List<MStatus> mStatus = new ArrayList<>();
    List<MClientArea> mAreas = new ArrayList<>();
    private MClientRequest mClientRequest = new MClientRequest();
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

    }

    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFocus();
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
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
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
            CharSequence[] charSequences = new CharSequence[6];
            charSequences[0] = getString(R.string.type_client);
            charSequences[2] = getString(R.string.group_client);
            charSequences[3] = getString(R.string.area);
            charSequences[1] = getString(R.string.status);
            charSequences[4] = getString(R.string.manager);
            charSequences[5] = getString(R.string.label);
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(getResources().getString(R.string.sort));
            builder.setCancelable(true);
            builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case 0:
                            startActivityForResult(new Intent(mContext, ChooseClientTypeActivity.class).putExtra("mClientTypes", (Serializable) mTypes), Constants.RESULT_TYPE);
                            break;
                        case 2:
                            startActivityForResult(new Intent(mContext, ChooseClientGroupActivity.class).putExtra("mClientGroups", (Serializable) mGroups), Constants.RESULT_GROUP);
                            break;
                        case 3:
                            startActivityForResult(new Intent(mContext, ChooseClientAreaActivity.class).putExtra("mClientAreas", (Serializable) mAreas), Constants.RESULT_AREA);
                            break;
                        case 1:
                            startActivityForResult(new Intent(mContext, ChooseClientStatusActivity.class).putExtra("mStatuses", (Serializable) mStatus), Constants.RESULT_STATUS);
                            break;
                        case 4:
                            startActivityForResult(new Intent(mContext, UsersActivity.class).putExtra("mIds", (Serializable) mIds), Constants.RESULT_USERS);
                            break;
                        case 5:
                            startActivityForResult(new Intent(mContext, ChooseClientLabelActivity.class).putExtra("mLabels", (Serializable) mLabels), Constants.RESULT_LABEL);
                            break;
                        default:
                            break;
                    }
                }
            });
            builder.show();
            return true;
        }
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        mClientRequest = new MClientRequest();
        mIds  = new ArrayList<>();
//        current_page = 1;
//        etText.setText("");
        if (resultCode == Constants.RESULT_USERS) {
            mIds = new ArrayList<>();
            mIds = (List<MId>) data.getSerializableExtra("mIds");
            mClientRequest.setUser_ids(mIds);
        }
        if (resultCode == Constants.RESULT_STATUS) {
            mStatus = new ArrayList<>();
            mStatus = (List<MStatus>) data.getSerializableExtra("mStatuses");
            for (MStatus s : mStatus) {
                if (s.is_select()) {
                    mIds.add(new MId(s.getId()));
                    s.setIs_select(false);
                }
            }
            mClientRequest.setStatus(mIds);
        }
        if (resultCode == Constants.RESULT_AREA) {
            mAreas = new ArrayList<>();
            mAreas = (List<MClientArea>) data.getSerializableExtra("mClientAreas");
            for (MClientArea s : mAreas) {
                if (s.is_select()) {
                    mIds.add(new MId(s.getClient_area_id()));
                    s.setIs_select(false);
                }
            }
            mClientRequest.setAreas(mIds);
        }
        if (resultCode == Constants.RESULT_GROUP) {
            mGroups = new ArrayList<>();
            mGroups = (List<MClientGroup>) data.getSerializableExtra("mClientGroups");
            for (MClientGroup s : mGroups) {
                if (s.is_select()) {
                    mIds.add(new MId(s.getClient_group_id()));
                    s.setIs_select(false);
                }
            }
            mClientRequest.setGroups(mIds);
        }
        if (resultCode == Constants.RESULT_TYPE) {
            mTypes = new ArrayList<>();
            mTypes = (List<MClientType>) data.getSerializableExtra("mClientTypes");
            for (MClientType s : mTypes) {
                if (s.is_select()) {
                    mIds.add(new MId(s.getClient_type_id()));
                    s.setIs_select(false);
                }
            }
            mClientRequest.setTypes(mIds);
        }

        if (resultCode == Constants.RESULT_LABEL) {
            mLabels = new ArrayList<>();
            mLabels = (List<MLabel>) data.getSerializableExtra("mLabels");
            for (MLabel s : mLabels) {
                if (s.getIs_has()) {
                    mIds.add(new MId(s.getClient_label_id()));
                    s.setIs_has(false);
                }
            }
            mClientRequest.setLabels(mIds);
        }
        Log.d("AAAA",new Gson().toJson(mClientRequest));

        lvFocus.postDelayed(new Runnable() {
            @Override
            public void run() {
                getFocus();
            }
        },200);
    }

    @Override
    public void Click(int i,int ii) {
        funcFocus_status(i,ii);
    }
}
