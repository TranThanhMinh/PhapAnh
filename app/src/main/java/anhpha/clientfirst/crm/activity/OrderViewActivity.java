package anhpha.clientfirst.crm.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.OrderViewAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.model.MId;
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

public class OrderViewActivity extends BaseAppCompatActivity implements Callback<MAPIResponse<MOrder>>, View.OnClickListener  {
    @Bind(R.id.rvActivities)
    ListView rvActivities;
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
    @Bind(R.id.tvOrderCode)
    TextView tvOrderCode;
    @Bind(R.id.tvOrderCodeParent)
    TextView tvOrderCodeParent;
    @Bind(R.id.btDone)
    Button btDone;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    MOrder mOrder = new MOrder();
    List<MContract> MContracts = new ArrayList<>();
    List<MContract> MContractsOld = new ArrayList<>();
    List<MContract> MContractsNew = new ArrayList<>();
    OrderViewAdapter orderAdapter ;
    Preferences preferences;
    boolean is_hide_menu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_order_view);
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
        if(mOrder.getOrder_sheet_id() == -1)
            is_hide_menu = true;
        tvClientName.setText(mOrder.getClient_name());


    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRetrofit().create(ServiceAPI.class)
                .getOrder(preferences.getStringValue(Constants.TOKEN, "")
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
        inflater.inflate(R.menu.menu_order_view, menu);
        if(!is_hide_menu){
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.edit)
                    menu.getItem(i).setVisible(true);
            }

            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.cancel)
                    menu.getItem(i).setVisible(true);
            }
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.send)
                    menu.getItem(i).setVisible(true);
            }
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.history)
                    menu.getItem(i).setVisible(true);
            }
        }
        if(!preferences.getBooleanValue(Constants.permission_cancel_order,false)){
            for (int i = 0; i < menu.size(); i++) {
                if (menu.getItem(i).getItemId() == R.id.cancel)
                    menu.getItem(i).setVisible(false);
            }
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(mContext,OrderActivity.class);
                intent.putExtra("mOrder", mOrder);
                startActivity(intent);
                return true;

            case R.id.send:
                    saveImage();
                return true;
            case R.id.print:

                return true;
            case R.id.history:
                Intent in = new Intent(mContext,History_orders_activity.class);
                startActivity(in);
                return true;
            case R.id.cancel:

                AlertDialog.Builder builder = new AlertDialog.Builder(OrderViewActivity.this);
                builder.setTitle(getResources().getString(R.string.alert));
                builder.setCancelable(true);
                builder.setMessage(getResources().getString(R.string.delete_order_message));
                builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        box.showLoadingLayout();
                        GetRetrofit().create(ServiceAPI.class)
                                .setStatusOrder(preferences.getStringValue(Constants.TOKEN, "")
                                        , preferences.getIntValue(Constants.USER_ID, 0)
                                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                        , mOrder.getOrder_contract_id()
                                        , 3
                                )
                                .enqueue(new Callback<MAPIResponse<MId>>() {
                                    @Override
                                    public void onResponse(Call<MAPIResponse<MId>> call, Response<MAPIResponse<MId>> response) {
                                        LogUtils.api(TAG, call, (response.body()));
                                        box.hideAll();
                                        TokenUtils.checkToken(mContext,response.body().getErrors());
                                        if(!response.body().isHasErrors()){
                                            finish();
                                        }
                                        else{
                                            Utils.showError(coordinatorLayout, R.string.delete_order_fail);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<MAPIResponse<MId>> call, Throwable t) {
                                        LogUtils.d(TAG, "getUserActivities ", t.toString());
                                        box.hideAll();
                                        Utils.showError(coordinatorLayout, R.string.delete_order_fail);
                                    }
                                });

                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.no), null);
                builder.show();

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
        tvOrderCode.setText(mOrder.getOrder_contract_code());
        tvOrderCodeParent.setText(mOrder.getOrder_contract_code_parent());
        switch (mOrder.getOrder_status_id()){
            case 1:
               tvType.setText(preferences.getStringValue(Constants.ORDER_STATUS_1, ""));
               tvType.setTextColor(mContext.getResources().getColor(R.color.order_status_1));
                break;
            case 2:
                tvType.setText(preferences.getStringValue(Constants.ORDER_STATUS_2, ""));
               tvType.setTextColor(mContext.getResources().getColor(R.color.order_status_4));
                break;
            case 3:
                tvType.setText(preferences.getStringValue(Constants.ORDER_STATUS_3, ""));
                tvType.setTextColor(mContext.getResources().getColor(R.color.dark));
                break;
            case 4:
                tvType.setText(preferences.getStringValue(Constants.ORDER_STATUS_4, ""));
               tvType.setTextColor(mContext.getResources().getColor(R.color.order_status_4));
                break;
            case 5:
                tvType.setText(preferences.getStringValue(Constants.ORDER_STATUS_5, ""));
                tvType.setTextColor(mContext.getResources().getColor(R.color.dark));
                break;
            case 6:
                tvType.setText(preferences.getStringValue(Constants.ORDER_STATUS_6, ""));
               tvType.setTextColor(mContext.getResources().getColor(R.color.order_status_6));
                break;
            case 7:
                tvType.setText(preferences.getStringValue(Constants.ORDER_STATUS_7, ""));
                tvType.setTextColor(mContext.getResources().getColor(R.color.order_status_7));
                break;
            case 8:
                tvType.setText(getString(R.string.delete));
                tvType.setTextColor(mContext.getResources().getColor(R.color.dark));
                break;
            default:
                break;
        }
        double totalAmountOrder  = 0;
        double totalAmountContract = 0;
        double vat = 0;
        double discountContract = 0;
        double discountOrder = 0;
        double prePay = mOrder.getPrepay();

        for (MContract pdd : mOrder.getOrder_detail_contracts() ) {
            MContract d = Utils.getPriceContract(pdd,mContext);

            totalAmountContract += d.getAmount_price();
        }
        totalAmountOrder += totalAmountContract;
        tvPrepay.setText(Utils.formatCurrency((prePay)));
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

                GetRetrofit().create(ServiceAPI.class)
                        .setStatusOrder(preferences.getStringValue(Constants.TOKEN, "")
                                , preferences.getIntValue(Constants.USER_ID, 0)
                                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                , mOrder.getOrder_contract_id()
                                , 7
                        )
                        .enqueue(new Callback<MAPIResponse<MId>>() {
                            @Override
                            public void onResponse(Call<MAPIResponse<MId>> call, Response<MAPIResponse<MId>> response) {
                                LogUtils.api(TAG, call, (response.body()));
                                box.hideAll();
                                TokenUtils.checkToken(mContext,response.body().getErrors());
                                if(!response.body().isHasErrors()){
                                    Utils.showDialogSuccess(mContext, R.string.done_order_done);
                                }
                                else{
                                    Utils.showError(coordinatorLayout, R.string.done_order_fail);
                                }
                            }
                            @Override
                            public void onFailure(Call<MAPIResponse<MId>> call, Throwable t) {
                                LogUtils.d(TAG, "getUserActivities ", t.toString());
                                box.hideAll();
                                Utils.showError(coordinatorLayout, R.string.done_order_fail);
                            }
                        });


                break;
            default:
                break;
        }
    }
    public static Bitmap getBitmapFromView(ScrollView view) {
        //Define a bitmap with the same size as the view
        ScrollView vv= view;
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;

//
//        view.measure(View.MeasureSpec.AT_MOST, View.MeasureSpec.UNSPECIFIED);
//        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getMeasuredHeight(),
//                Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        view.layout(0, 0, view.getWidth(), view.getMeasuredHeight());
//        view.draw(canvas);
//        return bitmap;
    }
    void saveImage() {
        String FILE = Environment.getExternalStorageDirectory() + "";

        File myDir = new File(FILE);
        myDir.mkdirs();
        String fname = "Receipt.jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();

        try {
            FileOutputStream out = new FileOutputStream(file);

            getBitmapFromView(scrollView).compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            composeEmail( getString(R.string.order), FILE + "/Receipt.jpg");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void composeEmail(final String subject, final String flie) {
//        String[] strarray = new String[1];
//        List<String> value = new ArrayList<>();
//        value.add("buvianthuong.it@gmail.com");
//        value.toArray(strarray);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, strarray);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + flie));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
