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
public class ContractsAdapter extends RecyclerView.Adapter<ContractsAdapter.MyViewHolder>{
    private List<MContract> MContractList;
    private Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price, unit;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            price= (TextView) view.findViewById(R.id.price);
            unit = (TextView) view.findViewById(R.id.unit);
        }
    }

    public MContract getItem(int position) {
        return MContractList.get(position);
    }

    public ContractsAdapter(Context mContext, List<MContract> MContractList) {
        this.MContractList = MContractList;
        this.mContext = mContext;
    }

    public void setMContractList(List<MContract> MContractList) {
        this.MContractList = MContractList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contract_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MContract p = Utils.getPriceContract(MContractList.get(position),mContext);
        holder.name.setText(p.getContract_name());

        if (p.getNumber() == 0 && p.getNumber_group()!=0) {
            holder.unit.setText(p.getContract_unit_group_name());
            holder.price.setText(Utils.formatCurrency(p.getPrice_group()));

        } else if (p.getNumber() != 0 && p.getNumber_group()!=0) {
            holder.unit.setText(p.getContract_unit_group_name() + "\n" + p.getContract_unit_name());
            holder.price.setText(Utils.formatCurrency(p.getPrice_group()) + "\n" + Utils.formatCurrency(p.getPrice()));
        }else if (p.getNumber() != 0 && p.getNumber_group()==0) {
            holder.unit.setText( p.getContract_unit_name());
            holder.price.setText(Utils.formatCurrency(p.getPrice()));

        }else if (p.getNumber() == 0 && p.getNumber_group()==0) {
            holder.unit.setText("");
            holder.price.setText(Utils.formatCurrency(p.getPrice()));
        }

    }

    @Override
    public int getItemCount() {
        return MContractList.size();
    }
}
