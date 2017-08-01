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
import anhpha.clientfirst.crm.adapter.ReportContractAdapter;
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
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MRequestBody;
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

/**
 * Created by mc975 on 2/6/17.
 */
public class ReportInventoryActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<List<MContract>>>,OnChartValueSelectedListener {
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
    List<MContract> mReports;
    List<MId> mIds = new ArrayList<>();
    Preferences preferences;
    double amount = 0;
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
        tvTitle.setText(getString(R.string.inventory_market));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvView.setLayoutManager(mLayoutManager);
        rvView.setItemAnimator(new DefaultItemAnimator());

        MRequestBody mRequestBody = new MRequestBody();
        mRequestBody.setFrom_date(Utils.getFromDateMonth());
        mRequestBody.setTo_date(Utils.getToDate());
        GetRetrofit().create(ServiceAPI.class)
                .getInventoryContractClients(preferences.getStringValue(Constants.TOKEN, "")
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
        inflater.inflate(R.menu.menu_user, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:

                return true;

            case R.id.user:
                startActivityForResult(new Intent(mContext, UsersActivity.class), Constants.RESULT_USERS);

                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setData( List<MContract> mReports) {
        tvSelect.setText("");
        //mChart.clearValues();
        mChart.clear();
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < mReports.size() ; i++) {
            MContract contract = Utils.getPriceContract(mReports.get(i),mContext);
            entries.add(new PieEntry((float) (contract.getAmount_price().floatValue()), mReports.get(i).getContract_name()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "AP DMS");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < mReports.size();i++){
            MColor mColor = new MColor(new Random().nextInt(255),new Random().nextInt(255),new Random().nextInt(255));
            colors.add(Color.rgb(mColor.getRed(), mColor.getGreen(), mColor.getBlue()));
            mReports.get(i).setmColor(mColor);
        }

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
        double price = Utils.getPriceContract(mReports.get((int) h.getX()),mContext).getAmount_price();
        double percent = price / amount * 100;
        tvSelect.setText(mReports.get((int) h.getX()).getContract_name() + ": " + Utils.formatCurrency(price) + "\n" + Utils.formatPercent(percent)+ " %");
    }

    @Override
    public void onNothingSelected() {
        tvSelect.setText("");
    }

    @Override
    public void onResponse(Call<MAPIResponse<List<MContract>>> call, Response<MAPIResponse<List<MContract>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        setData(mReports = response.body().getResult());
        ReportContractAdapter reportAdapter = new ReportContractAdapter(mReports,mContext);
        rvView.setAdapter(reportAdapter);

        amount = 0;
        for(MContract p: mReports){
            amount += Utils.getPriceContract(p,mContext).getAmount_price();
        }
        tvAmount.setText(Utils.formatCurrency(amount));

    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MContract>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (resultCode == Constants.RESULT_USERS) {
            mIds = new ArrayList<>();
            mIds = (List<MId>) data.getSerializableExtra("mIds");
            MRequestBody mRequestBody = new MRequestBody();
            mRequestBody.setFrom_date(Utils.getFromDateMonth());
            mRequestBody.setTo_date(Utils.getToDate());
            mRequestBody.setUser_ids(mIds);

            GetRetrofit().create(ServiceAPI.class)
                    .getInventoryContractClients(preferences.getStringValue(Constants.TOKEN, "")
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
