package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_Focus;
import anhpha.clientfirst.crm.adapter.adapter_History_focus;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.Url;
import anhpha.clientfirst.crm.model.AddFocus;
import anhpha.clientfirst.crm.model.Focus;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.Result;
import anhpha.clientfirst.crm.model.Result_focus;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.LogUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 8/2/2017.
 */

public class HistoryFocusActivity extends BaseAppCompatActivity implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private Toolbar toolbar;
    private RecyclerView lvHistory_focus;
    private Retrofit retrofit;
    private Preferences preferences;
    private List<Focus> lv_focus;
    private Bundle b;
    private Spinner spTarget;
    private TextView editFromDate, editToDate,tvName,tvAddress;
    private int Target, Type;
    private RadioGroup rgFocus;
    private RadioButton rbNo, rbYes;
    private Calendar cal;
    private int setDate;
    private DatePickerDialog date;
    MClient mClient = new MClient();
    int visibleItemCount =0 ;
    int totalItemCount =0 ;
    int pastVisibleItems =0 ;
    LinearLayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_focus);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        editFromDate = (TextView) findViewById(R.id.editFromDate);
        editToDate = (TextView) findViewById(R.id.editToDate);
        tvName = (TextView) findViewById(R.id.tvName);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        lvHistory_focus = (RecyclerView) findViewById(R.id.lvHistory_focus);
        spTarget = (Spinner) findViewById(R.id.spTarget);
        rgFocus = (RadioGroup) findViewById(R.id.rgFocus);
        rbNo = (RadioButton) findViewById(R.id.rbNo);
        rbYes = (RadioButton) findViewById(R.id.rbYes);
        rbNo.setChecked(true);
        editFromDate.setText(null);
        editToDate.setText(null);
        Intent intent = getIntent();
        mClient = (MClient) intent.getSerializableExtra("mClient");
        tvName.setText(mClient.getClient_name());
        tvAddress.setText(mClient.getAddress());
        preferences = new Preferences(mContext);
        b = getIntent().getExtras();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.srtHistory_focus);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
         manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvHistory_focus.setHasFixedSize(true);
        lvHistory_focus.setLayoutManager(manager);
        rgFocus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbNo) {
                    editFromDate.setEnabled(false);
                    editToDate.setEnabled(false);
                    Type = 2;
                } else {
                    Type = 1;
                    editFromDate.setEnabled(true);
                    editToDate.setEnabled(true);
                }
            }
        });
        Type = 2;
        Target = 1;

        String[] arr = {"Gặp", "Xin cuộc hẹn", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (
                        this,
                        R.layout.item_sp,
                        arr
                );
        //phải gọi lệnh này để hiển thị danh sách cho Spinner
        adapter.setDropDownViewResource
                (R.layout.item_sp1);
        //Thiết lập adapter cho Spinner
        spTarget.setAdapter(adapter);
        spTarget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Target = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        editFromDate.setEnabled(false);
        editToDate.setEnabled(false);
        editFromDate.setOnClickListener(this);
        editToDate.setOnClickListener(this);
        retrofit = getConnect();

    }
    @Override
    protected void onResume() {
        super.onResume();
        getHistoryFocus(mClient.getClient_id());
        lvHistory_focus.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = manager.getChildCount();
                    totalItemCount = manager.getItemCount();
                    pastVisibleItems = manager.findFirstVisibleItemPosition();


                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                        {

                            Log.d("...", totalItemCount+"");
                    }
                }
            }
        });
    }
    public Retrofit getConnect() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.URL_client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public void funcAddFocus(int i) {
        List<AddFocus> lvAddfocus = new ArrayList<>();
        AddFocus focus = new AddFocus();
        focus.setClient_id(i);
        focus.setFocus_type_id(Type);
        focus.setFocus_target_id(Target);
        focus.setUser_id(preferences.getIntValue(Constants.USER_ID, 0));
        focus.setPartner_id(preferences.getIntValue(Constants.PARTNER_ID, 0));
        focus.setBegin_date(editFromDate.getText().toString());
        focus.setEnd_date(editToDate.getText().toString());
        if (Type == 1) {
            SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
            int date = 0;
            try {
                Date date1 = null, date2 = null;
                try {
                    date1 = myFormat.parse(editFromDate.getText().toString());
                    date2 = myFormat.parse(editToDate.getText().toString());
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
                if (editFromDate.getText().toString().equals("") || editToDate.getText().toString().equals("")) {
                    Toast.makeText(this,R.string.srtCheckDate, Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = isDateAfter(editFromDate.getText().toString(), editToDate.getText().toString());
                    if (check == false)
                        Toast.makeText(this, R.string.srtDate_bigger, Toast.LENGTH_SHORT).show();
                    else {
                        long diff = date2.getTime() - date1.getTime();
                        date = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                        focus.setNumber_date(date);
                        lvAddfocus.add(focus);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } else {
            focus.setNumber_date(0);
            lvAddfocus.add(focus);
        }

        if (lvAddfocus.size() > 0) {
            ServiceAPI apiFocus = retrofit.create(ServiceAPI.class);
            Call<Result> result_focus = apiFocus.set_clients_focus(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), preferences.getStringValue(Constants.TOKEN, ""), lvAddfocus);
            result_focus.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {
                    LogUtils.api("",call,response);
                    if (response.body().getHasErrors() == false) {
                        Toast.makeText(mContext, R.string.srtDone, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(mContext, R.string.srtFalse, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {
                    LogUtils.api("",call,t.toString());
                    Toast.makeText(mContext, R.string.srtFalse, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public boolean isDateAfter(String startDate, String endDate) {
        try {
            String myFormatString = "dd/MM/yyyy"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate))
                return true;
            else
                return false;
        } catch (Exception e) {

            return false;
        }
    }

    public void getHistoryFocus(int client_id) {
        ServiceAPI focus = retrofit.create(ServiceAPI.class);
        Call<Result_focus> result_focus = focus.get_clients_focus(preferences.getIntValue(Constants.USER_ID, 0), preferences.getIntValue(Constants.PARTNER_ID, 0), client_id, preferences.getStringValue(Constants.TOKEN, ""));
        result_focus.enqueue(new Callback<Result_focus>() {
            @Override
            public void onResponse(Call<Result_focus> call, Response<Result_focus> response) {
                LogUtils.api("",call,response);
                if (response.body() != null) {
                    lv_focus = response.body().getFocus();
                    adapter_History_focus adapter_focus = new adapter_History_focus(HistoryFocusActivity.this, lv_focus);
                    lvHistory_focus.setAdapter(adapter_focus);
                } else Toast.makeText(mContext, "No data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Result_focus> call, Throwable t) {
                Toast.makeText(mContext, "No data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == editFromDate) {
            setDate = 1;
            Calendar now = Calendar.getInstance();
            date = DatePickerDialog.newInstance(this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            date.show(getFragmentManager(), "Datepickerdialog");
        }
        if (v == editToDate) {
            setDate = 2;
            Calendar now = Calendar.getInstance();
            date = DatePickerDialog.newInstance(this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            date.show(getFragmentManager(), "Datepickerdialog");
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
        cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (setDate == 1)
            editFromDate.setText(sdf.format(cal.getTime()));
        else editToDate.setText(sdf.format(cal.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_edit_history_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actionDone) {
            funcAddFocus(mClient.getClient_id());
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
