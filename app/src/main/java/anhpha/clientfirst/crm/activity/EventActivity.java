package anhpha.clientfirst.crm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.EventDetailAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MEvent;
import anhpha.clientfirst.crm.model.MEventDetail;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MPhoto;
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

public class EventActivity extends BaseAppCompatActivity implements View.OnClickListener  {
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.description)
    TextView description;

    @Bind(R.id.invite)
    TextView invite;
    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.confirm)
    TextView confirm;
    @Bind(R.id.attend)
    TextView attend;

    @Bind(R.id.invite_label)
    TextView invite_label;
    @Bind(R.id.cancel_label)
    TextView cancel_label;
    @Bind(R.id.confirm_label)
    TextView confirm_label;
    @Bind(R.id.attend_label)
    TextView attend_label;

    @Bind(R.id.rv2)
    ListView rv2;
    List<MId> mIds = new ArrayList<>();
    Preferences preferences;
    MEvent mEvent;
    EventDetailAdapter activityAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_event);
        ButterKnife.bind(this);
        mEvent = (MEvent) getIntent().getSerializableExtra("mEvent");
        setSupportActionBar(toolbar);
        ActionBar  actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_event);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        name.setText(mEvent.getEvent_name());
        description.setText(Html.fromHtml(mEvent.getEvent_content()));
        preferences = new Preferences(mContext);
        invite_label.setOnClickListener(this);
        cancel_label.setOnClickListener(this);
        attend_label.setOnClickListener(this);
        confirm_label.setOnClickListener(this);
        invite_label.setTag(0);
        cancel_label.setTag(0);
        attend_label.setTag(0);
        confirm_label.setTag(0);
        rv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MClient mClient = new MClient();
                mClient.setClient_id(activityAdapter.getItem(position).getClient_id());
                mClient.setClient_name(activityAdapter.getItem(position).getClient_name());
                mClient.setAddress(activityAdapter.getItem(position).getAddress());
                Intent intent = new Intent(mContext, ClientActivity.class).putExtra("mClient",mClient);
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MRequestBody mRequestBody = new MRequestBody();
        mRequestBody.setUser_ids(mIds);
        GetRetrofit().create(ServiceAPI.class)
                .getEvent(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , mEvent.getEvent_id()
                        ,mRequestBody
                )
                .enqueue(new Callback<MAPIResponse<MEvent>>() {
                            @Override
                            public void onResponse(Call<MAPIResponse<MEvent>> call, Response<MAPIResponse<MEvent>> response) {
                                LogUtils.api(TAG, call, (response.body()));
                                box.hideAll();
                                TokenUtils.checkToken(mContext,response.body().getErrors());
                                mEvent = response.body().getResult();
                                setEventDetail(mEvent.getClients());
                            }
                            @Override
                            public void onFailure(Call<MAPIResponse<MEvent>> call, Throwable t) {
                                LogUtils.d(TAG, "getUserActivities ", t.toString());
                                box.hideAll();
                            }
                });

    }

    private void setEventDetail(List<MEventDetail> eventDetails){
        int num_invite = 0;
        int num_cancel = 0;
        int num_confirm = 0;
        int num_attend = 0;
        for(MEventDetail e :mEvent.getClients())
        {
            switch (e.getEvent_detail_status_id()){
                case 1:
                    num_invite ++;
                    break;
                case 2:
                    num_cancel++;
                    break;
                case 3:
                    num_confirm++;
                    break;
                case 4:
                    num_attend++;
                    break;
                default:
                    break;
            }
        }
        attend.setText(num_attend + "");
        confirm.setText(num_confirm + "");
        cancel.setText(num_cancel + "");
        invite.setText(num_invite + "");

        activityAdapter = new EventDetailAdapter(eventDetails,mContext);
        rv2.setAdapter(activityAdapter);
        rv2.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.setListViewHeightBasedOnChildren(rv2);
            }
        }, 500);
        Utils.setListViewHeightBasedOnChildren(rv2);
        activityAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:

                return true;
            case R.id.user:
                startActivityForResult(new Intent(mContext, UsersActivity.class),Constants.RESULT_USERS);

                return true;
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        attend_label.setBackgroundResource(R.drawable.button_round_orange);
        invite_label.setBackgroundResource(R.drawable.button_round_blue);
        cancel_label.setBackgroundResource(R.drawable.button_round_red);
        confirm_label.setBackgroundResource(R.drawable.button_round_green);
        switch (view.getId()){
            case R.id.attend_label:
                if(Utils.tryParse(attend_label.getTag() + "") != 1) {
                    attend_label.setBackgroundResource(R.drawable.button_round_grey);
                    List<MEventDetail> details= new ArrayList<>();
                    for(MEventDetail ed: mEvent.getClients()){
                            if(4 == ed.getEvent_detail_status_id()){
                                details.add(ed);
                            }
                    }
                    setEventDetail(details);
                    attend_label.setTag(1);
                }else{
                    attend_label.setTag(0);
                    setEventDetail(mEvent.getClients());
                }
                break;
            case R.id.cancel_label:
                if(Utils.tryParse(cancel_label.getTag() + "") != 1) {
                    cancel_label.setBackgroundResource(R.drawable.button_round_grey);
                    List<MEventDetail> details= new ArrayList<>();
                    for(MEventDetail ed: mEvent.getClients()){
                        if(2 == ed.getEvent_detail_status_id()){
                            details.add(ed);
                        }

                    }
                    setEventDetail(details);
                    cancel_label.setTag(1);
                }else{
                    cancel_label.setTag(0);
                    setEventDetail(mEvent.getClients());
                }
                break;
            case R.id.confirm_label:
                if(Utils.tryParse(confirm_label.getTag() + "") != 1) {
                    confirm_label.setBackgroundResource(R.drawable.button_round_grey);
                    List<MEventDetail> details= new ArrayList<>();
                    for(MEventDetail ed: mEvent.getClients()){
                        if(3 == ed.getEvent_detail_status_id()){
                            details.add(ed);
                        }

                    }
                    setEventDetail(details);
                    confirm_label.setTag(1);
                }else{
                    confirm_label.setTag(0);
                    setEventDetail(mEvent.getClients());
                }
                break;
            case R.id.invite_label:
                if(Utils.tryParse(invite_label.getTag() + "") != 1) {
                    invite_label.setBackgroundResource(R.drawable.button_round_grey);
                    List<MEventDetail> details= new ArrayList<>();
                    for(MEventDetail ed: mEvent.getClients()){
                        if(1 == ed.getEvent_detail_status_id()){
                            details.add(ed);
                        }
                    }
                    setEventDetail(details);
                    invite_label.setTag(1);
                }else{
                    invite_label.setTag(0);
                    setEventDetail(mEvent.getClients());
                }
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

            MRequestBody mRequestBody = new MRequestBody();
            mRequestBody.setUser_ids(mIds);
            GetRetrofit().create(ServiceAPI.class)
                    .getEvent(preferences.getStringValue(Constants.TOKEN, "")
                            , preferences.getIntValue(Constants.USER_ID, 0)
                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                            , mEvent.getEvent_id()
                            ,mRequestBody
                    )
                    .enqueue(new Callback<MAPIResponse<MEvent>>() {
                        @Override
                        public void onResponse(Call<MAPIResponse<MEvent>> call, Response<MAPIResponse<MEvent>> response) {
                            LogUtils.api(TAG, call, (response.body()));
                            box.hideAll();
                            TokenUtils.checkToken(mContext,response.body().getErrors());
                            mEvent = response.body().getResult();
                            setEventDetail(mEvent.getClients());
                            attend_label.setBackgroundResource(R.drawable.button_round_orange);
                            invite_label.setBackgroundResource(R.drawable.button_round_blue);
                            cancel_label.setBackgroundResource(R.drawable.button_round_red);
                            confirm_label.setBackgroundResource(R.drawable.button_round_green);

                            attend_label.setTag(0);
                            invite_label.setTag(0);
                            confirm_label.setTag(0);
                            cancel_label.setTag(0);
                        }

                        @Override
                        public void onFailure(Call<MAPIResponse<MEvent>> call, Throwable t) {
                            LogUtils.d(TAG, "getUserActivities ", t.toString());
                            box.hideAll();
                        }
                    });

        }
    }
}
