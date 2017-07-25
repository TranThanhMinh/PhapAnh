package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import anhpha.clientfirst.crm.utils.DynamicBox;

public class ReportActivity extends BaseAppCompatActivity implements View.OnClickListener  {
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.textView4)
    TextView textView4;

    @Bind(R.id.include)
    Toolbar toolbar;
    DynamicBox box;
    MActivity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_report_user);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar  actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_report);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
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
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.textView1:
                Intent it1 = new Intent(mContext,ReportSalesMonthActivity.class);
                startActivity(it1);
                break;
            case R.id.textView2:
                Intent it2 = new Intent(mContext,ReportInventoryActivity.class);
                startActivity(it2);
                break;
            case R.id.textView3:
                Intent it3 = new Intent(mContext,ReportSalesUserActivity.class);
                startActivity(it3);
                break;
            case R.id.textView4:
                Intent it4 = new Intent(mContext,ReportOrderCountUserActivity.class);
                startActivity(it4);
                break;
            default:
                break;
        }

    }
}
