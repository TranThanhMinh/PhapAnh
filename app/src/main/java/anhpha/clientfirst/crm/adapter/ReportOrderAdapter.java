package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.model.MReport;
import anhpha.clientfirst.crm.utils.Utils;

/**
 * Created by mc975 on 2/6/17.
 */
public class ReportOrderAdapter extends RecyclerView.Adapter<ReportOrderAdapter.MyViewHolder>{
    private List<MReport> activityItemList;
    Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, value, color;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            value= (TextView) view.findViewById(R.id.value);
            color = (TextView) view.findViewById(R.id.color);

        }
    }


    public ReportOrderAdapter(List<MReport> activityItemList, Context context) {
        this.activityItemList = activityItemList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MReport activityItem = activityItemList.get(position);
        holder.name.setText(activityItem.getName());
        holder.value.setText(Utils.formatCurrency(activityItem.getValue()));
        holder.color.setBackgroundColor(Color.rgb(activityItem.getColor().getRed(),activityItem.getColor().getGreen(),activityItem.getColor().getBlue()));
        switch (position){
            case 0:
                holder.color.setBackgroundColor(mContext.getResources().getColor(R.color.order_status_1));
                break;
            case 1:
                holder.color.setBackgroundColor(mContext.getResources().getColor(R.color.order_status_2));
                break;
            case 2:
                holder.color.setBackgroundColor(mContext.getResources().getColor(R.color.order_status_4));
                break;
            case 3:
                holder.color.setBackgroundColor(mContext.getResources().getColor(R.color.order_status_6));
                break;
            case 4:
                holder.color.setBackgroundColor(mContext.getResources().getColor(R.color.order_status_7));
                break;
            case 5:
                holder.color.setBackgroundColor(mContext.getResources().getColor(R.color.dark_light));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
