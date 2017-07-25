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
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.utils.Utils;

/**
 * Created by mc975 on 2/6/17.
 */
public class ReportContractAdapter extends RecyclerView.Adapter<ReportContractAdapter.MyViewHolder>{
    private List<MContract> activityItemList;
    private Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, value, color;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            value= (TextView) view.findViewById(R.id.value);
            color = (TextView) view.findViewById(R.id.color);

        }
    }


    public ReportContractAdapter(List<MContract> activityItemList, Context mContext) {
        this.activityItemList = activityItemList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MContract activityItem = activityItemList.get(position);
        holder.name.setText(activityItem.getContract_name());
        holder.value.setText(Utils.formatCurrency(Utils.getPriceContract(activityItem,mContext).getAmount_price()));
        holder.color.setBackgroundColor(Color.rgb(activityItem.getmColor().getRed(),activityItem.getmColor().getGreen(),activityItem.getmColor().getBlue()));

    }

    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
