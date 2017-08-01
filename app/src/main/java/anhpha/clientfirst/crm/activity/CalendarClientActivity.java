package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MActivity;
import anhpha.clientfirst.crm.utils.DynamicBox;
import butterknife.Bind;
import butterknife.ButterKnife;

public class CalendarClientActivity extends BaseAppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    @Bind(R.id.radioButton0)
    RadioButton radioButton0;
    @Bind(R.id.radioButton1)
    RadioButton radioButton1;
    @Bind(R.id.radioButton2)
    RadioButton radioButton2;
    @Bind(R.id.radioButton3)
    RadioButton radioButton3;
    @Bind(R.id.radioButton4)
    RadioButton radioButton4;
    @Bind(R.id.radioButton5)
    RadioButton radioButton5;
    @Bind(R.id.radioButton6)
    RadioButton radioButton6;
    @Bind(R.id.radioButton7)
    RadioButton radioButton7;
    @Bind(R.id.radioButton8)
    RadioButton radioButton8;
    @Bind(R.id.include)
    Toolbar toolbar;

    MActivity mActivity;
    Date fromDate;
    Calendar calendar;
    Date toDate;
    Date today;
    DateFormat from_sdf = new SimpleDateFormat("MM/dd/yyyy 00:00");
    DateFormat to_sdf = new SimpleDateFormat("MM/dd/yyyy 23:59");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_calendar_client);
        ButterKnife.bind(this);
        Preferences preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_calendar);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        radioButton0.setOnCheckedChangeListener(this);
        radioButton1.setOnCheckedChangeListener(this);
        radioButton2.setOnCheckedChangeListener(this);
        radioButton3.setOnCheckedChangeListener(this);
        radioButton4.setOnCheckedChangeListener(this);
        radioButton5.setOnCheckedChangeListener(this);
        radioButton6.setOnCheckedChangeListener(this);
        radioButton7.setOnCheckedChangeListener(this);
        radioButton8.setOnCheckedChangeListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_null, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        String to_Date;
        String from_Date;
        switch (compoundButton.getId()){
            case R.id.radioButton0:
                today = new Date();
                calendar = Calendar.getInstance();
                calendar.setTime(today);

                to_Date =  "01/01/2092 00:00";
                from_Date =  "01/01/1999 00:00";

                setResult(Constants.RESULT_CALENDAR, new Intent().putExtra("toDate", to_Date).putExtra("fromDate", from_Date).putExtra("tvDate",radioButton0.getText()));
                finish();
                break;
            case R.id.radioButton1:
                today = new Date();
                calendar = Calendar.getInstance();
                calendar.setTime(today);
                 to_Date =  to_sdf.format(today);
                 from_Date =  from_sdf.format(today);
                setResult(Constants.RESULT_CALENDAR, new Intent().putExtra("toDate", to_Date).putExtra("fromDate", from_Date).putExtra("tvDate",radioButton1.getText()));
                finish();
                break;
            case R.id.radioButton2:
                today = new Date();
                calendar = Calendar.getInstance();
                calendar.setTime(today);
                calendar.add(Calendar.DATE, -1);
                today = calendar.getTime();

                 to_Date =  to_sdf.format(today);
                 from_Date =  from_sdf.format(today);
                setResult(Constants.RESULT_CALENDAR, new Intent().putExtra("toDate", to_Date).putExtra("fromDate", from_Date).putExtra("tvDate",radioButton2.getText()));
                finish();
                break;
            case R.id.radioButton3:
                today = new Date();
                calendar = Calendar.getInstance();
                calendar.setTime(today);
                calendar.set(Calendar.DAY_OF_WEEK, 2);
                calendar.add(Calendar.DATE, 0);
                fromDate = calendar.getTime();

                to_Date =  to_sdf.format(today);
                from_Date =  from_sdf.format(fromDate);
                setResult(Constants.RESULT_CALENDAR, new Intent().putExtra("toDate", to_Date).putExtra("fromDate", from_Date).putExtra("tvDate",radioButton3.getText()));
                finish();
                break;
            case R.id.radioButton4:

                today = new Date();
                calendar = Calendar.getInstance();
                calendar.setTime(today);
                calendar.set(Calendar.DAY_OF_WEEK, 2);
                calendar.add(Calendar.DATE, -1);
                today = calendar.getTime();

                calendar.add(Calendar.DATE, -6);
                fromDate = calendar.getTime();

                to_Date =  to_sdf.format(today);
                from_Date =  from_sdf.format(fromDate);
                setResult(Constants.RESULT_CALENDAR,new Intent().putExtra("toDate",to_Date).putExtra("fromDate",from_Date).putExtra("tvDate",radioButton4.getText()));
                finish();
                break;
            case R.id.radioButton5:
                today = new Date();

                calendar = Calendar.getInstance();
                calendar.setTime(today);

                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.DATE, -1);

                toDate = calendar.getTime();
                calendar.add(Calendar.MONTH, 0);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.DATE, 0);
                fromDate = calendar.getTime();

                to_Date =  to_sdf.format(toDate);
                from_Date =  from_sdf.format(fromDate);
                setResult(Constants.RESULT_CALENDAR, new Intent().putExtra("toDate", to_Date).putExtra("fromDate", from_Date).putExtra("tvDate",radioButton5.getText()));
                finish();
                break;
            case R.id.radioButton6:
                today = new Date();
                calendar = Calendar.getInstance();
                calendar.setTime(today);

                calendar.add(Calendar.MONTH, 0);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.DATE, -1);

                toDate = calendar.getTime();
                calendar.add(Calendar.MONTH, 0);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.DATE, 0);
                fromDate = calendar.getTime();

                to_Date =  to_sdf.format(toDate);
                from_Date =  from_sdf.format(fromDate);
                setResult(Constants.RESULT_CALENDAR,new Intent().putExtra("toDate",to_Date).putExtra("fromDate",from_Date).putExtra("tvDate",radioButton6.getText()));
                finish();
                break;
            case R.id.radioButton7:
                Calendar cal= Calendar.getInstance();
                int month = cal.get(Calendar.MONTH) + 1;
                int quarter=0;
                int target = 0;
                if(month<4){
                    quarter=1;
                    target = 1;
                    // news letter belongs to quarter 1
                }else if(month<7){
                    quarter=2;
                    target = 4;
                    // news letter belongs to quarter 2
                }else if(month<10){
                    quarter=3;
                    target = 7;
                    // news letter belongs to quarter 3
                }else{
                    quarter = 4;
                    target = 10;
                }

                today = new Date();

                calendar = Calendar.getInstance();
                calendar.setTime(today);
                toDate = calendar.getTime();

                calendar.add(Calendar.MONTH, ((month  - target) * -1));
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.DATE, 0);

                fromDate = calendar.getTime();

                 to_Date =  to_sdf.format(toDate);
                 from_Date =  from_sdf.format(fromDate);
                setResult(Constants.RESULT_CALENDAR, new Intent().putExtra("toDate", to_Date).putExtra("fromDate", from_Date).putExtra("tvDate",radioButton7.getText()));
                finish();
                break;
            case R.id.radioButton8:
                Calendar cal2= Calendar.getInstance();
                int month2 = cal2.get(Calendar.MONTH) + 1;
                int quarter2=0;
                int target2 = 0;
                if(month2<4){
                    quarter=1;
                    target2 = 1;
                    // news letter belongs to quarter 1
                }else if(month2<7){
                    quarter=2;
                    target2 = 4;
                    // news letter belongs to quarter 2
                }else if(month2<10){
                    quarter=3;
                    target2 = 7;
                    // news letter belongs to quarter 3
                }else{
                    quarter = 4;
                    target2 = 10;
                }

                today = new Date();

                calendar = Calendar.getInstance();
                calendar.setTime(today);
                fromDate = calendar.getTime();

                calendar.add(Calendar.MONTH, (((month2  - target2) + 3) * -1));
                calendar.set(Calendar.DAY_OF_MONTH, 1);


                fromDate = calendar.getTime();

                calendar.add(Calendar.MONTH, ((3)));
                calendar.set(Calendar.DAY_OF_MONTH, 0);
                calendar.add(Calendar.DATE, -1);
                toDate = calendar.getTime();
                to_Date =  to_sdf.format(toDate);
                from_Date =  from_sdf.format(fromDate);
                setResult(Constants.RESULT_CALENDAR, new Intent().putExtra("toDate", to_Date).putExtra("fromDate", from_Date).putExtra("tvDate",radioButton8.getText()));
                finish();
                break;
            default:
                break;

        }
    }
}
