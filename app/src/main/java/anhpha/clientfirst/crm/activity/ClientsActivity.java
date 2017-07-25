package anhpha.clientfirst.crm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.ClientAdapter;
import anhpha.clientfirst.crm.adapter.MStatus;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.DividerItemDecoration;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MClientArea;
import anhpha.clientfirst.crm.model.MClientGroup;
import anhpha.clientfirst.crm.model.MClientRequest;
import anhpha.clientfirst.crm.model.MClientType;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MLabel;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.HaversineAlgorithm;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import anhpha.clientfirst.crm.utils.DynamicBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientsActivity extends BaseAppCompatActivity implements SwipeRefreshLayout.OnRefreshListener ,Callback<MAPIResponse<List<MClient>>>, View.OnClickListener {
    @Bind(R.id.imageButton1)
    ImageButton imageButton1;
    @Bind(R.id.imageButton2)
    ImageButton imageButton2;
    @Bind(R.id.etText)
    EditText etText;
    @Bind(R.id.ibClose)
    ImageButton ibClose;
    @Bind(R.id.ibDone)
    ImageButton ibDone;
    @Bind(R.id.layout_find)
    LinearLayout layout_find;
    @Bind(R.id.imageButton3)
    ImageButton imageButton3;
    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    ClientAdapter activityAdapter;
    List<MClient> mClients = new ArrayList<>();
    List<MId> mIds = new ArrayList<>();
    List<MClientType> mTypes = new ArrayList<>();
    List<MClientGroup> mGroups = new ArrayList<>();
    List<MLabel> mLabels = new ArrayList<>();
    List<MStatus> mStatus = new ArrayList<>();
    List<MClientArea> mAreas = new ArrayList<>();
    boolean is_filter = false;
    private LinearLayoutManager mLayoutManager;
    private Preferences preferences;
    boolean is_distance;
    private boolean mHasRequestedMore = false;
    private int loadLimit = 20;
    private int current_page = 1;

    int visibleItemCount =0 ;
    int totalItemCount =0 ;
    int pastVisibleItems =0 ;
    boolean loading = true;
    private MClientRequest mClientRequest = new MClientRequest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_clients);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_client);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        ibClose.setOnClickListener(this);
        ibDone.setOnClickListener(this);

        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        activityAdapter = new ClientAdapter(mContext, mClients, is_distance);
        rvActivities.setAdapter(activityAdapter);

        rvActivities.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                        {
                            loading = false;
                            Log.v("...", "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data

                            current_page++;
                            loadClient();
                        }
                    }
                }
            }
        });

        box.showLoadingLayout();
        LogUtils.d(TAG, "getUserActivities ", "start");
        onLoadMoreItems();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_find_sort, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.find:
                layout_find.setVisibility(View.VISIBLE);
                return true;

            case R.id.sort:
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

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<List<MClient>>> call, Response<MAPIResponse<List<MClient>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        //swipeRefreshLayout.setRefreshing(false);
        TokenUtils.checkToken(mContext,response.body().getErrors());
        loading = true;
        //swipeRefreshLayout.setRefreshing(false);
        activityAdapter.setIs_distance(is_distance);
        if(current_page == 1) {
            mClients = response.body().getResult();
//            for (MClient mTracking : mClients) {
//
//                mTracking.setDistance(HaversineAlgorithm.HaversineInM(
//                         mTracking.getLatitude()
//                        , mTracking.getLongitude()
//                        , mLastLocation.getLatitude()
//                        , mLastLocation.getLongitude()));
//            }
            activityAdapter.setActivityItemList(mClients);
            rvActivities.setAdapter(activityAdapter);
        }

        else {
            mClients.addAll(response.body().getResult());
//            for (MClient mTracking : mClients) {
//
//                mTracking.setDistance(HaversineAlgorithm.HaversineInM(
//                        mLastLocation.getLatitude()
//                        , mLastLocation.getLongitude()
//                        , mTracking.getLatitude()
//                        , mTracking.getLongitude()));
//            }
            activityAdapter.setActivityItemList(mClients);
            activityAdapter.notifyDataSetChanged();
        }

    }

    private void loadClient() {
        //box.showLoadingLayout();
        if (is_distance) {
           onLoadMoreItemsLocation();
        } else {
          onLoadMoreItems();
        }
    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MClient>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton1:
                is_distance = false;
                current_page = 1;
                loadClient();
                break;
            case R.id.imageButton2:
                current_page = 1;
                is_distance = true;
                loadClient();
                break;
            case R.id.imageButton3:
                CharSequence[] charSequences = new CharSequence[3];
                charSequences[0] = getString(R.string.add_client_person);
                charSequences[1] = getString(R.string.add_client_company);
                charSequences[2] = getString(R.string.from_comtact);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(getResources().getString(R.string.add_client_dialog));
                builder.setCancelable(true);
                builder.setItems(charSequences, new  DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(mContext, EditClientActivity.class) ;
                                intent.putExtra("Structure",1);
                                startActivity(intent);

                                break;
                            case 1:
                                Intent intent2 = new Intent(mContext, EditClientActivity.class) ;
                                intent2.putExtra("Structure",2);
                                startActivity(intent2);

                                break;
                            case 2:
                                startActivity(new Intent(mContext, ChooseClientContactActivity.class));

                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.show();
                break;
            case R.id.ibClose:
                layout_find.setVisibility(View.GONE);
                etText.setText("");
                loadClient();
                break;
            case R.id.ibDone:
                current_page = 1;
                mClientRequest = new MClientRequest();
               loadClient();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        mClientRequest = new MClientRequest();
        mIds  = new ArrayList<>();
        current_page = 1;
        etText.setText("");
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

        rvActivities.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadClient();
            }
        },200);
    }


    @Override
    public void onRefresh() {
        current_page = 1;
        mClientRequest = new MClientRequest();
        etText.setText("");
        loadClient();
    }

    private void onLoadMoreItems() {
        mClientRequest.setValue(etText.getText().toString());
        GetRetrofit().create(ServiceAPI.class)
                .getClientsInPage(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , 40
                        ,"android1007"
                        ,current_page
                        ,mClientRequest
                )
                .enqueue(this);
    }
    private void onLoadMoreItemsLocation() {
        mClientRequest.setValue(etText.getText().toString());
        GetRetrofit().create(ServiceAPI.class)
                .getClientsLocation(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , 40
                        ,"android1007"
                        ,current_page
                        ,mLastLocation.getLatitude()
                        ,mLastLocation.getLongitude()
                        ,mClientRequest
                )
                .enqueue(this);
    }
}
