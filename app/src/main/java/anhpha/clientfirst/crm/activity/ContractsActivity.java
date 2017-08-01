package anhpha.clientfirst.crm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.ContractsAdapter;
import anhpha.clientfirst.crm.adapter.GroupContractsAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.DividerItemDecoration;
import anhpha.clientfirst.crm.customs.RecyclerTouchListener;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.model.MGroupContract;
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

public class ContractsActivity extends BaseAppCompatActivity implements RecyclerTouchListener.ClickListener,Callback<MAPIResponse<List<MGroupContract>>>, View.OnClickListener  {

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.left_drawer)
    LinearLayout mDrawerList;
    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.rvCategory)
    RecyclerView rvCategory;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.ibOpen)
    ImageButton ibOpen;
    @Bind(R.id.ibClose)
    ImageButton ibClose;
    EditText etUnit1;
    EditText etUnit2;
    EditText etNote;
    DynamicBox box;

    EditText note;
    EditText value;
    EditText number;
    TextView total;

    List<MContract> MContracts = new ArrayList<>();;
    List<MContract> list = new ArrayList<>();
    MClient mClient= new MClient();
    List<MGroupContract> mGroupContracts = new ArrayList<>();;
    ContractsAdapter activityAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_contracts);
        ButterKnife.bind(this);
        Preferences preferences = new Preferences(mContext);
        //set up
        mDrawerList.getLayoutParams().width = Utils.getWidth(mContext) - 100;
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_contract);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        ibOpen.setOnClickListener(this);
        ibClose.setOnClickListener(this);


        RecyclerView.LayoutManager  mLayoutManager = new GridLayoutManager(mContext, 2);
        rvActivities.setLayoutManager(mLayoutManager);

        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(mContext);
        mLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        rvCategory.setLayoutManager(mLayoutManager2);
        rvCategory.setItemAnimator(new DefaultItemAnimator());
        rvCategory.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvActivities.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvActivities, this));

        rvCategory.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvActivities, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                list = new ArrayList<>();
                if(mGroupContracts.get(position).getContract_group_id()==0){
                    for (MContract p : MContracts) {
                            list.add(p);
                    }
                }else {
                    for (MContract p : MContracts) {
                        if (p.getContract_group_id() == mGroupContracts.get(position).getContract_group_id()) {
                            list.add(p);
                        }
                    }
                }

                activityAdapter.setMContractList(list);
                activityAdapter.notifyDataSetChanged();
                mDrawerLayout.closeDrawers();
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        Intent iii = getIntent();
        MContracts = (List<MContract>) iii.getSerializableExtra("MContracts");
        try {
            activityAdapter = new ContractsAdapter(mContext, MContracts);

            rvActivities.setAdapter(activityAdapter);
            activityAdapter.notifyDataSetChanged();
        }catch (Exception e){

        }

        GetRetrofit().create(ServiceAPI.class)
                .getContractGroups(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                        , 0
                        , 1
                )
                .enqueue(this);
        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        box.showLoadingLayout();

        LogUtils.d(TAG, "getUserActivities ", "start");
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

                setResult(Constants.RESULT_PRODUCT, new Intent().putExtra("MContractsNew", (Serializable) MContracts));
                finish();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<List<MGroupContract>>> call, Response<MAPIResponse<List<MGroupContract>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        try {
            mGroupContracts  = new ArrayList<>();
            MGroupContract groupContract = new MGroupContract();
            groupContract.setContract_group_name(getString(R.string.all));
            mGroupContracts.add(groupContract);
            mGroupContracts.addAll(response.body().getResult());
            GroupContractsAdapter activityAdapter = new GroupContractsAdapter(mContext, mGroupContracts);
            rvCategory.setAdapter(activityAdapter);

            activityAdapter.notifyDataSetChanged();
        }catch (Exception e){

        }

    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MGroupContract>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        case R.id.ibClose:
            mDrawerLayout.closeDrawers();
            break;
        case R.id.ibOpen:
            mDrawerLayout.openDrawer(mDrawerList);
            break;
        default:
            break;
    }
    }

    @Override
    public void onClick(View view, final int position) {
        final MContract mContract = activityAdapter.getItem(position);

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
                activityAdapter.getItem(position).setNumber(unit_1);
                activityAdapter.getItem(position).setPrice(unit_2);
                activityAdapter.getItem(position).setNote(snote);
                activityAdapter.notifyDataSetChanged();

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
        number.setText((mContract.getNumber().intValue() == 0 ? 1: mContract.getNumber().intValue()) + "");
        note.setText(mContract.getNote());
        value.setText(Utils.formatCurrency(mContract.getPrice()));

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
