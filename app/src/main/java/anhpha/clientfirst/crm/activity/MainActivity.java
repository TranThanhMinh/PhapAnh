package anhpha.clientfirst.crm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MUserDefault;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mc975 on 2/6/17.
 */
public class MainActivity extends BaseAppCompatActivity implements View.OnClickListener {
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.left_drawer)
    LinearLayout mDrawerList;
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
    @Bind(R.id.imageButton6)
    ImageButton imageButton6;
    @Bind(R.id.tvBar)
    TextView tvBar;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.ivClose)
    ImageButton ivClose;
    @Bind(R.id.ivMenu)
    ImageView ivMenu;

    @Bind(R.id.tvKpi)
    TextView tvKpi;
    @Bind(R.id.tvEvent)
    TextView tvEvent;
    @Bind(R.id.tvTracking)
    TextView tvTracking;
    @Bind(R.id.tvChangePass)
    TextView tvChangePass;
    @Bind(R.id.tvLogout)
    TextView tvLogout;
    @Bind(R.id.tvDebt)
    TextView tvDebt;
    @Bind(R.id.tvWorkWeek)
    TextView tvWorkWeek;
    @Bind(R.id.tvGeneral)
    TextView tvGeneral;
    Preferences prefs;


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Preferences preferences = new Preferences(mContext);
        GetRetrofit().create(ServiceAPI.class)
                .getUserDefault(preferences.getStringValue(Constants.TOKEN,"")
                        ,preferences.getIntValue(Constants.USER_ID,0)
                        ,preferences.getIntValue(Constants.PARTNER_ID,0)
                )
                .enqueue(new Callback<MAPIResponse<MUserDefault>>() {
                    @Override
                    public void onResponse(Call<MAPIResponse<MUserDefault>> call, Response<MAPIResponse<MUserDefault>> response) {
                        preferences.putBooleanValue(Constants.permission_cancel_order,response.body().getResult().isPermission_cancel_order());
                        preferences.putBooleanValue(Constants.permission_edit_client,response.body().getResult().isPermission_edit_client());
                        preferences.putBooleanValue(Constants.permission_tracking_manager,response.body().getResult().isPermission_tracking_manager());
                        for(MOrderStatus os : response.body().getResult().getOrder_status()){
                            switch (os.getOrder_status_id()){
                                case 1:
                                    preferences.putStringValue(Constants.ORDER_STATUS_1,os.getOrder_status_name());
                                    break;
                                case 2:
                                    preferences.putStringValue(Constants.ORDER_STATUS_2,os.getOrder_status_name());
                                    break;
                                case 3:
                                    preferences.putStringValue(Constants.ORDER_STATUS_3,os.getOrder_status_name());
                                    break;
                                case 4:
                                    preferences.putStringValue(Constants.ORDER_STATUS_4,os.getOrder_status_name());
                                    break;
                                case 5:
                                    preferences.putStringValue(Constants.ORDER_STATUS_5,os.getOrder_status_name());
                                    break;
                                case 6:
                                    preferences.putStringValue(Constants.ORDER_STATUS_6,os.getOrder_status_name());
                                    break;
                                case 7:
                                    preferences.putStringValue(Constants.ORDER_STATUS_7,os.getOrder_status_name());
                                    break;

                                default:
                                    break;
                            }

                        }

                        if (!response.body().getResult().isPermission_tracking_manager())
                            tvTracking.setVisibility(View.GONE);
                    }
                    @Override
                    public void onFailure(Call<MAPIResponse<MUserDefault>> call, Throwable t) {

                    }
                });
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //set up
        mDrawerList.getLayoutParams().width = Utils.getWidth(mContext) - 100;
        prefs = new Preferences(mContext);
        tvBar.setText(prefs.getStringValue(Constants.PARTNER_NAME, ""));
        tvName.setText(prefs.getStringValue(Constants.USER_NAME, ""));

        //onclick
        ivMenu.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
        imageButton5.setOnClickListener(this);
        imageButton6.setOnClickListener(this);
        tvKpi.setOnClickListener(this);
        tvEvent.setOnClickListener(this);
        tvTracking.setOnClickListener(this);
        tvChangePass.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        tvGeneral.setOnClickListener(this);
        tvWorkWeek.setOnClickListener(this);
        tvDebt.setOnClickListener(this);

        Picasso.with(getApplicationContext()).load(R.mipmap.ic_crm_74).resize(500,500).centerInside().error(R.drawable.no_img_big).skipMemoryCache().into(imageButton1);
        Picasso.with(getApplicationContext()).load(R.mipmap.ic_crm_75).resize(500,500).centerInside().error(R.drawable.no_img_big).skipMemoryCache().into(imageButton2);
        Picasso.with(getApplicationContext()).load(R.mipmap.ic_crm_76).resize(500,500).centerInside().error(R.drawable.no_img_big).skipMemoryCache().into(imageButton3);
        Picasso.with(getApplicationContext()).load(R.mipmap.ic_crm_79).resize(500,500).centerInside().error(R.drawable.no_img_big).skipMemoryCache().into(imageButton4);
        Picasso.with(getApplicationContext()).load(R.mipmap.ic_crm_77).resize(500,500).centerInside().error(R.drawable.no_img_big).skipMemoryCache().into(imageButton5);
        Picasso.with(getApplicationContext()).load(R.mipmap.ic_crm_100).resize(500,500).centerInside().error(R.drawable.no_img_big).skipMemoryCache().into(imageButton6);

    }

    @Override
    public void onClick(View view) {

        mDrawerLayout.closeDrawers();

        switch (view.getId()){
            case R.id.ivMenu:
                mDrawerLayout.openDrawer(mDrawerList);
                break;
            case R.id.ivClose:
                break;
            case R.id.imageButton1:
                Intent it1 = new Intent(mContext,ActivityUserActivity.class);
                startActivity(it1);
                break;
            case R.id.imageButton2:
                Intent it2 = new Intent(mContext,ReportActivity.class);
                startActivity(it2);
                break;
            case R.id.imageButton3:
                Intent it3 = new Intent(mContext,WorksActivity.class);
                startActivity(it3);
                break;
            case R.id.imageButton4:
                Intent it4 = new Intent(mContext,OrdersActivity.class);
                startActivity(it4);
                break;
            case R.id.imageButton5:
                Intent it5 = new Intent(mContext,ClientsActivity.class);
                startActivity(it5);
                break;
            case R.id.imageButton6:
                Intent it6 = new Intent(mContext,Focus_activity.class);
                startActivity(it6);
                break;
            case R.id.tvKpi:
                Intent it7 = new Intent(mContext,KPIActivity.class);
                startActivity(it7);
                break;
            case R.id.tvEvent:
                Intent it8 = new Intent(mContext,EventsActivity.class);
                startActivity(it8);
                break;
            case R.id.tvTracking:
                Intent it9 = new Intent(mContext,TrackingActivity.class);
                startActivity(it9);
                break;
            case R.id.tvChangePass:
                Intent it10 = new Intent(mContext, ChangPassActivity.class);
                startActivity(it10);
                break;
            case R.id.tvWorkWeek:
                Intent it120 = new Intent(mContext, CalendarActivitiesActivity.class);
                startActivity(it120);
                break;
            case R.id.tvDebt:
                Intent it122 = new Intent(mContext, OrdersDebtActivity.class);
                startActivity(it122);
                break;
            case R.id.tvGeneral:
                Intent it103 = new Intent(mContext, GeneralActivity.class);
                startActivityForResult(it103, Constants.KEY_PUT_RESTART_LANGUAGE);
                break;
            case R.id.tvLogout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getResources().getString(R.string.alert));
                builder.setCancelable(true);
                builder.setMessage(getResources().getString(R.string.logout_message));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        prefs.putBooleanValue(Constants.USER_LOGGED_IN, true);
                        prefs.putIntValue(Constants.USER_ID, 0);

                        Intent intent = new Intent(mContext,LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.no), null);
                builder.show();
                break;
            default:
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.KEY_PUT_RESTART_LANGUAGE) {
            Intent intent = new Intent(mContext, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
