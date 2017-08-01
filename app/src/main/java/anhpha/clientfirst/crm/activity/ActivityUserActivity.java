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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.ActivityAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.RecyclerTouchListener;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MActivity;
import anhpha.clientfirst.crm.model.MActivityItem;
import anhpha.clientfirst.crm.model.MId;
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

public class ActivityUserActivity extends BaseAppCompatActivity implements RecyclerTouchListener.ClickListener, Callback<MAPIResponse<MActivity>>, View.OnClickListener  {
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.textView7)
    TextView textView7;
    @Bind(R.id.imageButton1)
    ImageButton imageButton1;
    @Bind(R.id.imageButton2)
    ImageButton imageButton2;
    @Bind(R.id.imageButton3)
    ImageButton imageButton3;
    @Bind(R.id.imageButton4)
    ImageButton imageButton4;
    @Bind(R.id.imageButton5)
    ImageButton imageButton5;
    @Bind(R.id.imageButton7)
    ImageButton imageButton7;
    @Bind(R.id.line1)
    LinearLayout line1;
    @Bind(R.id.line2)
    LinearLayout line2;
    @Bind(R.id.line3)
    LinearLayout line3;
    @Bind(R.id.line4)
    LinearLayout line4;
    @Bind(R.id.line5)
    LinearLayout line5;
    @Bind(R.id.line7)
    LinearLayout line7;
    @Bind(R.id.tvAmount)
    TextView tvAmount;
    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    MActivity mActivity;
    List<MId> mIds = new ArrayList<>();
    List<MActivityItem> mActivityItems = new ArrayList<>();
    List<MActivityItem> mActivityItems1 = new ArrayList<>();
    String toDate;
    String fromDate;
    int type = 0;
    ActivityAdapter activityAdapter;
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_activity_user);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_activity_user);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvActivities, this));

        fromDate =(Utils.getFromDate());
        toDate = (Utils.getToDate());

        line1.setOnClickListener(this);
        line2.setOnClickListener(this);
        line3.setOnClickListener(this);
        line4.setOnClickListener(this);
        line5.setOnClickListener(this);
        line7.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:
                startActivityForResult( new Intent(mContext, CalendarActivity.class), Constants.RESULT_CALENDAR);
                return true;
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

    public List<MActivityItem> getActivityItems(List<MActivityItem> mActivityItems){
        mActivityItems1 = new ArrayList<>();
        switch (type){
            case 0:
                mActivityItems1.addAll(mActivityItems);
                break;
            case 1:
                for (MActivityItem i: mActivityItems){
                    if(i.getActivity_type() == 1)
                    mActivityItems1.add(i);
                }
                break;
            case 2:
                for (MActivityItem i: mActivityItems){
                    if(i.getActivity_type() == 2)
                        mActivityItems1.add(i);
                }
                break;
            case 3:
                for (MActivityItem i: mActivityItems){
                    if(i.getActivity_type() == 3)
                        mActivityItems1.add(i);
                }
                break;
            case 9:
                for (MActivityItem i: mActivityItems){
                    if(i.getActivity_type() == 9)
                        mActivityItems1.add(i);
                }
                break;
            case 5:
                for (MActivityItem i: mActivityItems){
                    if(i.getActivity_type() == 5)
                        mActivityItems1.add(i);
                }
                break;
            case 6:
                for (MActivityItem i: mActivityItems){
                    if(i.getActivity_type() == 6)
                        mActivityItems1.add(i);
                }
                break;
            case 7:
                for (MActivityItem i: mActivityItems){
                    if(i.getActivity_type() == 7)
                        mActivityItems1.add(i);
                }
                break;
            default:
                break;
        }
        return mActivityItems1;
    }

    @Override
    public void onResponse(Call<MAPIResponse<MActivity>> call, Response<MAPIResponse<MActivity>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();

        TokenUtils.checkToken(mContext,response.body().getErrors());

        mActivity = response.body().getResult();
        mActivityItems = mActivity.getActivies();

        activityAdapter = new ActivityAdapter(getActivityItems(mActivityItems));
        rvActivities.setAdapter(activityAdapter);
        activityAdapter.notifyDataSetChanged();

        tvAmount.setText("$ " + Utils.formatCurrency(mActivity.getSales_amount()));

        textView1.setText(mActivity.getOrder_count() + "");
        textView2.setText(mActivity.getAdd_client_count() + "");
        textView3.setText(mActivity.getCheckin_count() + "");
        textView4.setText(mActivity.getCall_count() + "");
        textView5.setText(mActivity.getEmail_count() + "");
        textView7.setText(mActivity.getEvent_count() + "");
    }

    @Override
    public void onFailure(Call<MAPIResponse<MActivity>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
        imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_16));
        imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_17));
        imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_96));
        imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_18));
        imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_20));
        imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_23));

        switch (view.getId()){
            case R.id.line1:
                if(type!= Constants.ACTIVITY_TYPE_ORDER)type = Constants.ACTIVITY_TYPE_ORDER;else type = 0;
                if(type!= Constants.ACTIVITY_TYPE_ORDER)imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_16));else imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_11));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line2:
                if(type!= Constants.ACTIVITY_TYPE_CLIENT)type = Constants.ACTIVITY_TYPE_CLIENT;else type = 0;
                if(type!= Constants.ACTIVITY_TYPE_CLIENT)imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_17));else imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_3));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line3:
                if(type!= Constants.ACTIVITY_TYPE_CHECKIN)type = Constants.ACTIVITY_TYPE_CHECKIN;else type = 0;
                if(type!= Constants.ACTIVITY_TYPE_CHECKIN)imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_96));else imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_97));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line4:
                if(type!= Constants.ACTIVITY_TYPE_CALL)type = Constants.ACTIVITY_TYPE_CALL;else type = 0;
                if(type!= Constants.ACTIVITY_TYPE_CALL)imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_18));else imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_6));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            case R.id.line7:
                if(type!= Constants.ACTIVITY_TYPE_EVENT)type = Constants.ACTIVITY_TYPE_EVENT;else type = 0;
                if(type!= Constants.ACTIVITY_TYPE_EVENT)imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_23));else imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_15));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;

            case R.id.line5:
                if(type!= Constants.ACTIVITY_TYPE_EMAIL)type = Constants.ACTIVITY_TYPE_EMAIL;else type = 0;
                if(type!= Constants.ACTIVITY_TYPE_EMAIL)imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_20));else imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_crm_1));
                activityAdapter.setActivityItemList(getActivityItems(mActivityItems));
                activityAdapter.notifyDataSetChanged();
                break;
            default:
                break;
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

        if (resultCode == Constants.RESULT_CALENDAR) {
            toDate = data.getStringExtra("toDate");
            fromDate = data.getStringExtra("fromDate");
            tvDate.setText(data.getStringExtra("tvDate"));
            title.setText(data.getStringExtra("tvDate"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MRequestBody mRequestBody = new MRequestBody();
        mRequestBody.setFrom_date(fromDate);
        mRequestBody.setTo_date(toDate);
        mRequestBody.setUser_ids(mIds);

        GetRetrofit().create(ServiceAPI.class)
                .getUserActivities(preferences.getStringValue(Constants.TOKEN,"")
                        ,preferences.getIntValue(Constants.USER_ID,0)
                        ,preferences.getIntValue(Constants.PARTNER_ID,0)
                        ,mRequestBody
                )
                .enqueue(this);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        box.showLoadingLayout();
    }

    @Override
    public void onClick(View view, int position) {
        startActivity(new Intent(mContext,ClientActivity.class).putExtra("mActivityItem",mActivityItems1.get(position)));
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
