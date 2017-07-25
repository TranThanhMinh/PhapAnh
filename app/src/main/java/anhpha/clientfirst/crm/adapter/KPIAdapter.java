package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MKPI;
import anhpha.clientfirst.crm.utils.Utils;

/**
 * Created by mc975 on 2/6/17.
 */
public class KPIAdapter extends RecyclerView.Adapter<KPIAdapter.MyViewHolder>{
    private List<MKPI> activityItemList;
    public Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, target, perform, percent;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            target= (TextView) view.findViewById(R.id.target);
            perform= (TextView) view.findViewById(R.id.perform);
            percent = (TextView) view.findViewById(R.id.percent);
        }
    }

    public KPIAdapter(Context context, List<MKPI> activityItemList) {
        this.activityItemList = activityItemList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kpi_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MKPI activityItem = activityItemList.get(position);
        holder.name.setText(activityItem.getKpi_user_type_name());
        holder.target.setText(Utils.formatCurrency(activityItem.getKpi_target_value()));
        holder.perform.setText(Utils.formatCurrency(activityItem.getKpi_value()));
        try {
            if(activityItem.getKpi_target_value() != 0) {
                String a = (int) (activityItem.getKpi_value() / activityItem.getKpi_target_value() * 100) + " %";
                holder.percent.setText(a);
            }else{
                holder.percent.setText("0 %");
            }
        }catch (Exception e){
            holder.percent.setText("0 %");
        }
    }

    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
