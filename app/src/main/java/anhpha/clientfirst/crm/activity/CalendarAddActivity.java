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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MWorkUser;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;

public class CalendarAddActivity extends BaseAppCompatActivity implements View.OnClickListener ,AdapterView.OnItemSelectedListener , TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{
    @Bind(R.id.etTitle)
    EditText etTitle;
    @Bind(R.id.etContent)
    EditText etContent;
    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.include)
    Toolbar toolbar;
    MWorkUser mWorkUser = new MWorkUser();
    MClient mClient = new MClient();
    int type = 1;
    Preferences preferences;

    Calendar calendar ;
    DatePickerDialog datePickerDialog ;
    int Year, Month, Day ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_calendar_add);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_calendars);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Intent intent = getIntent();
        mWorkUser = (MWorkUser) intent.getSerializableExtra("mWorkUser");
        mClient = (MClient) intent.getSerializableExtra("mClient");

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add(getString(R.string.meeting));
        categories.add(getString(R.string.checkin));
        categories.add(getString(R.string.call));
        categories.add(getString(R.string.email));
        categories.add(getString(R.string.order));
        categories.add(getString(R.string.check_inventory));
        categories.add(getString(R.string.other));

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        tvDate.setOnClickListener(this);
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
                if(tvDate.getText().toString().contains("/")) {
                    mWorkUser = new MWorkUser();
                    mWorkUser.setWork_begin_date(tvDate.getText().toString());
                    mWorkUser.setContent_work(etContent.getText().toString());
                    mWorkUser.setCreate_date(etTitle.getText().toString());
                    Utils.addEvent(CalendarAddActivity.this,
                            mWorkUser.getCreate_date(),
                            mWorkUser.getContent_work(),
                            Utils.convertDateTime(mWorkUser.getWork_begin_date(),"").getTime(),1
                    );
                    finish();
                }else{
                    Utils.showError(coordinatorLayout, R.string.require_date);
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
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.tvDate:
                Calendar calendar = Calendar.getInstance();
                datePickerDialog = DatePickerDialog.newInstance(
                        CalendarAddActivity.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorApp));
                datePickerDialog.setCancelText(getString(R.string.no));
                datePickerDialog.setOkText(getString(R.string.yes));
                datePickerDialog.setTitle(getString(R.string.choose_date));
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        type = position;


    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onDateSet(DatePickerDialog view, int Year, int Month, int Day) {

        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                CalendarAddActivity.this,
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
        this.Month  = Month;
        this.Year =  Year;
        this.Day =Day;

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        tvDate.setText((Month +1) + "/" + Day + "/" + Year + " " + hourOfDay+":"+ minute);
    }
}
