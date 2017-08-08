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
import java.util.Collections;
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
import anhpha.clientfirst.crm.model.MClientLabel;
import anhpha.clientfirst.crm.model.MClientRequest;
import anhpha.clientfirst.crm.model.MClientType;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MLabel;
import anhpha.clientfirst.crm.model.Result;
import anhpha.clientfirst.crm.model.Result_focus;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.sweet.SweetAlert.SweetAlertDialog;
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
    private boolean isSort = false;
    private Retrofit retrofit;
    private Preferences preferences;
    private List<Focus> lv_focus;
    private List<Focus> lv_sort;
    List<MClient> mClients = new ArrayList<>();
    List<MId> mIds = new ArrayList<>();
    List<MClientType> mTypes = new ArrayList<>();
    List<MClientType> mType = new ArrayList<>();
    List<MClientGroup> mGroups = new ArrayList<>();
    List<MClientGroup> mGroup = new ArrayList<>();
    List<MLabel> mLabels = new ArrayList<>();
    List<MLabel> mLabel = new ArrayList<>();
    List<MStatus> mStatus = new ArrayList<>();
    List<MStatus> mStatu = new ArrayList<>();
    List<MClientArea> mAreas = new ArrayList<>();
    List<MClientArea> mArea = new ArrayList<>();
    private MClientRequest mClientRequest = new MClientRequest();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        lvFocus = (RecyclerView) findViewById(R.id.lvFocus);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        preferences = new Preferences(mContext);

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
        getFocus(0);

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
        if (isSort == true) {
            Log.d("RESULT_TYPE", "lv_sort");
            adapter_Focus adapter_focus = new adapter_Focus(Focus_activity.this, lv_sort, Focus_activity.this);
            lvFocus.setAdapter(adapter_focus);
        } else {
            Log.d("RESULT_TYPE", "lv_focus");
            getFocus(0);
        }
    }

    public void getFocus(int type) {
        ServiceAPI focus = retrofit.create(ServiceAPI.class);
        Call<Result_focus> result_focus = focus.get_clients_focus(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), type, preferences.getStringValue(Constants.TOKEN, ""));
        result_focus.enqueue(new Callback<Result_focus>() {
            @Override
            public void onResponse(Call<Result_focus> call, Response<Result_focus> response) {
                LogUtils.api("", call, response);
                if (response.body() != null) {
                    lv_focus = response.body().getFocus();
                    Collections.sort(lv_focus);
                    adapter_Focus adapter_focus = new adapter_Focus(Focus_activity.this, lv_focus, Focus_activity.this);
                    lvFocus.setAdapter(adapter_focus);
                } else Toast.makeText(mContext, "No data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Result_focus> call, Throwable t) {
                LogUtils.api("", call, t.toString());
                Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void funcFocus_status(int i, int ii) {
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
                LogUtils.api("", call, response);
                if (response.body().getHasErrors() == false) {
                    new SweetAlertDialog(Focus_activity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(Focus_activity.this.getString(R.string.srtNotifi))
                            .setContentText(Focus_activity.this.getString(R.string.srtDone))
                            .setConfirmText(Focus_activity.this.getString(R.string.srtAgree))
                            .show();
                    getFocus(0);
                } else {
                    new SweetAlertDialog(Focus_activity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(Focus_activity.this.getString(R.string.srtNotifi))
                            .setContentText(Focus_activity.this.getString(R.string.srtFalse))
                            .setConfirmText(Focus_activity.this.getString(R.string.srtAgree))
                            .show();
                }
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                LogUtils.api("", call, t.toString());
                new SweetAlertDialog(Focus_activity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(Focus_activity.this.getString(R.string.srtNotifi))
                        .setContentText(Focus_activity.this.getString(R.string.srtFalse))
                        .setConfirmText(Focus_activity.this.getString(R.string.srtAgree))
                        .show();
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
        mIds = new ArrayList<>();
        lv_sort = new ArrayList<>();
//        current_page = 1;
//        etText.setText("");
        if (resultCode == Constants.RESULT_USERS) {
            adapter_Focus adapter_focus = new adapter_Focus(Focus_activity.this, lv_focus, Focus_activity.this);
            lvFocus.setAdapter(adapter_focus);
        } else if (resultCode == Constants.RESULT_STATUS) {
            lvFocus.setAdapter(null);
            mStatu = new ArrayList<>();
            mStatu = (List<MStatus>) data.getSerializableExtra("mStatuses");
            if (mStatu != null && mStatu.size() > 0) {
                isSort = true;
                for (Focus f : lv_focus) {
                    Log.d("RESULT_STATUS1", " = " + f.getStatusId());
                    for (MStatus s : mStatu) {
                        if (s.getId() == f.getStatusId()) {
                            if (s.is_select()) {
                                Log.d("RESULT_STATUS2", s.getId() + " = " + f.getStatusId());
                                listSort(f);
//                                s.setIs_select(false);
                            }

                        }

                    }
                }
            } else isSort = false;

        } else if (resultCode == Constants.RESULT_AREA) {
            lvFocus.setAdapter(null);
            mArea = new ArrayList<>();
            mArea = (List<MClientArea>) data.getSerializableExtra("mClientAreas");
            if (mArea != null && mArea.size() > 0) {
                isSort = true;
                for (Focus f : lv_focus) {
                    for (MClientArea s : mArea) {
                        if (s.getClient_area_id() == f.getClientAreaId()) {
                            if (s.is_select()) {
                                listSort(f);
                            }
//                        s.setIs_select(false);
                        }

                    }
                }
            } else isSort = false;

        } else if (resultCode == Constants.RESULT_GROUP) {
            lvFocus.setAdapter(null);
            mGroup = new ArrayList<>();
            mGroup = (List<MClientGroup>) data.getSerializableExtra("mClientGroups");

            if (mGroup != null && mGroup.size() > 0) {
                isSort = true;
                for (Focus f : lv_focus) {
                    for (MClientGroup s : mGroup) {
                        if (s.getClient_group_id() == f.getClientGroupId()) {
                            if (s.is_select()) {
                                listSort(f);
//                                s.setIs_select(false);
                            }

                        }

                    }
                }
            } else isSort = false;
        } else if (resultCode == Constants.RESULT_TYPE) {
            lvFocus.setAdapter(null);
            mType = (List<MClientType>) data.getSerializableExtra("mClientTypes");
            if (mType != null && mType.size() > 0) {
                isSort = true;
                for (Focus f : lv_focus) {
                    for (MClientType s : mType) {
                        if (s.getClient_type_id() == f.getClientTypeId()) {
                            if (s.is_select()) {
                                Log.d("RESULT_TYPE", s.getClient_type_id() + " = " + f.getClientTypeId());
                                listSort(f);
//                                s.setIs_select(false);
                            }
                        }

                    }
                }
            } else isSort = false;

            // mClientRequest.setTypes(mIds);
        } else if (resultCode == Constants.RESULT_LABEL) {
            lvFocus.setAdapter(null);
            mLabel = new ArrayList<>();
            mLabel = (List<MLabel>) data.getSerializableExtra("mLabels");
            if (mLabel != null && mLabel.size() > 0) {
                isSort = true;
                for (Focus f : lv_focus) {
                    String ClientLabel = "", Label = "";
                    List<MClientLabel> lvLabel = f.getLabels();
                    Log.d("RESULT_TYPE1", "");
                    for (MClientLabel m : lvLabel) {
                        ClientLabel = ClientLabel + "," + m.getHex();
                        Log.d("RESULT_TYPE2", m.getHex());
                    }
                    for (MLabel s : mLabel) {
                        if (s.getIs_has()) {
                            Label = Label + "," + s.getHex();
                            Log.d("RESULT_TYPE3", s.getHex());
                        }
                    }
                    if (ClientLabel.contains(Label)) {
                        listSort(f);
                        Log.d("RESULT_TYPE4", "Giong");
                    } else {
                        Log.d("RESULT_TYPE5", "Khong Giong");
                    }
                }
            } else isSort = false;

        }
        Log.d("AAAA", new Gson().toJson(mClientRequest));

        lvFocus.postDelayed(new Runnable() {
            @Override
            public void run() {
                //getFocus(0);

            }
        }, 1);
    }

    public void listSort(Focus f) {
        Focus focus = new Focus();
        focus.setClientId(f.getClientId());
        focus.setUserId(f.getUserId());
        focus.setClientName(f.getClientName());
        focus.setAddress(f.getAddress());
        focus.setClientTypeId(f.getClientTypeId());
        focus.setClientGroupId(f.getClientGroupId());
        focus.setClientAreaId(f.getClientAreaId());
        focus.setLatitude(f.getLatitude());
        focus.setLongitude(f.getLongitude());
        focus.setStatusId(f.getStatusId());
        focus.setUserManagerId(f.getUserManagerId());
        focus.setClientFocusId(f.getClientFocusId());
        focus.setFocusStatusId(f.getFocusStatusId());
        focus.setFocusTypeId(f.getFocusTypeId());
        focus.setFocusTargetId(f.getFocusTargetId());
        focus.setClient_structure_id(f.getClient_structure_id());
        focus.setFocusStatusName(f.getFocusStatusName());
        focus.setFocusTargetName(f.getFocusTargetName());
        focus.setBeginDate(f.getBeginDate());
        focus.setEndDate(f.getEndDate());
        focus.setNumberOrder(f.getNumberOrder());
        focus.setNumberMeeting(f.getNumberMeeting());
        focus.setNumberCall(f.getNumberCall());
        focus.setNumberDate(f.getNumberDate());
        focus.setNumberEmail(f.getNumberEmail());
        focus.setNumberEvent(f.getNumberEvent());
        focus.setLabels(f.getLabels());
        lv_sort.add(focus);
    }
    @Override
    public void Click(int i, int ii) {
        funcFocus_status(i, ii);
    }
}
