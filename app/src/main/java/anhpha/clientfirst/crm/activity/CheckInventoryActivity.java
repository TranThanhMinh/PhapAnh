package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.OrderAdapter;
import anhpha.clientfirst.crm.adapter.OrderViewAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.AdapterInterface;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.model.MOrder;
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

public class CheckInventoryActivity extends BaseAppCompatActivity implements AdapterInterface,Callback<MAPIResponse<List<MContract>>>, View.OnClickListener  {
    @Bind(R.id.rvActivities)
    ListView rvActivities;
    @Bind(R.id.btAdd)
    Button btAdd;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.tvClientName)
    TextView tvClientName;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.tvAmount)
    TextView tvAmount;

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    MOrder mOrder = new MOrder();
    MClient mClient = new MClient();
    List<MContract> MContracts = new ArrayList<>();
    List<MContract> MContractsOld = new ArrayList<>();
    List<MContract> MContractsNew = new ArrayList<>();
    OrderAdapter orderAdapter ;
    OrderViewAdapter orderViewAdapter;
    Preferences preferences ;

    EditText etUnit1;
    EditText etUnit2;
    EditText etNote;
    boolean is_edit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_check_ineventory);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_check_inventory);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        btAdd.setOnClickListener(this);


        mClient = (MClient) getIntent().getSerializableExtra("mClient");
        mOrder = (MOrder) getIntent().getSerializableExtra("mOrder");

        if(mOrder == null)
            mOrder = new MOrder();
        else {
            btAdd.setVisibility(View.GONE);
            GetRetrofit().create(ServiceAPI.class)
                    .getInventory(preferences.getStringValue(Constants.TOKEN, "")
                            , preferences.getIntValue(Constants.USER_ID, 0)
                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                            , mOrder.getInventory_contract_client_id()
                    )
                    .enqueue(new Callback<MAPIResponse<MOrder>>() {
                        @Override
                        public void onResponse(Call<MAPIResponse<MOrder>> call, Response<MAPIResponse<MOrder>> response) {
                            LogUtils.api(TAG, call, (response.body()));
                            box.hideAll();
                            TokenUtils.checkToken(mContext,response.body().getErrors());
                            mOrder = response.body().getResult();
                            LoadOrderView();
                        }
                        @Override
                        public void onFailure(Call<MAPIResponse<MOrder>> call, Throwable t) {
                            Utils.showError(coordinatorLayout, R.string.inventory_fail);
                            LogUtils.d(TAG, "getUserActivities ", t.toString());
                            box.hideAll();
                        }
                    });
            box.showLoadingLayout();

            LogUtils.d(TAG, "getUserActivities ", "start");
        }

        if(mClient == null){
            mClient = new MClient();
        }else {
            tvClientName.setText(mClient.getClient_name());
            tvAddress.setText(mClient.getAddress());
            if (mClient.getAddress() != null && !mClient.getAddress().isEmpty()) {
                tvAddress.setVisibility(View.VISIBLE);
            }
            LoadContract();
        }
        if(mClient.getClient_id()==0){
            tvClientName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(mContext,ChooseClientsActivity.class), Constants.RESULT_CLIENT);
                }
            });
        }
        orderAdapter = new OrderAdapter(mContext,MContracts,this);
        orderViewAdapter = new OrderViewAdapter(mContext,MContracts);
        rvActivities.setAdapter(orderAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);
        if(mOrder.getInventory_contract_client_id()>0){
            for (int i = 0; i < menu.size(); i++) {
                if(menu.getItem(i).getItemId() == R.id.done)
                    menu.getItem(i).setVisible(false);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                if(mClient.getClient_id() > 0 && mOrder.getContracts().size() > 0) {
                GetRetrofit().create(ServiceAPI.class)
                        .setInventory(preferences.getStringValue(Constants.TOKEN, "")
                                , preferences.getIntValue(Constants.USER_ID, 0)
                                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                , mClient.getClient_id()
                                , mOrder
                        )
                        .enqueue(new Callback<MAPIResponse<MOrder>>() {
                            @Override
                            public void onResponse(Call<MAPIResponse<MOrder>> call, Response<MAPIResponse<MOrder>> response) {
                                LogUtils.api(TAG, call, (response.body()));
                                box.hideAll();
                                TokenUtils.checkToken(mContext,response.body().getErrors());
                                if(response.body().isHasErrors()){
                                    Utils.showError(coordinatorLayout, R.string.inventory_fail);
                                } else {
                                    Utils.showDialogSuccess(mContext, R.string.inventory_done);

                                }
                            }
                            @Override
                            public void onFailure(Call<MAPIResponse<MOrder>> call, Throwable t) {
                                Utils.showError(coordinatorLayout, R.string.inventory_fail);
                                LogUtils.d(TAG, "getUserActivities ", t.toString());
                                box.hideAll();
                            }
                        });
                box.showLoadingLayout();

                LogUtils.d(TAG, "getUserActivities ", "start");
                }else{
                    if(mClient.getClient_id() > 0)
                        Utils.showError(coordinatorLayout, R.string.require_contract);
                    else
                        Utils.showError(coordinatorLayout, R.string.require_client);
                }
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<List<MContract>>> call, Response<MAPIResponse<List<MContract>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        MContracts = response.body().getResult();

    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MContract>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btAdd:
                if(mClient.getClient_id() > 0) {
                    Intent it = new Intent(mContext, ContractsActivity.class);
                    it.putExtra("MContracts", (Serializable) MContracts);
                    startActivityForResult(it, Constants.RESULT_PRODUCT);
                }else{
                    Utils.showError(coordinatorLayout, R.string.require_client);
                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Constants.RESULT_PRODUCT) {
            MContractsNew = (List<MContract>) data.getSerializableExtra("MContractsNew");

            MContractsOld = new ArrayList<>();
            for (MContract p : mOrder.getContracts()) {
                MContractsOld.add(p);
            }
            for (MContract p : MContractsNew) {
                int count = 0;
                for (MContract p2 : MContractsOld) {
                    if (p.getContract_id() == p2.getContract_id() && p.getNumber() > 0) {
                        p2.setNumber(p2.getNumber() + p.getNumber());
                        count++;
                        break;
                    }
                }
                if (count == 0 && p.getNumber() > 0) {
                    MContractsOld.add(p);
                }
            }
            mOrder.setContracts(MContractsOld);
            LoadOrder();

        }else if(resultCode == Constants.RESULT_CLIENT)
        {
            mClient = (MClient) data.getSerializableExtra("mClient");
            tvClientName.setText(mClient.getClient_name());
            tvAddress.setText(mClient.getAddress());
            if (mClient.getAddress() != null && !mClient.getAddress().isEmpty()) {
                tvAddress.setVisibility(View.VISIBLE);
            }
            tvClientName.setOnClickListener(this);
            LoadContract();
        }
    }
    private void LoadOrder() {
        orderAdapter = new OrderAdapter(mContext,mOrder.getContracts(),this);
        rvActivities.setAdapter(orderAdapter);
        double amount = 0;
        for (MContract d : mOrder.getContracts()){
            MContract MContract = Utils.getPriceContract(d,mContext);
            amount += MContract.getAmount_price();
        }
        mOrder.setAmount(amount);
        tvAmount.setText(getString(R.string.total_amount_i)+ " "+ Utils.formatCurrency(amount));
    }
    private void LoadOrderView() {
        orderViewAdapter.setActivityItemList(mOrder.getContracts());
        orderViewAdapter.notifyDataSetChanged();
        rvActivities.setAdapter(orderViewAdapter);
        double amount = 0;
        for (MContract d : mOrder.getContracts()){
            MContract MContract = Utils.getPriceContract(d,mContext);
            amount += MContract.getAmount_price();
        }
        mOrder.setAmount(amount);
        tvAmount.setText(getString(R.string.total_amount_i)+ " "+ Utils.formatCurrency(amount));
    }

    private void LoadContract() {

        GetRetrofit().create(ServiceAPI.class)
                .getContractByGroup(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        ,0
                        ,mClient.getClient_id()
                )
                .enqueue(this);
        box.showLoadingLayout();

        LogUtils.d(TAG, "getUserActivities ", "start");

    }

    @Override
    public void buttonPressed(int i, final int position) {
        switch (i) {
            case 1:
//                final MContract MContract = Utils.getPriceContract(mOrder.getContracts().get(position),mContext);
//
//                // custom dialog
//                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//                // ...Irrelevant code for customizing the buttons and title
//                LayoutInflater inflater = this.getLayoutInflater();
//                View dialogView = inflater.inflate(R.layout.dialog_contract, null);
//                dialogBuilder.setView(dialogView);
//                dialogBuilder.setTitle(MContract.getContract_name());
//                dialogBuilder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        double unit_1 = Utils.tryParseDouble(etUnit1.getText().toString());
//                        double unit_2 = Utils.tryParseDouble(etUnit2.getText().toString());
//                        String note = etNote.getText().toString();
//
//                        if (MContract.getNumber_in_group() > 1) {
//                            orderAdapter.getItem(position).setNumber(unit_1 + (unit_2 * MContract.getNumber_in_group()));
//                        } else {
//                            orderAdapter.getItem(position).setNumber(unit_1);
//                        }
//                        orderAdapter.getItem(position).setNote(note);
//                        rvActivities.setAdapter(orderAdapter);
//                        LoadOrder();
//                        Utils.setListViewHeightBasedOnChildren(rvActivities);
//                    }
//                });
//                dialogBuilder.setNegativeButton(getResources().getString(R.string.no), null);
//                etUnit1 = (EditText) dialogView.findViewById(R.id.etUnit1);
//                etUnit2 = (EditText) dialogView.findViewById(R.id.etUnit2);
//                etNote = (EditText) dialogView.findViewById(R.id.etNote);
//                TextView tvNameUnit1 = (TextView) dialogView.findViewById(R.id.tvNameUnit1);
//                TextView tvNameUnit2 = (TextView) dialogView.findViewById(R.id.tvNameUnit2);
//                TextView tvPrice1 = (TextView) dialogView.findViewById(R.id.tvPrice1);
//                TextView tvPrice2 = (TextView) dialogView.findViewById(R.id.tvPrice2);
//                if(MContract.getNumber_in_group() > 1){
//                    tvNameUnit2.setVisibility(View.VISIBLE);
//                    tvPrice2.setVisibility(View.VISIBLE);
//                    etUnit2.setVisibility(View.VISIBLE);
//                }
//                etUnit1.setText(MContract.getNumber().intValue() + "");
//                etUnit2.setText(MContract.getNumber_group().intValue() + "");
//                etNote.setText(MContract.getNote());
//                tvPrice1.setText(Utils.formatCurrency(MContract.getPrice()));
//                tvPrice2.setText(Utils.formatCurrency(MContract.getPrice_group()));
//                tvNameUnit1.setText(MContract.getContract_unit_name());
//                tvNameUnit2.setText(MContract.getContract_unit_group_name());
//
//                AlertDialog alertDialog = dialogBuilder.create();
//                alertDialog.show();

                break;
            case 3:
                mOrder.getContracts().remove(position);
                orderAdapter = new OrderAdapter(mContext,mOrder.getContracts(),this);
                rvActivities.setAdapter(orderAdapter);
                LoadOrder();

                break;
            default:
                break;
        }
    }
}
