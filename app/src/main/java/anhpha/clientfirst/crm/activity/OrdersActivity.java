package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
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
import anhpha.clientfirst.crm.adapter.OrdersAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.RecyclerTouchListener;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.model.MRequestBody;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import anhpha.clientfirst.crm.utils.DynamicBox;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends BaseAppCompatActivity implements  RecyclerTouchListener.ClickListener, Callback<MAPIResponse<List<MOrder>>>, View.OnClickListener  {
    @Bind(R.id.textView0)
    TextView textView0;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.left_drawer)
    LinearLayout mDrawerList;

    @Bind(R.id.ibOpen)
    ImageButton ibOpen;
    @Bind(R.id.ibClose)
    ImageButton ibClose;

    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    DynamicBox box;
    OrdersAdapter activityAdapter;
    List<MOrder> mOrders;
    List<MOrder> list = new ArrayList<>();
    List<MOrder> list2 = new ArrayList<>();
    List<MId> mIds;
    Preferences preferences;
    int type;
    boolean load_order =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_orders);
        ButterKnife.bind(this);
         preferences = new Preferences(mContext);
        //set up
        mDrawerList.getLayoutParams().width = Utils.getWidth(mContext) - 100;
        setSupportActionBar(toolbar);
        ActionBar  actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_order);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        ibClose.setOnClickListener(this);
        ibOpen.setOnClickListener(this);

        textView0.setOnClickListener(this);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);

        RecyclerView.LayoutManager  mLayoutManager = new GridLayoutManager(mContext, 2);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvActivities, this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(load_order){
            MRequestBody mRequestBody = new MRequestBody();
            List<MId> mIds = new ArrayList<>();
            mIds.add(new MId(1));mIds.add(new MId(2));mIds.add(new MId(4));
            mIds.add(new MId(6));
            mRequestBody.setIds(mIds);


            GetRetrofit().create(ServiceAPI.class)
                    .getOrderByPartner(preferences.getStringValue(Constants.TOKEN, "")
                            , preferences.getIntValue(Constants.USER_ID, 0)
                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                            , mRequestBody
                    )
                    .enqueue(this);
            setProgressBarIndeterminateVisibility(true);
            setProgressBarVisibility(true);
            box.showLoadingLayout();

            LogUtils.d(TAG, "getUserActivities ", "start");
        }
        load_order = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_orders, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:
                Intent intent = new Intent(mContext,OrderActivity.class);
                startActivity(intent);

                return true;

            case R.id.user:
                startActivityForResult(new Intent(mContext, UsersActivity.class),Constants.RESULT_USERS);
                load_order = false;
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<List<MOrder>>> call, Response<MAPIResponse<List<MOrder>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        mOrders = response.body().getResult();
        list = new ArrayList<>();
        list.addAll(mOrders);
        LoadData(list);
    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MOrder>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibClose:
                mDrawerLayout.closeDrawers();
                break;
            case R.id.ibOpen:
                mDrawerLayout.openDrawer(mDrawerList);
                break;
            case R.id.textView0:
                type  = 0;
                LoadData(list);
                mDrawerLayout.closeDrawers();

                break;
            case R.id.textView1:
                type  = 1;
                list2 = new ArrayList<>();
                for (MOrder mOrder : list){
                    if (mOrder.getOrder_status_id() == type){
                        list2.add(mOrder);
                    }
                }
                LoadData(list2);
                mDrawerLayout.closeDrawers();

                break;
            case R.id.textView2:
                type  = 2;
                list2 = new ArrayList<>();
                for (MOrder mOrder : list){
                    if (mOrder.getOrder_status_id() == type){
                        list2.add(mOrder);
                    }
                }
                LoadData(list2);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.textView3:
                type  = 4;
                list2 = new ArrayList<>();
                for (MOrder mOrder : list){
                    if (mOrder.getOrder_status_id() == type){
                        list2.add(mOrder);
                    }
                }
                LoadData(list2);
                mDrawerLayout.closeDrawers();
                break;
            case R.id.textView4:
                type  = 6;
                list2 = new ArrayList<>();
                for (MOrder mOrder : list){
                    if (mOrder.getOrder_status_id() == type){
                        list2.add(mOrder);
                    }
                }
                LoadData(list2);
                mDrawerLayout.closeDrawers();
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
            list = new ArrayList<>();
            for (MOrder mTracking : mOrders){
                for (MId id : mIds) {
                    if (mTracking.getUser_id() == id.getId()){
                        list.add(mTracking);
                    }
                }
            }
            LoadData(list);
            load_order = false;

        }
    }

    private void LoadData(List<MOrder> list) {
        double order_status_0 = 0;
        double order_status_1 = 0;
        double order_status_2 = 0;
        double order_status_4 = 0;
        double order_status_6 = 0;
        double order_status_num_0 = 0;
        double order_status_num_1 = 0;
        double order_status_num_2 = 0;
        double order_status_num_4 = 0;
        double order_status_num_6 = 0;
        for(MOrder o : this.list){
            order_status_0 += o.getAmount_payment();
            order_status_num_0++;
            switch (o.getOrder_status_id()){
                case 1:
                    order_status_1 += o.getAmount_payment();
                    order_status_num_1++;
                    break;
                case 2:
                    order_status_2 += o.getAmount_payment();
                    order_status_num_2++;
                    break;
                case 4:
                    order_status_4 += o.getAmount_payment();
                    order_status_num_4++;
                    break;
                case 6:
                    order_status_6 += o.getAmount_payment();
                    order_status_num_6++;
                    break;
                default:
                    break;
            }
        }
        textView0.setText(getString(R.string.all) +" ("+Utils.formatCurrency(order_status_num_0)+") - "+ Utils.formatCurrency(order_status_0));
        textView1.setText(preferences.getStringValue(Constants.ORDER_STATUS_1,"") +" ("+Utils.formatCurrency(order_status_num_1)+") - "+ Utils.formatCurrency(order_status_1));
        textView2.setText(preferences.getStringValue(Constants.ORDER_STATUS_2,"") +" ("+Utils.formatCurrency(order_status_num_2)+") - "+ Utils.formatCurrency(order_status_2));
        textView3.setText(preferences.getStringValue(Constants.ORDER_STATUS_4,"") +" ("+Utils.formatCurrency(order_status_num_4)+") - "+ Utils.formatCurrency(order_status_4));
        textView4.setText(preferences.getStringValue(Constants.ORDER_STATUS_6,"") +" ("+Utils.formatCurrency(order_status_num_6)+") - "+ Utils.formatCurrency(order_status_6));

        activityAdapter = new OrdersAdapter(mContext,list);
        rvActivities.setAdapter(activityAdapter);
        activityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(mContext,OrderViewActivity.class);
        intent.putExtra("mOrder", activityAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
