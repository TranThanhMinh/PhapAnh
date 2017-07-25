package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MGroupContract;

/**
 * Created by mc975 on 2/6/17.
 */
public class GroupContractsAdapter extends RecyclerView.Adapter<GroupContractsAdapter.MyViewHolder>{
    private List<MGroupContract> mGroupContractList = new ArrayList<>();
    private Context mContext;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
        }
    }


    public GroupContractsAdapter(Context mContext, List<MGroupContract> MGroupContractList) {
        this.mGroupContractList = MGroupContractList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_contract_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MGroupContract activityItem = mGroupContractList.get(position);
        holder.name.setText(activityItem.getContract_group_name());

    }

    @Override
    public int getItemCount() {
        return mGroupContractList.size();
    }
}
