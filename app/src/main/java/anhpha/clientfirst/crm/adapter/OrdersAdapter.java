package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.utils.Utils;

/**
 * Created by mc975 on 2/6/17.
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder>{
    private List<MOrder> mOrderList;
    private Context mContext;
    private Preferences preferences;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvClientName, tvAmount, tvTime, tvUser,tvOrderCode,tvStatus;

        public MyViewHolder(View view) {
            super(view);
            tvClientName = (TextView) view.findViewById(R.id.tvClientName);
            tvAmount= (TextView) view.findViewById(R.id.tvAmount);
            tvOrderCode = (TextView) view.findViewById(R.id.tvOrderCode);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            tvUser = (TextView) view.findViewById(R.id.tvUser);
            tvStatus = (TextView) view.findViewById(R.id.tvStatus);
        }
    }


    public OrdersAdapter(Context mContext , List<MOrder> mOrderList) {
        this.mOrderList = mOrderList;
        this.mContext = mContext;
        preferences = new Preferences(mContext);
    }

    public MOrder getItem(int position) {
        return mOrderList.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MOrder activityItem = mOrderList.get(position);
        holder.tvAmount.setText(Utils.formatCurrency(activityItem.getAmount_payment()));
        holder.tvClientName.setText(activityItem.getClient_name());
        holder.tvOrderCode.setText(activityItem.getOrder_contract_no().replace(" ", ""));
        holder.tvUser.setText(mContext.getString(R.string.us) + ": " + activityItem.getUser_name());
        holder.tvTime.setText((activityItem.getCreate_date()));
        switch (activityItem.getOrder_status_id()){
            case 1:
                holder.tvStatus.setText(preferences.getStringValue(Constants.ORDER_STATUS_1, ""));
                holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.order_status_1));
                break;
            case 2:
                holder.tvStatus.setText(preferences.getStringValue(Constants.ORDER_STATUS_2, ""));
                holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.order_status_2));
                break;
            case 4:
                holder.tvStatus.setText(preferences.getStringValue(Constants.ORDER_STATUS_4, ""));
                holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.order_status_4));
                break;
            case 6:
                holder.tvStatus.setText(preferences.getStringValue(Constants.ORDER_STATUS_6, ""));
                holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.order_status_6));
                break;
            case 7:
                holder.tvStatus.setText(preferences.getStringValue(Constants.ORDER_STATUS_7, ""));
                holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.order_status_7));
                break;
            default:
                break;
        }
        switch (activityItem.getOrder_status_id()){
            case 1:
                holder.tvOrderCode.setBackgroundResource(R.drawable.layout_round);
             break;
            case 2:
                holder.tvOrderCode.setBackgroundResource(R.drawable.layout_round_orange);
                break;
            case 4:
                holder.tvOrderCode.setBackgroundResource(R.drawable.layout_round_blue);
                 break;
            case 6:
                holder.tvOrderCode.setBackgroundResource(R.drawable.layout_round_red);
            default:
                break;

        }
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
}
