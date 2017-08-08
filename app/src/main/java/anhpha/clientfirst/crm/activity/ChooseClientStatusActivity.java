package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.ChooseClientStatusAdapter;
import anhpha.clientfirst.crm.adapter.MStatus;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.DividerItemDecoration;
import anhpha.clientfirst.crm.customs.RecyclerTouchListener;
import anhpha.clientfirst.crm.utils.DynamicBox;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ChooseClientStatusActivity extends BaseAppCompatActivity implements  RecyclerTouchListener.ClickListener, View.OnClickListener  {

    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    ChooseClientStatusAdapter activityAdapter;
    List<MStatus> mStatuses = new ArrayList<>();
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_choose_clients);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.status);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mStatuses = (List<MStatus>) getIntent().getSerializableExtra("mStatuses");
        if(mStatuses.isEmpty()){
            MStatus mStatus = new MStatus(1,getString(R.string.activity),false);
            MStatus mStatus1 = new MStatus(2,getString(R.string.in_activity),false);
            mStatuses= new ArrayList<>();
            mStatuses.add(mStatus);
            mStatuses.add(mStatus1);
        }
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvActivities.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvActivities, this));

        activityAdapter = new ChooseClientStatusAdapter(mContext,mStatuses);
        rvActivities.setAdapter(activityAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                setResult(Constants.RESULT_STATUS, new Intent().putExtra("mStatuses", (Serializable) mStatuses));
                finish();
                return true;
            case android.R.id.home:
                mStatuses=null;
                setResult(Constants.RESULT_STATUS, new Intent().putExtra("mStatuses", (Serializable) mStatuses));
                finish();
              //  onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onClick(View view, int position) {
        mStatuses.get(position).setIs_select(!mStatuses.get(position).is_select());
        activityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onBackPressed() {
        mStatuses=null;
        setResult(Constants.RESULT_STATUS, new Intent().putExtra("mStatuses", (Serializable) mStatuses));
        finish();
    }
}
