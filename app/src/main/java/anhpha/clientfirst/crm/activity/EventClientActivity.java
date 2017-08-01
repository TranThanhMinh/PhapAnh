package anhpha.clientfirst.crm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MEvent;
import anhpha.clientfirst.crm.model.MMessager;
import anhpha.clientfirst.crm.model.MPhoto;
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

public class EventClientActivity extends BaseAppCompatActivity implements View.OnClickListener  {
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.number)
    TextView number;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.description)
    TextView description;
    @Bind(R.id.status)
    TextView status;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.tvView)
    TextView tvView;
    @Bind(R.id.layout_image)
    LinearLayout layout_image;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    MEvent mEvent;
    MClient mClient;
    boolean ShowMenu = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_event_client);
        ButterKnife.bind(this);
        mEvent = (MEvent) getIntent().getSerializableExtra("mEvent");
        ShowMenu = getIntent().getBooleanExtra("ShowMenu",true);
        mClient = (MClient) getIntent().getSerializableExtra("mClient");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_event);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        if(mEvent!= null){
            if(mEvent.getPhotos().size() > 0) {
                Picasso.with(getApplicationContext()).load(mEvent.getPhotos().get(0).url.indexOf("http") < 0 ? "http://" : mEvent.getPhotos().get(0).url + mEvent.getPhotos().get(0).name).fit().centerInside().error(R.drawable.no_img_big).into(image);
                if(mEvent.getPhotos().size() >1){
                    tvView.setVisibility(View.VISIBLE);
                }
                tvView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (layout_image.getVisibility() == View.VISIBLE) {
                            layout_image.setVisibility(View.GONE);
                            tvView.setText(R.string.view_more);
                            image.setVisibility(View.VISIBLE);
                            tvView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.y32, 0, 0, 0);
                        } else {
                            layout_image.setVisibility(View.VISIBLE);
                            image.setVisibility(View.GONE);
                            tvView.setText(R.string.view_hide);
                            tvView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.y33, 0, 0, 0);
                        }
                    }
                });


                View view = null;
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                for (final MPhoto item : mEvent.getPhotos()) {
                    view = inflater.inflate(R.layout.item_image, null);
                    ImageView thumbNail = (ImageView) view.findViewById(R.id.imgProgram);
                    try {
                        Picasso.with(getApplicationContext()).load(item.url.indexOf("http") < 0 ? "http://" : item.url + item.name).fit().centerInside().error(R.drawable.no_img_big).into(thumbNail);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    layout_image.addView(view);
                }
            }
        }
        Preferences preferences = new Preferences(mContext);
        GetRetrofit().create(ServiceAPI.class)
                .getEventClient(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , mEvent.getEvent_id()
                        ,mClient.getClient_id()
                )
                .enqueue(new Callback<MAPIResponse<MEvent>>() {
                    @Override
                    public void onResponse(Call<MAPIResponse<MEvent>> call, Response<MAPIResponse<MEvent>> response) {
                        LogUtils.api(TAG, call, (response.body()));
                        box.hideAll();
                        TokenUtils.checkToken(mContext,response.body().getErrors());

                        //get image --
                        if(mEvent.getPhotos().size()== 0) {
                            mEvent = response.body().getResult();
                            if(mEvent.getPhotos().size()> 0) {
                                Picasso.with(getApplicationContext()).load(mEvent.getPhotos().get(0).url.indexOf("http") < 0 ? "http://" : mEvent.getPhotos().get(0).url + mEvent.getPhotos().get(0).name).fit().centerInside().error(R.drawable.no_img_big).into(image);
                                if (mEvent.getPhotos().size() > 1) {
                                    tvView.setVisibility(View.VISIBLE);
                                }
                                tvView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (layout_image.getVisibility() == View.VISIBLE) {
                                            layout_image.setVisibility(View.GONE);
                                            tvView.setText(R.string.view_more);
                                            image.setVisibility(View.VISIBLE);
                                            tvView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.y32, 0, 0, 0);
                                        } else {
                                            layout_image.setVisibility(View.VISIBLE);
                                            image.setVisibility(View.GONE);
                                            tvView.setText(R.string.view_hide);
                                            tvView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.y33, 0, 0, 0);
                                        }
                                    }
                                });


                                View view = null;
                                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                for (final MPhoto item : mEvent.getPhotos()) {
                                    view = inflater.inflate(R.layout.item_image, null);
                                    ImageView thumbNail = (ImageView) view.findViewById(R.id.imgProgram);
                                    try {
                                        Picasso.with(getApplicationContext()).load(item.url.indexOf("http") < 0 ? "http://" : item.url + item.name).fit().centerInside().error(R.drawable.no_img_big).into(thumbNail);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    layout_image.addView(view);
                                }
                            }
                        }
                        mEvent = response.body().getResult();
                        switch (response.body().getResult().getEvent_detail_status_id()){
                            case 1:
                                status.setText(getString(R.string.invite));
                                break;
                            case 2:
                                status.setText(getString(R.string.cancel));
                                break;
                            case 3:
                                status.setText(getString(R.string.confirm));
                                break;
                            case 4:
                                status.setText(getString(R.string.attend));
                                break;
                            default:
                                break;
                        }
                        name.setText(mEvent.getEvent_name());
                        date.setText(getString(R.string.date)+ ": "+ mEvent.getDate_begin());
                        address.setText(getString(R.string.address_point)+ ": "+ mEvent.getAddress());
                        number.setText(getString(R.string.number_invite)+ ": "+ Utils.formatCurrency(mEvent.getNum_client()));

                        description.setText(Html.fromHtml(mEvent.getEvent_content()));
                    }

                    @Override
                    public void onFailure(Call<MAPIResponse<MEvent>> call, Throwable t) {
                        LogUtils.d(TAG, "getUserActivities ", t.toString());
                        box.hideAll();
                    }
                });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_event, menu);
        if(ShowMenu){
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.invite)
                    menu.getItem(i).setVisible(true);
                if (menu.getItem(i).getItemId() == R.id.cancel)
                    menu.getItem(i).setVisible(true);
                if (menu.getItem(i).getItemId() == R.id.confirm)
                    menu.getItem(i).setVisible(true);
                if (menu.getItem(i).getItemId() == R.id.attend)
                    menu.getItem(i).setVisible(true);
            }

        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.invite:
                    setEventClientStatus(1);
                return true;
            case R.id.cancel:
                setEventClientStatus(2);
                return true;
            case R.id.confirm:
                setEventClientStatus(3);
                return true;
            case R.id.attend:
                setEventClientStatus(4);
                return true;
            case R.id.user:

                return true;
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setEventClientStatus(final int i) {

        if(mEvent.getEvent_detail_status_id() ==0 && i > 1){
            Utils.showError(coordinatorLayout, R.string.require_invite_client);
        }else {
            Preferences preferences = new Preferences(mContext);
            GetRetrofit().create(ServiceAPI.class)
                    .setEventStatus(preferences.getStringValue(Constants.TOKEN, "")
                            , preferences.getIntValue(Constants.USER_ID, 0)
                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                            , mEvent.getEvent_id()
                            , i
                            , mClient.getClient_id()
                    )
                    .enqueue(new Callback<MAPIResponse<MMessager>>() {
                        @Override
                        public void onResponse(Call<MAPIResponse<MMessager>> call, Response<MAPIResponse<MMessager>> response) {
                            LogUtils.api(TAG, call, (response.body()));
                            box.hideAll();
                            TokenUtils.checkToken(mContext, response.body().getErrors());
                            if (response.body().isHasErrors()) {
                                switch (i) {
                                    case 1:
                                        Utils.showError(coordinatorLayout, R.string.invite_fail);
                                        break;
                                    case 2:
                                        Utils.showError(coordinatorLayout, R.string.cancel_fail);
                                        break;
                                    case 3:
                                        Utils.showError(coordinatorLayout, R.string.confirm_fail);
                                        break;
                                    case 4:
                                        Utils.showError(coordinatorLayout, R.string.attend_fail);
                                        break;
                                    default:
                                        break;
                                }
                            } else {
                                switch (i) {
                                    case 1:
                                        Utils.showDialogSuccess(mContext, R.string.invite_done);
                                        break;
                                    case 2:
                                        Utils.showDialogSuccess(mContext, R.string.cancel_done);
                                        break;
                                    case 3:
                                        Utils.showDialogSuccess(mContext, R.string.confirm_done);
                                        break;
                                    case 4:
                                        Utils.showDialogSuccess(mContext, R.string.attend_done);
                                        break;
                                    default:
                                        break;
                                }

                            }

                        }
                        @Override
                        public void onFailure(Call<MAPIResponse<MMessager>> call, Throwable t) {
                            LogUtils.d(TAG, "getUserActivities ", t.toString());
                            box.hideAll();
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {

    }
}
