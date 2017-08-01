package anhpha.clientfirst.crm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.OrderAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.AdapterInterface;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.model.MId;
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

public class OrderActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<List<MContract>>>, View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, AdapterInterface {

    @Bind(R.id.btAdd)
    Button btAdd;
    @Bind(R.id.spOrderStatus)
    Spinner spOrderStatus;
    @Bind(R.id.rvActivities)
    ListView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.tvClientName)
    TextView tvClientName;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.etPrepay)
    EditText etPrepay;
    @Bind(R.id.textView21)
    TextView textView21;
    @Bind(R.id.textView22)
    TextView textView22;
    @Bind(R.id.textView23)
    TextView textView23;
    @Bind(R.id.note)
    EditText noteO;

    @Bind(R.id.etOrderCode)
    EditText etOrderCode;

    @Bind(R.id.etOrderCodeParent)
    EditText etOrderCodeParent;

    EditText note;
    EditText value;
    EditText number;
    TextView total;

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    MOrder mOrder = new MOrder();
    MClient mClient = new MClient();
    List<MContract> MContracts = new ArrayList<>();
    List<MContract> MContractsOld = new ArrayList<>();
    List<MContract> MContractsNew = new ArrayList<>();
    OrderAdapter orderAdapter;
    String date = "";
    int type = 1;

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day;
    Preferences preferences;

    double totalAmountContract = 0;
    double vat = 0;
    double discountContract = 0;
    double discountOrder = 0;
    double prePay = 0;
    boolean is_edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_order);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_order);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        tvDate.setOnClickListener(this);
        orderAdapter = new OrderAdapter(mContext, mOrder.getOrder_detail_contracts(), this);

        mOrder = (MOrder) getIntent().getSerializableExtra("mOrder");
        mClient = (MClient) getIntent().getSerializableExtra("mClient");

        if (mOrder == null) {
            mOrder = new MOrder();
            mOrder.setOrder_type_id(1);
            List<String> string=new ArrayList<String>();

            string.add( preferences.getStringValue(Constants.ORDER_STATUS_1,""));
            ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
            spOrderStatus.setAdapter(adapter);
        } else {
            mClient = new MClient();
            mClient.setClient_id(mOrder.getClient_id());
            mClient.setClient_name(mOrder.getClient_name());
            mClient.setAddress(mOrder.getAddress());
            mOrder.setDelivery_date(mOrder.getDelivery_date() == null ? "" : mOrder.getDelivery_date());
            tvDate.setText(mOrder.getDelivery_date().isEmpty() ? getString(R.string.choose_date) : mOrder.getDelivery_date());
            date = mOrder.getDelivery_date();
            noteO.setText(mOrder.getNote());
            LoadOrder();
            is_edit = true;
            spOrderStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int status = mOrder.getOrder_status_id();
                    if(position == 1){
                    switch (mOrder.getOrder_status_id()) {
                        case 1:
                            status = 2;
                            break;
                        case 2:
                            status = 4;
                            break;
                        case 4:
                            status = 6;
                            break;
                        case 6:
                            status = 7;
                            break;
                        default:
                            break;
                    }
                        GetRetrofit().create(ServiceAPI.class)
                                .setStatusOrder(preferences.getStringValue(Constants.TOKEN, "")
                                        , preferences.getIntValue(Constants.USER_ID, 0)
                                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                        , mOrder.getOrder_contract_id()
                                        , status
                                )
                                .enqueue(new Callback<MAPIResponse<MId>>() {
                                    @Override
                                    public void onResponse(Call<MAPIResponse<MId>> call, Response<MAPIResponse<MId>> response) {
                                        LogUtils.api(TAG, call, (response.body()));
                                        box.hideAll();
                                        TokenUtils.checkToken(mContext,response.body().getErrors());
                                        if(!response.body().isHasErrors()){
                                            Utils.showSuccess(coordinatorLayout, R.string.change_order_status_done);
                                        }
                                        else{
                                            Utils.showError(coordinatorLayout, R.string.delete_order_fail);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<MAPIResponse<MId>> call, Throwable t) {
                                        LogUtils.d(TAG, "getUserActivities ", t.toString());
                                        box.hideAll();
                                        Utils.showError(coordinatorLayout, R.string.delete_order_fail);
                                    }
                                });
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            List<String> string=new ArrayList<String>();
            ArrayAdapter adapter;
            switch (mOrder.getOrder_status_id()){
                case 1:
                    string=new ArrayList<String>();
                    string.add( preferences.getStringValue(Constants.ORDER_STATUS_1,""));
                    string.add( preferences.getStringValue(Constants.ORDER_STATUS_2,""));
                    adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
                    spOrderStatus.setAdapter(adapter);

                    break;
                case 2:
                    string=new ArrayList<String>();
                    string.add( preferences.getStringValue(Constants.ORDER_STATUS_2,""));
                    string.add( preferences.getStringValue(Constants.ORDER_STATUS_4,""));
                    adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
                    spOrderStatus.setAdapter(adapter);

                    break;
                case 4:
                    string=new ArrayList<String>();
                    string.add( preferences.getStringValue(Constants.ORDER_STATUS_4,""));
                    string.add( preferences.getStringValue(Constants.ORDER_STATUS_6,""));
                    adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
                    spOrderStatus.setAdapter(adapter);

                    break;
                case 6:
                    string=new ArrayList<String>();
                    string.add( preferences.getStringValue(Constants.ORDER_STATUS_6,""));
                    string.add( preferences.getStringValue(Constants.ORDER_STATUS_7,""));
                    adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
                    spOrderStatus.setAdapter(adapter);
                    break;
                case 7:
                    string=new ArrayList<String>();
                    string.add( preferences.getStringValue(Constants.ORDER_STATUS_7,""));
                    adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
                    spOrderStatus.setAdapter(adapter);
                    break;
                case 3:
                    string=new ArrayList<String>();
                    string.add( preferences.getStringValue(Constants.ORDER_STATUS_3,""));
                    adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
                    spOrderStatus.setAdapter(adapter);
                    break;
                case 5:
                    string=new ArrayList<String>();
                    string.add( preferences.getStringValue(Constants.ORDER_STATUS_5,""));
                    adapter = new ArrayAdapter<>(this, R.layout.simple_spinner_item, string);
                    spOrderStatus.setAdapter(adapter);
                    break;
                default:
                    break;
            }

        }
        if (mClient == null) {
            mClient = new MClient();
        } else {
            tvClientName.setText(mClient.getClient_name());
            tvAddress.setText(mClient.getAddress());
            if (mOrder.getAddress() != null && !mClient.getAddress().isEmpty()) {
                tvAddress.setVisibility(View.VISIBLE);
            }
        }
        if (mClient.getClient_id() == 0) {
            tvClientName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(mContext, ChooseClientsActivity.class), Constants.RESULT_CLIENT);
                }
            });
        }
        btAdd.setOnClickListener(this);

        rvActivities.setAdapter(orderAdapter);
        Utils.setListViewHeightBasedOnChildren(rvActivities);

        LoadContract();
        etOrderCode.addTextChangedListener(new TextWatcher() {
                                               @Override
                                               public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                               }

                                               @Override
                                               public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                                   mOrder.setOrder_contract_code(charSequence.toString());
                                               }

                                               @Override
                                               public void afterTextChanged(Editable editable) {

                                               }
                                           });
        etOrderCodeParent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mOrder.setOrder_contract_code_parent(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        etPrepay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                etPrepay.removeTextChangedListener(this);
                double prePay = Utils.tryParseDouble(editable.toString().replace(",", ""));
                etPrepay.setText(Utils.formatCurrency(prePay));
                textView22.setText(Utils.formatCurrency((prePay)));
                mOrder.setPrepay(prePay);
                textView23.setText(Utils.formatCurrency(totalAmountContract - prePay));
                etPrepay.setSelection(etPrepay.getText().length());
                etPrepay.addTextChangedListener(this);
            }
        });
    }
    private void LoadContract() {
        if (mClient.getClient_id() > 0) {
            GetRetrofit().create(ServiceAPI.class)
                    .getContractByGroup(preferences.getStringValue(Constants.TOKEN, "")
                            , preferences.getIntValue(Constants.USER_ID, 0)
                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                            , 0
                            , mClient.getClient_id()
                    )
                    .enqueue(this);
            setProgressBarIndeterminateVisibility(true);
            setProgressBarVisibility(true);
            box.showLoadingLayout();

            LogUtils.d(TAG, "getUserActivities ", "start");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                if (mClient.getClient_id() > 0 && mOrder.getOrder_detail_contracts().size() > 0) {

                    box.showLoadingLayout();
                    List<MContract> MContracts = mOrder.getOrder_detail_contracts();
                    int id = mOrder.getOrder_contract_id();
                    mOrder = new MOrder();
                    mOrder.setOrder_detail_contracts(MContracts);
                    mOrder.setOrder_contract_id(id);
                    mOrder.setDelivery_date(date);
                    mOrder.setAmount_payment(totalAmountContract);
                    mOrder.setOrder_amount(totalAmountContract);
                    mOrder.setOrder_type_id(type);
                    mOrder.setPartner_id(preferences.getIntValue(Constants.PARTNER_ID, 0));
                    mOrder.setAmount_non_discount(totalAmountContract);
                    mOrder.setUser_id(preferences.getIntValue(Constants.USER_ID, 0));
                    mOrder.setClient_id(mClient.getClient_id());
                    mOrder.setClient_name(mClient.getClient_name());
                    mOrder.setAddress(mClient.getAddress());

                    mOrder.setOrder_status_id(1);
                    mOrder.setPrepay(Utils.tryParseDouble(etPrepay.getText().toString()));
                    mOrder.setOrder_contract_code((etOrderCode.getText().toString()));
                    mOrder.setOrder_contract_code_parent((etOrderCodeParent.getText().toString()));
                    mOrder.setPayment_type_id(type);
                    mOrder.setNote(noteO.getText().toString());
                    GetRetrofit().create(ServiceAPI.class)
                            .setOrder(preferences.getStringValue(Constants.TOKEN, "")
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
                                    if (response.body().isHasErrors()) {
                                        if (!is_edit)
                                            Utils.showError(coordinatorLayout, R.string.order_fail);
                                        else
                                            Utils.showError(coordinatorLayout, R.string.order_edit_fail);
                                    } else {
                                        if (!is_edit)
                                            Utils.showDialogSuccess(mContext, R.string.order_done);
                                        else
                                            Utils.showDialogSuccess(mContext, R.string.order_edit_done);
                                    }
                                }

                                @Override
                                public void onFailure(Call<MAPIResponse<MOrder>> call, Throwable t) {
                                    LogUtils.d(TAG, "getUserActivities ", t.toString());
                                    box.hideAll();
                                    if (!is_edit)
                                        Utils.showError(coordinatorLayout, R.string.order_fail);
                                    else
                                        Utils.showError(coordinatorLayout, R.string.order_edit_fail);
                                }
                            });
                } else {
                    if (mClient.getClient_id() > 0)
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
        switch (view.getId()) {
            case R.id.btAdd:
                if (mClient.getClient_id() > 0) {
                    Intent it = new Intent(mContext, ContractsActivity.class);
                    it.putExtra("MContracts", (Serializable) MContracts);
                    startActivityForResult(it, Constants.RESULT_PRODUCT);
                } else {
                    Utils.showError(coordinatorLayout, R.string.require_client);
                }
                break;

            case R.id.tvDate:
                calendar = Calendar.getInstance();

                Year = calendar.get(Calendar.YEAR);
                Month = calendar.get(Calendar.MONTH);
                Day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = DatePickerDialog.newInstance(
                        OrderActivity.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorApp));
                datePickerDialog.setCancelText(getString(R.string.no));
                datePickerDialog.setOkText(getString(R.string.yes));
                datePickerDialog.setTitle(getString(R.string.choose_date));
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
            default:
                break;
        }
    }

    private void LoadOrder() {
        orderAdapter.setActivityItemList(mOrder.getOrder_detail_contracts());
        orderAdapter.notifyDataSetChanged();
        Utils.setListViewHeightBasedOnChildren(rvActivities);

        mOrder.setClient_name(tvClientName.getText().toString());
        mOrder.setAddress(tvAddress.getText().toString());

        totalAmountContract = 0;
        vat = 0;
        discountContract = 0;
        discountOrder = 0;
        etPrepay.setText(Utils.formatCurrency(mOrder.getPrepay()));
        etOrderCode.setText((mOrder.getOrder_contract_code()));
        etOrderCodeParent.setText((mOrder.getOrder_contract_code_parent()));

        prePay = Utils.tryParseDouble(etPrepay.getText().toString());

        for (MContract pdd : mOrder.getOrder_detail_contracts()) {
            MContract d = Utils.getPriceContract(pdd, mContext);

            totalAmountContract += d.getAmount_price();

        }
        textView21.setText(Utils.formatCurrency(totalAmountContract));
        textView22.setText(Utils.formatCurrency((prePay)));
        textView23.setText(Utils.formatCurrency(totalAmountContract - prePay));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.RESULT_PRODUCT) {
            MContractsNew = (List<MContract>) data.getSerializableExtra("MContractsNew");

            MContractsOld = new ArrayList<>();
            for (MContract p : mOrder.getOrder_detail_contracts()) {
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
            mOrder.setOrder_detail_contracts(MContractsOld);
            LoadOrder();

        } else if (resultCode == Constants.RESULT_CLIENT) {
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Year, (Month + 1), Day);
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                OrderActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );

        tpd.setThemeDark(false);
        tpd.setAccentColor(getResources().getColor(R.color.colorApp));
        tpd.setTitle(getString(R.string.choose_time));
        tpd.setCancelText(getString(R.string.no));
        tpd.setOkText(getString(R.string.yes));
        tpd.show(getFragmentManager(), "DatePickerDialog");
        this.Month = Month + 1;
        this.Year = Year;
        this.Day = Day;

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        tvDate.setText((Month) + "/" + Day + "/" + Year + " " + hourOfDay + ":" + minute);
        date = tvDate.getText().toString();
    }

    @Override
    public void buttonPressed(int i, final int position) {
        switch (i) {
            case 1:
                final MContract mContract = Utils.getPriceContract(mOrder.getOrder_detail_contracts().get(position), mContext);
                // custom dialog
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                // ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_contract, null);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setTitle(mContract.getContract_name());
                dialogBuilder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        double unit_1 = Utils.tryParseDouble(number.getText().toString());
                        double unit_2 = Utils.tryParseDouble(value.getText().toString());
                        String snote = note.getText().toString();
                        orderAdapter.getItem(position).setNumber(unit_1);
                        orderAdapter.getItem(position).setPrice(unit_2);
                        orderAdapter.getItem(position).setNote(snote);
                        rvActivities.setAdapter(orderAdapter);
                        LoadOrder();
                        Utils.setListViewHeightBasedOnChildren(rvActivities);
                    }
                });
                dialogBuilder.setNegativeButton(getResources().getString(R.string.no), null);
                number = (EditText) dialogView.findViewById(R.id.number);
                value = (EditText) dialogView.findViewById(R.id.value);
                note = (EditText) dialogView.findViewById(R.id.note);
                total = (TextView) dialogView.findViewById(R.id.total);


                value.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        value.removeTextChangedListener(this);
                        double prePay = Utils.tryParseDouble(editable.toString().replace(",", ""));
                        value.setText(Utils.formatCurrency(prePay));
                        value.setSelection(value.getText().length());
                        value.addTextChangedListener(this);

                        total.setText(getString(R.string.total_amount_i)+ " " + Utils.formatCurrency(prePay * Utils.tryParseDouble(number.getText().toString())));
                    }
                });
                number.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        number.removeTextChangedListener(this);
                        double prePay = Utils.tryParseDouble(editable.toString().replace(",", ""));
                        number.setText(Utils.formatCurrency(prePay));
                        number.setSelection(number.getText().length());
                        number.addTextChangedListener(this);
                        total.setText(getString(R.string.total_amount_i)+ " " + Utils.formatCurrency(prePay * Utils.tryParseDouble(value.getText().toString())));
                    }
                });

                TextInputLayout aTIL = (TextInputLayout) dialogView.findViewById(R.id.input_layout_3);
                aTIL.setHint(mContract.getPrice_name().isEmpty()?mContext.getString(R.string.value):mContract.getPrice_name());
                value.setHint(mContract.getPrice_name().isEmpty()?mContext.getString(R.string.value):mContract.getPrice_name());
                number.setText(mContract.getNumber().intValue() + "");
                note.setText(mContract.getNote());
                value.setText(Utils.formatCurrency(mContract.getPrice()));
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();

                break;
            case 3:
                mOrder.getOrder_detail_contracts().remove(position);
                orderAdapter = new OrderAdapter(mContext, mOrder.getOrder_detail_contracts(), this);
                rvActivities.setAdapter(orderAdapter);
                LoadOrder();
                break;
            default:
                break;
        }
    }
}
