package anhpha.clientfirst.crm.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.CalendarsAdapter;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.DividerItemDecoration;
import anhpha.clientfirst.crm.customs.RecyclerTouchListener;
import anhpha.clientfirst.crm.interfaces.AdapterInterface;
import anhpha.clientfirst.crm.model.MCalendar;
import anhpha.clientfirst.crm.utils.CalendarContentResolver;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import anhpha.clientfirst.crm.utils.DynamicBox;

public class CalendarsActivity extends BaseAppCompatActivity implements  RecyclerTouchListener.ClickListener, TextWatcher, View.OnClickListener  {

    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.ibClose)
    ImageButton ibClose;
    @Bind(R.id.layout_find)
    LinearLayout layout_find;
    @Bind(R.id.etText)
    EditText etText;
    CalendarsAdapter activityAdapter;
    List<MCalendar> mClients = new ArrayList<>();
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_calendars);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar  actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_calendars);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        checkPermission(Manifest.permission.WRITE_CALENDAR);

        ibClose.setOnClickListener(this);
        etText.addTextChangedListener(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvActivities.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvActivities, this));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CalendarContentResolver calendarContentResolver = new CalendarContentResolver(mContext);
        mClients = calendarContentResolver.getCalendars();
        activityAdapter = new CalendarsAdapter(mContext, mClients, new AdapterInterface() {
            @Override
            public void buttonPressed(int i, int position) {
                Utils.deleteEvent(mClients.get(position).getEvent_id(),mContext);
                CalendarContentResolver calendarContentResolver = new CalendarContentResolver(mContext);
                mClients = calendarContentResolver.getCalendars();
                activityAdapter.setActivityItemList(mClients);
                activityAdapter.notifyDataSetChanged();
                rvActivities.setAdapter(activityAdapter);
            }
        });
        rvActivities.setAdapter(activityAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.find:
                layout_find.setVisibility(View.VISIBLE);
                return true;
            case R.id.add:
                startActivity(new Intent(mContext,CalendarAddActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ibClose:
            layout_find.setVisibility(View.GONE);
                etText.setText("");
            break;
            default:
            break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        List<MCalendar> list = new ArrayList<>();
        String x = Utils.deAccent(editable.toString().toLowerCase());
        for (MCalendar mClient: mClients){
            String a = Utils.deAccent(mClient.getCalendar_title().toLowerCase());
            if(a.indexOf(x)>=0){
                list.add(mClient);
            }
        }
        activityAdapter.setActivityItemList(list);
        activityAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view, int position) {
        //setResult(Constants.RESULT_CLIENT, new Intent().putExtra("mClient",activityAdapter.get(position)));
        //finish();
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
