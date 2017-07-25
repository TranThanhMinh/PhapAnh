package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MClientContact;

/**
 * Created by mc975 on 2/6/17.
 */
public class ChooseClientContactAdapter extends RecyclerView.Adapter<ChooseClientContactAdapter.MyViewHolder>{
    private List<MClientContact> activityItemList;
    public Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public CheckBox checkBox;
        public TextView textView;

        public MyViewHolder(View view) {
            super(view);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            textView = (TextView) view.findViewById(R.id.phone);
        }

    }

    public ChooseClientContactAdapter(Context context, List<MClientContact> activityItemList) {
        this.activityItemList = activityItemList;
        this.mContext = context;
    }

    public void setActivityItemList(List<MClientContact> activityItemList) {
        this.activityItemList = activityItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_check_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)  {
        final MClientContact activityItem = activityItemList.get(position);
        holder.checkBox.setText(activityItem.getClient_name());
        holder.textView.setText(activityItem.getPhone());
        holder.textView.setVisibility(View.VISIBLE);
        holder.checkBox.setChecked(activityItem.is_select());

    }
    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
