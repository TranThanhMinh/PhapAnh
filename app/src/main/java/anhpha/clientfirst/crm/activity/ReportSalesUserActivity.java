package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.ReportAdapter;
import anhpha.clientfirst.crm.charting.charts.PieChart;
import anhpha.clientfirst.crm.charting.data.Entry;
import anhpha.clientfirst.crm.charting.data.PieData;
import anhpha.clientfirst.crm.charting.data.PieDataSet;
import anhpha.clientfirst.crm.charting.data.PieEntry;
import anhpha.clientfirst.crm.charting.formatter.PercentFormatter;
import anhpha.clientfirst.crm.charting.highlight.Highlight;
import anhpha.clientfirst.crm.charting.interfaces.datasets.IDataSet;
import anhpha.clientfirst.crm.charting.listener.OnChartValueSelectedListener;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MColor;
import anhpha.clientfirst.crm.model.MReport;
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

/**
 * Created by mc975 on 2/6/17.
 */
public class ReportSalesUserActivity extends BaseAppCompatActivity  implements Callback<MAPIResponse<List<MReport>>>,OnChartValueSelectedListener {
    @Bind(R.id.chart1)
    PieChart mChart;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvAmount)
    TextView tvAmount;
    @Bind(R.id.tvSelect)
    TextView tvSelect;
    @Bind(R.id.rvView)
    RecyclerView rvView;
    List<MReport> mReports;
    String toDate;
    String fromDate;
    Preferences preferences;
    double value = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_piechart);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_report);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        preferences = new Preferences(mContext);
        tvTitle.setText(getString(R.string.month));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvView.setLayoutManager(mLayoutManager);
        rvView.setItemAnimator(new DefaultItemAnimator());

        MRequestBody mRequestBody = new MRequestBody();
        mRequestBody.setFrom_date(Utils.getFromDateMonth());
        mRequestBody.setTo_date(Utils.getToDate());
        GetRetrofit().create(ServiceAPI.class)
                .getSalesAmountUser(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , mRequestBody
                )
                .enqueue(this);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        box.showLoadingLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_calendar, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:
                startActivityForResult( new Intent(mContext, CalendarActivity.class),Constants.RESULT_CALENDAR);

                return true;

            case R.id.user:

                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setData( List<MReport> mReports) {
        tvSelect.setText("");
        //mChart.clearValues();
        mChart.clear();

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < mReports.size() ; i++) {
            entries.add(new PieEntry((float) (mReports.get(i).getValue()), mReports.get(i).getName()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "AP DMS");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        value = 0;
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < mReports.size();i++){
            MColor mColor = new MColor(new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255));
            colors.add(Color.rgb(mColor.getRed(),mColor.getGreen(),mColor.getBlue()));
            mReports.get(i).setColor(mColor);
            value += mReports.get(i).getValue();
        }
        
        tvAmount.setText(Utils.formatCurrency(value));
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());

        mChart.setData(data);

        mChart.setExtraOffsets(5, 10, 5, 5);
        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);
        for (IDataSet<?> set : mChart.getData().getDataSets())
            set.setDrawValues(!set.isDrawValuesEnabled());

        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        double percent = mReports.get((int) h.getX()).getValue() / value * 100;

        tvSelect.setText(mReports.get((int) h.getX()).getName() + ": $ " + Utils.formatCurrency(mReports.get((int) h.getX()).getValue())  +"\n"+ Utils.formatPercent(percent) + " %"  );
    }

    @Override
    public void onNothingSelected() {
        tvSelect.setText("");
    }

    @Override
    public void onResponse(Call<MAPIResponse<List<MReport>>> call, Response<MAPIResponse<List<MReport>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        setData(mReports = response.body().getResult());
        ReportAdapter reportAdapter = new ReportAdapter(mReports);
        rvView.setAdapter(reportAdapter);
    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MReport>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Constants.RESULT_CALENDAR) {
            toDate = data.getStringExtra("toDate");
            fromDate = data.getStringExtra("fromDate");

            tvTitle.setText(data.getStringExtra("tvDate"));

            MRequestBody mRequestBody = new MRequestBody();
            mRequestBody.setFrom_date(fromDate);
            mRequestBody.setTo_date(toDate);
            GetRetrofit().create(ServiceAPI.class)
                    .getSalesAmountUser(preferences.getStringValue(Constants.TOKEN, "")
                            , preferences.getIntValue(Constants.USER_ID, 0)
                            , preferences.getIntValue(Constants.PARTNER_ID, 0)
                            , mRequestBody
                    )
                    .enqueue(this);
            setProgressBarIndeterminateVisibility(true);
            setProgressBarVisibility(true);
            box.showLoadingLayout();
        }

    }
}
