package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MLabel;

/**
 * Created by mc975 on 2/6/17.
 */
public class ChooseClientLabelAdapter extends RecyclerView.Adapter<ChooseClientLabelAdapter.MyViewHolder>{
    private List<MLabel> activityItemList;
    public Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView name;
        public ImageButton check;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            check= (ImageButton) view.findViewById(R.id.check);
        }
    }

    public ChooseClientLabelAdapter(Context context, List<MLabel> activityItemList) {
        this.activityItemList = activityItemList;
        this.mContext = context;
    }

    public void setActivityItemList(List<MLabel> activityItemList) {
        this.activityItemList = activityItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.choose_label_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)  {
        final MLabel activityItem = activityItemList.get(position);
        holder.name.setText(activityItem.getClient_label_name());
        if(!activityItem.getHex().isEmpty()) {
            holder.name.setBackgroundColor(Color.parseColor(activityItem.getHex()));
            holder.check.setBackgroundColor(Color.parseColor(activityItem.getHex()));
        }else {
            holder.name.setBackgroundColor(Color.GRAY);
            holder.check.setBackgroundColor(Color.GRAY);
        }
        if(activityItem.getIs_has()){
            holder.check.setVisibility(View.VISIBLE);
        }else{
            holder.check.setVisibility(View.GONE);
        }

    }
    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
