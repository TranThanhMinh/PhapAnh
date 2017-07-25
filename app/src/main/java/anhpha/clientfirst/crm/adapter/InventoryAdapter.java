package anhpha.clientfirst.crm.adapter;

import android.content.Context;
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
public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.MyViewHolder>{
    private List<MContract> activityItemList;
    public Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, number, total;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            number= (TextView) view.findViewById(R.id.number);
            total= (TextView) view.findViewById(R.id.total);
        }
    }

    public InventoryAdapter(Context context, List<MContract> activityItemList) {
        this.activityItemList = activityItemList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inventory_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MContract activityItem = activityItemList.get(position);
        MContract p = Utils.getPriceContract(activityItem, mContext);
        holder.name.setText(activityItem.getContract_name());


            if (p.getNumber() == 0 && p.getNumber_group()!=0) {
                holder.number.setText(p.getContract_unit_group_name());
                holder.total.setText(Utils.formatCurrency(p.getPrice_group_number()));

            } else if (p.getNumber() != 0 && p.getNumber_group()!=0) {
                holder.number.setText(p.getContract_unit_group_name() + "\n" + p.getContract_unit_name());
                holder.total.setText(Utils.formatCurrency(p.getPrice_group_number()) + "\n" + Utils.formatCurrency(p.getPrice_number()));
            }else if (p.getNumber() != 0 && p.getNumber_group()==0) {
                holder.number.setText( p.getContract_unit_name());
                holder.total.setText(Utils.formatCurrency(p.getPrice_number()));

            }else if (p.getNumber() == 0 && p.getNumber_group()==0) {
                holder.number.setText("-");
                holder.total.setText("-");
            }




    }

    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
