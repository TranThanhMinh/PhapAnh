package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.graphics.Paint;
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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.WorkAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.DividerItemDecoration;
import anhpha.clientfirst.crm.interfaces.AdapterInterface;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MCalendar;
import anhpha.clientfirst.crm.model.MWorkUser;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.CalendarContentResolver;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import anhpha.clientfirst.crm.utils.DynamicBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorksActivity extends BaseAppCompatActivity implements  AdapterInterface, Callback<MAPIResponse<List<MWorkUser>>>, View.OnClickListener  {
    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    private Paint p = new Paint();
    List<MWorkUser> mWorkUsers;
    Preferences preferences;
    WorkAdapter activityAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_works);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar  actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_work);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_alter_add, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRetrofit().create(ServiceAPI.class)
                .getWorksUser(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                )
                .enqueue(this);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        box.showLoadingLayout();

        LogUtils.d(TAG, "getUserActivities ", "start");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                startActivity(new Intent(mContext, WorkActivity.class));
                return true;
            case R.id.alter:
                startActivity(new Intent(mContext, CalendarsActivity.class));
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<List<MWorkUser>>> call, Response<MAPIResponse<List<MWorkUser>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        mWorkUsers = response.body().getResult();
        boolean is_set = true;
        boolean is_set2 = true;
        for (MWorkUser mWorkUser: mWorkUsers) {
                mWorkUser.setDate(Utils.convertDateTime(mWorkUser.getWork_begin_date()));
        }

        Collections.sort(mWorkUsers, new Comparator<MWorkUser>() {
            @Override
            public int compare(MWorkUser fruit2, MWorkUser fruit1) {
                return fruit2.getDate().compareTo(fruit1.getDate()) ;
            }
        });
        for (MWorkUser mWorkUser: mWorkUsers){
            if(Utils.convertTime(mWorkUser.getWork_begin_date()) && is_set){
                mWorkUser.setStatus_date_id(1);
                is_set = false;
            }
            if(!Utils.convertTime(mWorkUser.getWork_begin_date()) && is_set2){
                mWorkUser.setStatus_date_id(-1);
                is_set2 = false;
            }else if(!Utils.convertTime(mWorkUser.getWork_begin_date()) && !is_set2){
                mWorkUser.setStatus_date_id(3);
            }
        }
        activityAdapter = new WorkAdapter(mContext,mWorkUsers,this);
        rvActivities.setAdapter(activityAdapter);
        activityAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MWorkUser>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void buttonPressed(final int i, final int position) {
        if(i==0){
            startActivity(new Intent(mContext, WorkActivity.class).putExtra("mWorkUser",mWorkUsers.get(position)));

        }else
        GetRetrofit().create(ServiceAPI.class)
                .setWorkUserStatus(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , mWorkUsers.get(position).getWork_user_id()
                        , i
                )
                .enqueue(new Callback<MAPIResponse<MWorkUser>>() {
                    @Override
                    public void onResponse(Call<MAPIResponse<MWorkUser>> call, Response<MAPIResponse<MWorkUser>> response) {
                        LogUtils.api(TAG, call, (response.body()));
                        box.hideAll();
                        TokenUtils.checkToken(mContext,response.body().getErrors());
                        CalendarContentResolver calendarContentResolver = new CalendarContentResolver(mContext);
                        List<MCalendar> mCalendars = calendarContentResolver.getCalendars();
                        int calendar_id = -1;
                        for(MCalendar c : mCalendars){
                            if(Utils.tryParse(c.getLocation()) ==  mWorkUsers.get(position).getWork_user_id()){
                                calendar_id = (c.getEvent_id());
                                break;
                            }
                        }

                        Utils.deleteEvent( calendar_id,mContext);
                        if(i==3){
                            mWorkUsers.remove(position);
                        }else {
                            mWorkUsers.get(position).setStatus_id(i);
                        }
                        boolean is_set = true;
                        boolean is_set2 = true;
                        for (MWorkUser mWorkUser: mWorkUsers) {
                            mWorkUser.setDate(Utils.convertDateTime(mWorkUser.getWork_begin_date()));
                        }

                        Collections.sort(mWorkUsers, new Comparator<MWorkUser>() {
                            @Override
                            public int compare(MWorkUser fruit2, MWorkUser fruit1) {
                                return fruit2.getDate().compareTo(fruit1.getDate()) ;
                            }
                        });
                        for (MWorkUser mWorkUser: mWorkUsers){
                            if(Utils.convertTime(mWorkUser.getWork_begin_date()) && is_set){
                                mWorkUser.setStatus_date_id(1);
                                is_set = false;
                            }
                            if(!Utils.convertTime(mWorkUser.getWork_begin_date()) && is_set2){
                                mWorkUser.setStatus_date_id(-1);
                                is_set2 = false;

                            }else if(!Utils.convertTime(mWorkUser.getWork_begin_date()) && !is_set2){
                                mWorkUser.setStatus_date_id(3);
                            }
                        }
                        activityAdapter = new WorkAdapter(mContext,mWorkUsers,WorksActivity.this);
                        rvActivities.setAdapter(activityAdapter);
                        activityAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<MAPIResponse<MWorkUser>> call, Throwable t) {
                        LogUtils.d(TAG, "getUserActivities ", t.toString());
                        box.hideAll();
                    }
                });
        box.showLoadingLayout();
    }

}
