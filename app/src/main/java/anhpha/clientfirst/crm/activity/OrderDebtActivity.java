package anhpha.clientfirst.crm.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.OrderDebtAdapter;
import anhpha.clientfirst.crm.adapter.OrderViewAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.model.MDebt;
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

public class OrderDebtActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<MOrder>>, View.OnClickListener,DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener  {
    @Bind(R.id.rvActivities)
    ListView rvActivities;
    @Bind(R.id.rv2)
    ListView rv2;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.tvClientName)
    TextView tvClientName;
    @Bind(R.id.tvAddress)
    TextView tvAddress;
    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.tvType)
    TextView tvType;
    @Bind(R.id.tvPrepay)
    TextView tvPrepay;
    @Bind(R.id.textView21)
    TextView textView21;
    @Bind(R.id.textView22)
    TextView textView22;
    @Bind(R.id.textView23)
    TextView textView23;
    @Bind(R.id.note)
    TextView note;
    @Bind(R.id.btDone)
    Button btDone;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;


    TextView date;
    EditText note_d;
    EditText value;
    RadioButton radioButton1;
    RadioButton radioButton2;

    MOrder mOrder = new MOrder();
    OrderViewAdapter orderAdapter ;
    Preferences preferences;
    int Year, Month, Day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_order_debt);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_order);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mOrder = (MOrder) getIntent().getSerializableExtra("mOrder");

        if(mOrder == null){
            mOrder = new MOrder();
        }else{

        }
        tvClientName.setText(mOrder.getClient_name());

        rv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                 if(mOrder.getDebts().get(position).getDebt_status_id() == 1) {
                    // custom dialog
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(OrderDebtActivity.this);
                    // ...Irrelevant code for customizing the buttons and title
                    LayoutInflater inflater = OrderDebtActivity.this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_contract_debt, null);
                    dialogBuilder.setView(dialogView);
                    dialogBuilder.setTitle(getString(R.string.get_money));
                    dialogBuilder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MDebt mDebt = mOrder.getDebts().get(position);
                            mDebt.setOrder_contract_id(mOrder.getOrder_contract_id());
                            mDebt.setUser_id(preferences.getIntValue(Constants.USER_ID, 0));
                            if (radioButton1.isChecked()) {
                                mDebt.setDate_plan_debt(date.getText().toString());
                                mDebt.setValue_plan_debt(Utils.tryParseDouble(value.getText().toString()));
                                mDebt.setNote((note.getText().toString()));
                                mDebt.setDebt_status_id(1);
                            } else {
                                mDebt.setDate_debt(date.getText().toString());
                                mDebt.setValue_debt(Utils.tryParseDouble(value.getText().toString()));
                                mDebt.setNote((note.getText().toString()));
                                mDebt.setDebt_status_id(2);
                            }

                            GetRetrofit().create(ServiceAPI.class)
                                    .setOrderDebt(preferences.getStringValue(Constants.TOKEN, "")
                                            , preferences.getIntValue(Constants.USER_ID, 0)
                                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                            , mDebt
                                    )
                                    .enqueue(new Callback<MAPIResponse<MDebt>>() {
                                        @Override
                                        public void onResponse(Call<MAPIResponse<MDebt>> call, Response<MAPIResponse<MDebt>> response) {
                                            LogUtils.api(TAG, call, (response.body()));
                                            box.hideAll();
                                            TokenUtils.checkToken(mContext, response.body().getErrors());
                                            getData();
                                        }

                                        @Override
                                        public void onFailure(Call<MAPIResponse<MDebt>> call, Throwable t) {
                                        }
                                    });
                            box.showLoadingLayout();
                        }
                    });

                    dialogBuilder.setNegativeButton(getResources().getString(R.string.no), null);
                    date = (TextView) dialogView.findViewById(R.id.date);
                    value = (EditText) dialogView.findViewById(R.id.value);
                    note = (EditText) dialogView.findViewById(R.id.note);
                    radioButton1 = (RadioButton) dialogView.findViewById(R.id.radioButton1);
                    radioButton2 = (RadioButton) dialogView.findViewById(R.id.radioButton2);
                    MDebt activityItem = mOrder.getDebts().get(position);
                    note.setText(activityItem.getNote());

                    date.setText(activityItem.getDebt_status_id() == 1 ? activityItem.getDate_plan_debt() : activityItem.getDate_debt());
                    value.setText(activityItem.getDebt_status_id() == 1 ? Utils.formatCurrency(activityItem.getValue_plan_debt()) : Utils.formatCurrency(activityItem.getValue_debt()));

                    radioButton1.setChecked(activityItem.getDebt_status_id() == 1 ? true : false);
                    radioButton2.setChecked(activityItem.getDebt_status_id() == 2 ? true : false);
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

                         }
                     });
                    radioButton1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            radioButton2.setChecked(false);
                            radioButton1.setChecked(true);
                        }
                    });
                    radioButton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            radioButton1.setChecked(false);
                            radioButton2.setChecked(true);
                        }
                    });
                    date.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar calendar = Calendar.getInstance();

                            Year = calendar.get(Calendar.YEAR);
                            Month = calendar.get(Calendar.MONTH);
                            Day = calendar.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                                    OrderDebtActivity.this, Year, Month, Day);
                            datePickerDialog.setThemeDark(false);

                            datePickerDialog.showYearPickerFirst(false);

                            datePickerDialog.setAccentColor(getResources().getColor(R.color.colorApp));
                            datePickerDialog.setCancelText(getString(R.string.no));
                            datePickerDialog.setOkText(getString(R.string.yes));
                            datePickerDialog.setTitle(getString(R.string.choose_date));
                            datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                        }
                    });
                    AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
    public void getData(){
        GetRetrofit().create(ServiceAPI.class)
                .getOrderDebt(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , mOrder.getOrder_contract_id()
                )
                .enqueue(this);
        box.showLoadingLayout();

        LogUtils.d(TAG, "getUserActivities ", "start");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                // custom dialog
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                // ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_contract_debt, null);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setTitle(getString(R.string.get_money));
                dialogBuilder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MDebt mDebt =  new MDebt();
                        mDebt.setOrder_contract_id(mOrder.getOrder_contract_id());
                        mDebt.setUser_id(preferences.getIntValue(Constants.USER_ID,0));
                        if(radioButton1.isChecked()){
                            mDebt.setDate_plan_debt(date.getText().toString());
                            mDebt.setDate_debt(date.getText().toString());
                            mDebt.setValue_plan_debt(Utils.tryParseDouble(value.getText().toString()));
                            mDebt.setNote((note.getText().toString()));
                            mDebt.setDebt_status_id(1);
                        }else{
                            mDebt.setDate_debt(date.getText().toString());
                            mDebt.setDate_plan_debt(date.getText().toString());
                            mDebt.setValue_debt(Utils.tryParseDouble(value.getText().toString()));
                            mDebt.setNote((note.getText().toString()));
                            mDebt.setDebt_status_id(2);
                        }
                        GetRetrofit().create(ServiceAPI.class)
                                .setOrderDebt(preferences.getStringValue(Constants.TOKEN, "")
                                        , preferences.getIntValue(Constants.USER_ID, 0)
                                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                        , mDebt
                                )
                                .enqueue(new Callback<MAPIResponse<MDebt>>() {
                                    @Override
                                    public void onResponse(Call<MAPIResponse<MDebt>> call, Response<MAPIResponse<MDebt>> response) {
                                        LogUtils.api(TAG, call, (response.body()));
                                        box.hideAll();
                                        TokenUtils.checkToken(mContext,response.body().getErrors());
                                        getData();
                                    }

                                    @Override
                                    public void onFailure(Call<MAPIResponse<MDebt>> call, Throwable t) {
                                    }
                                });
                        box.showLoadingLayout();
                    }
                });

                dialogBuilder.setNegativeButton(getResources().getString(R.string.no), null);
                date = (TextView) dialogView.findViewById(R.id.date);
                value = (EditText) dialogView.findViewById(R.id.value);
                note = (EditText) dialogView.findViewById(R.id.note);
                radioButton1 = (RadioButton) dialogView.findViewById(R.id.radioButton1);
                radioButton2 = (RadioButton) dialogView.findViewById(R.id.radioButton2);
                radioButton1.setChecked(true);
                date.setText(Utils.getDate());
                radioButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        radioButton2.setChecked(false);
                        radioButton1.setChecked(true);
                    }
                });
                radioButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        radioButton1.setChecked(false);
                        radioButton2.setChecked(true);
                    }
                });
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

                    }
                });
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();

                        Year = calendar.get(Calendar.YEAR);
                        Month = calendar.get(Calendar.MONTH);
                        Day = calendar.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                                OrderDebtActivity.this, Year, Month, Day);
                        datePickerDialog.setThemeDark(false);

                        datePickerDialog.showYearPickerFirst(false);

                        datePickerDialog.setAccentColor(getResources().getColor(R.color.colorApp));
                        datePickerDialog.setCancelText(getString(R.string.no));
                        datePickerDialog.setOkText(getString(R.string.yes));
                        datePickerDialog.setTitle(getString(R.string.choose_date));
                        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                    }
                });
                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                return true;

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<MOrder>> call, Response<MAPIResponse<MOrder>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        mOrder = response.body().getResult();
        orderAdapter = new OrderViewAdapter(mContext,mOrder.getOrder_detail_contracts());
        rvActivities.setAdapter(orderAdapter);
        rvActivities.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.setListViewHeightBasedOnChildren(rvActivities);
            }
        }, 500);
        Utils.setListViewHeightBasedOnChildren(rvActivities);

        OrderDebtAdapter adapter = new OrderDebtAdapter(mContext,mOrder.getDebts());
        rv2.setAdapter(adapter);
        rv2.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.setListViewHeightBasedOnChildren(rv2);
            }
        }, 500);
        Utils.setListViewHeightBasedOnChildren(rv2);

        if(mOrder.getAddress()!=null && !mOrder.getAddress().isEmpty()){
            tvAddress.setVisibility(View.VISIBLE);
            tvAddress.setText(mOrder.getAddress());

        }
        if(mOrder.getOrder_type_id() == 2){
            btDone.setVisibility(View.VISIBLE);
            btDone.setOnClickListener(this);
        }

        note.setText(getString(R.string.note) + ": " + mOrder.getNote());
        mOrder.setClient_name(tvClientName.getText().toString());
        tvDate.setText(mOrder.getDelivery_date());

        double totalAmountContract = 0;
        double prePay = mOrder.getPrepay();
        tvPrepay.setText(Utils.formatCurrency((prePay)));
        for (MContract pdd : mOrder.getOrder_detail_contracts() ) {
            MContract d = Utils.getPriceContract(pdd,mContext);

            totalAmountContract += d.getAmount_price();
        }
        for (MDebt pdd : mOrder.getDebts() ) {
           if(pdd.getDebt_status_id() == 2){
               prePay +=pdd.getValue_debt();
           }
        }

        textView21.setText(Utils.formatCurrency(totalAmountContract));
        textView22.setText(Utils.formatCurrency((prePay)));
        textView23.setText(Utils.formatCurrency(totalAmountContract - prePay));

    }

    @Override
    public void onFailure(Call<MAPIResponse<MOrder>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btDone:

                break;
            default:
                break;
        }
    }
    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Year, (Month + 1), Day);
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                OrderDebtActivity.this,
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
        date.setText(tvDate.getText().toString());
    }
}
