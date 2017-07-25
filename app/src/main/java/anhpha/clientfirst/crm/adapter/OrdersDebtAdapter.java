package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MOrder;
import anhpha.clientfirst.crm.utils.Utils;

/**
 * Created by mc975 on 2/6/17.
 */
public class OrdersDebtAdapter extends RecyclerView.Adapter<OrdersDebtAdapter.MyViewHolder>{
    private List<MOrder> mOrderList;
    private Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvClientName, tvAmount, tvTime, tvUser,tvOrderCode,tvPrepay, tvTotal;

        public MyViewHolder(View view) {
            super(view);
            tvClientName = (TextView) view.findViewById(R.id.tvClientName);
            tvAmount= (TextView) view.findViewById(R.id.tvAmount);
            tvOrderCode = (TextView) view.findViewById(R.id.tvOrderCode);
            tvTime = (TextView) view.findViewById(R.id.tvTime);
            tvUser = (TextView) view.findViewById(R.id.tvUser);
            tvPrepay = (TextView) view.findViewById(R.id.tvPrepay);
            tvTotal = (TextView) view.findViewById(R.id.tvTotal);
        }
    }


    public OrdersDebtAdapter(Context mContext , List<MOrder> mOrderList) {
        this.mOrderList = mOrderList;
        this.mContext = mContext;
    }

    public MOrder getItem(int position) {
        return mOrderList.get(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_debt_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MOrder activityItem = mOrderList.get(position);
        holder.tvAmount.setText(mContext.getString(R.string.total) +": "+Utils.formatCurrency(activityItem.getAmount_payment()));
        holder.tvClientName.setText(activityItem.getClient_name());
        holder.tvOrderCode.setText(activityItem.getOrder_contract_no().replace(" ", ""));
        holder.tvUser.setText(mContext.getString(R.string.us) + ": " + activityItem.getUser_name());
        holder.tvTime.setText((activityItem.getCreate_date()));
        holder.tvPrepay.setText(mContext.getString(R.string.paid) +": "+Utils.formatCurrency(activityItem.getPrepay()));
        holder.tvTotal.setText(mContext.getString(R.string.remain) +": "+Utils.formatCurrency(activityItem.getAmount_payment() - activityItem.getPrepay()));
    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }
}
